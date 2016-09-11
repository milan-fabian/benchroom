using Benchroom.Executor.Model;
using log4net;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Management.Automation;
using System.Reflection;
using System.Text;
using System.Threading;

namespace Benchroom.Executor
{
    class Runner
    {
        private static readonly ILog logger = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        private RunInput runData;
        private string directory;
        public string Server { private get; set; }
        public int NumberOfRuns { private get; set; }
        public bool PrintOutput { private get; set; }
        public bool TestRun { private get; set; }
        public int MaxDeviation { private get; set; }
        private string protocolFile;
        private long? timeMonitor;
        private long? cpuTimeMonitor;
        private List<RunInput.RunMonitor> fileMonitors = new List<RunInput.RunMonitor>();
        private Dictionary<string, string> systemParameters = SystemParameters.getParameters();

        public Runner(RunInput runData, String directory)
        {
            this.runData = runData;
            this.protocolFile = "benchroom_protocol-" + DateTime.Now.ToString().Replace(':', '.') + "-id" + runData.runId + ".txt";
            this.directory = directory;
            Directory.CreateDirectory(directory);
            foreach (RunInput.RunMonitor monitor in runData.monitors)
            {
                if (monitor.type.Equals(RunInput.RunMonitor.RUN_TIME))
                {
                    timeMonitor = monitor.monitorId;
                } else if (monitor.type.Equals(RunInput.RunMonitor.FILE_SIZE))
                {
                    fileMonitors.Add(monitor);
                } else if(monitor.type.Equals(RunInput.RunMonitor.CPU_TIME)) {
                    cpuTimeMonitor = monitor.monitorId;
                }
            }
        }

        public void runBenchmarks()
        {
            logger.Info("Starting benchmarks for \"" + runData.runName + "\"");
            writeProtocolHeader();
            setupSoftware();
            setupBenchmark();
            parameterSetupScripts();
            int i = 1;
            List<List<RunInput.RunParameter>> combinations = ParameterCombinations.getCombinations(runData.parameters);
            foreach (List<RunInput.RunParameter> parameters in combinations)
            {
                logger.Info("Running with parameters " + i++ + " out of " + combinations.Count);
                executeRun(parameters);
            }
            parameterCleanupScripts();
            cleanupBenchmark();
            cleanupSoftware();
            logger.Info("Finished benchmarks for \"" + runData.runName + "\"");
        }

        private void executeRun(List<RunInput.RunParameter> parameters)
        {
            RunOutput run = new RunOutput();
            run.whenStarted = DateTime.Now;
            List<Dictionary<long, double>> results = new List<Dictionary<long, double>>();
            List<long> parameterIds = new List<long>();
            StringBuilder parameterNames = new StringBuilder();
            List<string> parameterValues = new List<string>();
            foreach(RunInput.RunParameter parameter in parameters)
            {
                parameterIds.Add(parameter.parameterId);
                parameterNames.Append(parameter.parameterName).Append("; ");
                parameterValues.Add(parameter.commandLineArguments);
            }
            parameterNames.Remove(parameterNames.Length - 2, 2);
            string commandLineArguments = String.Format(runData.commandLineArguments, parameterValues.ToArray());
            writeToProtocol("Running with parameters \"" + parameterNames + "\", command line is: \"" + commandLineArguments + "\"\n");
            for (int i = 0; i < NumberOfRuns; i++)
            {
                Dictionary<long, double> result = executeSingleRun(commandLineArguments, parameterNames.ToString());
                if (result != null)
                {
                    results.Add(result);
                }
                afterEachRun();
            }
            if (results.Count > 0)
            {
                run.parameterIds = parameterIds;
                run.runId = runData.runId;
                run.systemParameters = systemParameters;
                run.results = new List<RunOutput.RunResult>();
                foreach (long monitorId in results[0].Keys)
                {
                    double sum = 0;
                    double sumSquared = 0;
                    results.ForEach(x => {
                        double value = x[monitorId];
                        sum += value;
                        sumSquared += value * value;
                        });
                    double average = sum / results.Count;
                    if (results.Count > 1)
                    {
                        double deviation = Math.Sqrt((sumSquared / results.Count) - (average * average)) / average;
                        if (deviation * 100 > MaxDeviation)
                        {
                            logger.Warn(String.Format("Deviation of results is too big ({0:N2} %), not sending results", deviation * 100));
                            return;
                        }
                    }
                    run.results.Add(new RunOutput.RunResult() { monitorId = monitorId, result = average });
                }
                if (!TestRun)
                {
                    Connector.sendRun(Server, run);
                } else
                {
                    logger.Warn("Test run, not sending results to server");
                }
            }
        }

