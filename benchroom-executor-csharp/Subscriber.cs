using Benchroom.Executor.Model;
using System.Threading;

namespace Benchroom.Executor
{
    class Subscriber
    {
        public static void Subscribe()
        {
            while (true)
            {
                RunJob runJob = Connector.subscribeCheck();
                if (runJob != null && runJob.id.Length > 0)
                {
                    string dataId = runJob.softwareVersionId + "-" + runJob.benchmarkSuiteId;
                    RunInput runData = Connector.getRunData(dataId, runJob.minPriority, null);
                    Runner runner = new Runner(runData)
                    {
                        NumberOfRuns = runJob.numberOfRuns,
                        PrintOutput = false,
                        TestRun = false
                    };
                    runner.RunnedCombinations = Connector.getRunnedCombinations(dataId, runJob.minPriority, null);
                    runner.runBenchmarks();
                    Connector.subscribeChange(runJob.id, "FINISHED");
                }
                else
                {
                    Thread.Sleep(10 * 1000);
                }
            }
        }

    }
}
