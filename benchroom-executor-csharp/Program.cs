using Benchroom.Executor.Model;
using log4net;
using log4net.Appender;
using log4net.Core;
using log4net.Layout;
using log4net.Repository.Hierarchy;
using System;

namespace Benchroom.Executor
{
    class Program
    {

        static void Main(string[] args)
        {
            Settings.Load();
            setupLogger();
            Options options = new Options();
            if (CommandLine.Parser.Default.ParseArguments(args, options))
            {
                if (options.Subscribe)
                {
                    Subscriber.Subscribe();
                }
                else
                {
                    RunInput runData = Connector.getRunData(options.BenchmarkId, options.MinPriority, options.ChoosenParameters);
                    Runner runner = new Runner(runData)
                    {
                        NumberOfRuns = options.NumberOfRuns,
                        PrintOutput = options.PrintOutput,
                        TestRun = options.TestRun
                    };
                    if (!options.AlsoRun)
                    {
                        runner.RunnedCombinations = Connector.getRunnedCombinations(options.BenchmarkId,
                                                                            options.MinPriority, options.ChoosenParameters);
                    }
                    runner.runBenchmarks();
                }
            }
        }

        private static void setupLogger()
        {
            Hierarchy hierarchy = (Hierarchy)LogManager.GetRepository();

            PatternLayout patternLayout = new PatternLayout();
            patternLayout.ConversionPattern = "%date{HH:mm:ss,fff} %-5level %logger{1} - %message%newline";
            patternLayout.ActivateOptions();

            ConsoleAppender console = new ConsoleAppender();
            console.Layout = patternLayout;
            console.ActivateOptions();
            hierarchy.Root.AddAppender(console);

            FileAppender roller = new FileAppender();
            roller.AppendToFile = false;
            roller.File = "benchroom-log.txt";
            roller.Layout = patternLayout;
            roller.ActivateOptions();
            hierarchy.Root.AddAppender(roller);

            hierarchy.Root.Level = Level.Info;
            hierarchy.Configured = true;
        }
    }
}
