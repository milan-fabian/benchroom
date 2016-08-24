using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Benchroom.Executor
{
    class Runner
    {
        private RunData runData;

        public Runner(RunData runData)
        {
            this.runData = runData;
        }

        public void runBenchmarks()
        {
            Console.Write("- Starting benchmarks for \"" + runData.runName + "\"");
            setupSoftware();
            setupBenchmark();
            foreach (RunData.RunParameter parameter in runData.parameters)
            {
                executeRun(parameter);
            }
            cleanupBenchmark();
            cleanupSoftware();
            Console.Write("- Finished benchmarks for \"" + runData.runName + "\"");
        }

        private void executeRun(RunData.RunParameter parameter)
        {
           
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
