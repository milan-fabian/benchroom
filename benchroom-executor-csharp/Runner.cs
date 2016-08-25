using Benchroom.Executor.Model;
using System;
using System.Collections.Generic;
using System.Diagnostics;

namespace Benchroom.Executor
{
    class Runner
    {
        private RunData runData;
        private string server;
        private long? timeMonitor = null;
        private Dictionary<string, string> hardwareParameters = Hardware.getParameters();

        public Runner(RunData runData, string server)
        {
            this.runData = runData;
            this.server = server;
            foreach (RunData.RunMonitor monitor in runData.monitors)
            {
                if (monitor.type.Equals(RunData.RunMonitor.RUN_TIME))
                {
                    timeMonitor = monitor.monitorId;
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
            Stopwatch stopwatch = new Stopwatch();
            Process process = new Process();
            ProcessStartInfo startInfo = new ProcessStartInfo();
            startInfo.FileName = runData.runName + ".exe";
            startInfo.Arguments = parameter.commandLineArguments;
            startInfo.UseShellExecute = false;
            process.StartInfo = startInfo;
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
            run.hardwareParameters = hardwareParameters;
            run.results = new List<Run.RunResult> {
                new Run.RunResult() { monitorId = timeMonitor.Value, result = stopwatch.Elapsed.TotalMilliseconds }
            };
            Connector.sendRun(server, run);
        }

        private void runProcess(Process process)
        {
            process.Start();
            process.WaitForExit();
        }

        private void setupSoftware()
        {
    
        }

        private void setupBenchmark()
        {

        }

        private void cleanupSoftware()
        {

        }

        private void cleanupBenchmark()
        {

        }
    }
}
