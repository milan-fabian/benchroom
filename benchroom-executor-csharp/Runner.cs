using Benchroom.Executor.Model;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Management.Automation;
using System.Threading;

namespace Benchroom.Executor
{
    class Runner
    {
        private RunData runData;
        private string server;
        private string directory;
        private int numberOfRuns;
        private long? timeMonitor = null;
        private List<RunData.RunMonitor> fileMonitors = new List<RunData.RunMonitor>();
        private Dictionary<string, string> systemParameters = SystemParameters.getParameters();

        public Runner(RunData runData, String directory, string server, int numberOfRuns)
        {
            this.runData = runData;
            this.server = server;
            this.numberOfRuns = numberOfRuns;
            this.directory = directory;
            Directory.CreateDirectory(directory);
            foreach (RunData.RunMonitor monitor in runData.monitors)
            {
                if (monitor.type.Equals(RunData.RunMonitor.RUN_TIME))
                {
                    timeMonitor = monitor.monitorId;
                } else if (monitor.type.Equals(RunData.RunMonitor.FILE_SIZE))
                {
                    fileMonitors.Add(monitor);
                }
            }
        }

        public void runBenchmarks()
        {
            Console.WriteLine("- Starting benchmarks for \"" + runData.runName + "\"");
            setupSoftware();
            setupBenchmark();
            foreach (RunData.RunParameter parameter in runData.parameters)
            {
                executeRun(parameter);
            }
            cleanupBenchmark();
            cleanupSoftware();
            Console.WriteLine("- Finished benchmarks for \"" + runData.runName + "\"");
        }

        private void executeRun(RunData.RunParameter parameter)
        {
            Run run = new Run();
            run.whenStarted = DateTime.Now;
            List<Dictionary<long, double>> results = new List<Dictionary<long, double>>();
            for (int i = 0; i < numberOfRuns; i++)
            {
                Dictionary<long, double> result = executeSingleRun(parameter);
                if (result != null)
                {
                    results.Add(result);
                }
            }
            if (results.Count > 0)
            {
                run.parameterId = parameter.parameterId;
                run.runId = runData.runId;
                run.systemParameters = systemParameters;
                run.results = new List<Run.RunResult>();
                foreach (long monitorId in results[0].Keys)
                {
                    double sum = 0;
                    results.ForEach(x => sum += x[monitorId]);
                    run.results.Add(new Run.RunResult() { monitorId = monitorId, result = sum / results.Count });
                }
                Connector.sendRun(server, run);
            }
        }

        private Dictionary<long, double> executeSingleRun(RunData.RunParameter parameter)
        {
            Process process = prepareProcess(parameter);
            Stopwatch stopwatch = new Stopwatch();
            Thread.Sleep(500);
            stopwatch.Start();
            try
            {
                runProcess(process);
            }
            catch (Exception ex)
            {
                Console.WriteLine("-- Can't run benchmark: " + ex);
                return null;
            }
            stopwatch.Stop();
            Console.WriteLine("-- Running with parameter \"" + parameter.parameterId + "\" took " + stopwatch.Elapsed.TotalSeconds + " seconds");
            if (process.ExitCode != 0)
            {
                Console.WriteLine("-- Exit code is " + process.ExitCode + ", result won't be saved");
                return null;
            }
            Dictionary<long, double> results = new Dictionary<long, double>();
            if (timeMonitor.HasValue)
            {
                results.Add(timeMonitor.Value, stopwatch.ElapsedMilliseconds);
            }
            foreach (RunData.RunMonitor monitor in fileMonitors)
            {
                long size = new FileInfo(directory + "\\" + monitor.action).Length;
                results.Add(monitor.monitorId, size);
            }
            return results;
        }

        private Process prepareProcess(RunData.RunParameter parameter)
        {
            Process process = new Process();
            ProcessStartInfo startInfo = new ProcessStartInfo();
            startInfo.FileName = directory + "\\software.exe";
            startInfo.Arguments = runData.commandLineArguments.Replace("{}", parameter.commandLineArguments);
            startInfo.WorkingDirectory = directory;
            startInfo.UseShellExecute = false;
            process.StartInfo = startInfo;
            return process;
        }

        private void runProcess(Process process)
        {
            process.Start();
            process.WaitForExit();
        }

        private void setupSoftware()
        {
            if (runData.benchmarkSetup != "")
            {
                Console.WriteLine("- Executing setup software script");
                runScript(runData.sofwareSetup);
            }
        }

        private void setupBenchmark()
        {
            if (runData.benchmarkSetup != "") {
                Console.WriteLine("- Executing setup benchmark script");
                runScript(runData.benchmarkSetup);
            }
        }

        private void cleanupSoftware()
        {
            if (runData.benchmarkSetup != "")
            {
                Console.WriteLine("- Executing cleanup software script");
                runScript(runData.sofwareCleanup);
            }
        }

        private void cleanupBenchmark()
        {
            if (runData.benchmarkSetup != "")
            {
                Console.WriteLine("- Executing cleanup benchmark script");
                runScript(runData.benchmarkCleanup);
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
                    Console.WriteLine("- Error while executing script: " + script);
                }
            }
        }
    }
}
