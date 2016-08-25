using Benchroom.Executor.Model;
using System;

namespace Benchroom.Executor
{
    class Program
    {

        static void Main(string[] args)
        {
            Options options = new Options();
            if (CommandLine.Parser.Default.ParseArguments(args, options))
            {
                RunData runData = Connector.getRunData(options.serverUrl, options.benchmarkId);
                Runner runner = new Runner(runData, options.serverUrl);
                runner.runBenchmarks();
            }
            Console.ReadKey();
        }
    }
}
