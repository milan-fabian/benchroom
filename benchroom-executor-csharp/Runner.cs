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
        private long? timeMonitor = null;
        private List<RunData.RunMonitor> fileMonitors = new List<RunData.RunMonitor>();
        private Dictionary<string, string> systemParameters = SystemParameters.getParameters();

        public Runner(RunData runData, String directory, string server)
        {
            this.runData = runData;
            this.server = server;
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
            Process process = prepareProcess(parameter);
            Stopwatch stopwatch = new Stopwatch();
            Thread.Sleep(500);
            run.whenStarted = DateTime.Now;
            stopwatch.Start();
            try {
                runProcess(process);
            }
            catch (Exception ex)
            {
                Console.WriteLine("-- Can't run benchmark: " + ex);
                return;
            }
            stopwatch.Stop();
            Console.WriteLine("-- Running with parameter \"" + parameter.parameterId + "\" took " + stopwatch.Elapsed.TotalSeconds + " seconds");
            if (process.ExitCode != 0)
            {
                Console.WriteLine("-- Exit code is " + process.ExitCode + ", result won't be saved");
                return;
            }
            run.parameterId = parameter.parameterId;
            run.runId = runData.runId;
            run.systemParameters = systemParameters;
            run.results = new List<Run.RunResult> {
                new Run.RunResult() { monitorId = timeMonitor.Value, result = stopwatch.Elapsed.TotalMilliseconds }
            };
            foreach (RunData.RunMonitor monitor in fileMonitors)
            {
                long size = new FileInfo(directory + "\\" + monitor.action).Length;
                run.results.Add(new Run.RunResult() { monitorId = monitor.monitorId, result = size });
            }
            Connector.sendRun(server, run);
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