        private Dictionary<long, double> executeSingleRun(string commandLineArguments, string parameterNames)
        {
            Process process = prepareProcess(commandLineArguments);
            Thread.Sleep(400);
            try
            {
                runProcess(process);
            }
            catch (Exception ex)
            {
                logger.Info("Can't run benchmark: " + ex);
                writeToProtocol("Run with parameters \"" + parameterNames + "\" was unsuccessful due to exception\n");
                return null;
            }
            TimeSpan elapsed = process.ExitTime - process.StartTime;
            logger.Info("Running with parameters \"" + parameterNames + "\" took " + elapsed.TotalSeconds + " seconds");
            if (process.ExitCode != 0)
            {
                logger.Warn("Exit code is " + process.ExitCode + ", result won't be saved");
                writeToProtocol("Run with parameters \"" + parameterNames + "\" was unsuccessful, exit code is " + process.ExitCode + "\n");
                return null;
            }
            Dictionary<long, double> results = new Dictionary<long, double>();
            writeToProtocol("Run with parameters \"" + parameterNames + "\" was successfull\n");
            if (timeMonitor.HasValue)
            {
                writeToProtocol("\tElapsed time: " + elapsed.TotalMilliseconds + " ms\n");
                results.Add(timeMonitor.Value, elapsed.TotalMilliseconds);
            }
            if (cpuTimeMonitor.HasValue)
            {
                double cpuTime = process.TotalProcessorTime.TotalMilliseconds;
                writeToProtocol("\tCPU time: " + cpuTime + " %\n");
                results.Add(cpuTimeMonitor.Value, cpuTime);
            }
            foreach (RunInput.RunMonitor monitor in fileMonitors)
            {
                long size = new FileInfo(directory + "\\" + monitor.action).Length;
                results.Add(monitor.monitorId, size);
            }
            return results;
        }

        private Process prepareProcess(string commandLineArguments)
        {
            Process process = new Process();
            ProcessStartInfo startInfo = new ProcessStartInfo();
            startInfo.FileName = directory + "\\software.exe";
            startInfo.Arguments = commandLineArguments;
            startInfo.WorkingDirectory = directory;
            startInfo.UseShellExecute = false;
            if (!PrintOutput)
            {
                startInfo.RedirectStandardOutput = true;
                startInfo.RedirectStandardError = true;
                process.OutputDataReceived += ProcessOutputScrapper;
            }
            process.StartInfo = startInfo;
            return process;
        }

        private void ProcessOutputScrapper(object sender, DataReceivedEventArgs e)
        {
        }

        private void runProcess(Process process)
        {
            process.Start();
            if (!PrintOutput)
            {
                process.BeginOutputReadLine();
                process.BeginErrorReadLine();
            }
            process.WaitForExit();
        }

        private void setupSoftware()
        {
            if (runData.sofwareSetup != null && runData.sofwareSetup != "")
            {
                logger.Info("Executing setup software script");
                runScript(runData.sofwareSetup);
            }
        }

        private void setupBenchmark()
        {
            if (runData.benchmarkSetup != null && runData.benchmarkSetup != "") {
                logger.Info("Executing setup benchmark script");
                runScript(runData.benchmarkSetup);
            }
        }

        private void cleanupSoftware()
        {
            if (runData.sofwareCleanup != null && runData.sofwareCleanup != "")
            {
                logger.Info("Executing cleanup software script");
                runScript(runData.sofwareCleanup);
            }
        }

        private void cleanupBenchmark()
        {
            if (runData.benchmarkSetup != null && runData.benchmarkSetup != "")
            {
                logger.Info("Executing cleanup benchmark script");
                runScript(runData.benchmarkCleanup);
            }
        }

        private void afterEachRun()
        {
            if (runData.bencharkAfterEachRunScript != null && runData.bencharkAfterEachRunScript != "")
            {
                logger.Info("Executing cleanup benchmark script");
                runScript(runData.bencharkAfterEachRunScript);
            }
        }

        private void parameterSetupScripts()
        {
            foreach (List<RunInput.RunParameter> parameterList in runData.parameters)
            {
                foreach(RunInput.RunParameter parameter in parameterList)
                {
                    if (parameter.setupScript != null && parameter.setupScript != "")
                    {
                        logger.Info("Executing setup script for parameter \"" + parameter.parameterName + "\"");
                        runScript(parameter.setupScript);
                    }
                }
            }
        }

        private void parameterCleanupScripts()
        {
            foreach (List<RunInput.RunParameter> parameterList in runData.parameters)
            {
                foreach (RunInput.RunParameter parameter in parameterList)
                {
                    if (parameter.cleanupScript != null && parameter.cleanupScript != "")
                    {
                        logger.Info("Executing cleanup script for parameter \"" + parameter.parameterName + "\"");
                        runScript(parameter.cleanupScript);
                    }
                }
            }
        }

        private void runScript(string script)
        {
            using (PowerShell powerShell = PowerShell.Create())
            {
                powerShell.AddScript("cd " + directory);
                powerShell.AddScript(script);
                powerShell.Invoke();
                if (powerShell.Streams.Error.Count > 0)
                {
                    logger.Warn("Error while executing script: " + powerShell.Streams.Error[0].ToString());
                }
            }
        }

        private void writeToProtocol(string data)
        {
            File.AppendAllText(protocolFile, data);
        }

        private void writeProtocolHeader()
        {
            writeToProtocol("Benchroom " + Assembly.GetExecutingAssembly().GetName().Version.ToString()
                + "\nBenchmark \"" + runData.runName + "\"\nSystem parameters: "
                + string.Join(";", systemParameters.Select(x => x.Key + "=" + x.Value).ToArray()) + "\n\n");
        }
    }
}
