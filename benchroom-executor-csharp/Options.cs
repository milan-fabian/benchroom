using CommandLine;
using CommandLine.Text;
using System.Collections.Generic;

namespace Benchroom.Executor
{
    class Options
    {
        [Option('s', "server", Required = true, HelpText = "URL of the Benchroom server")]
        public string ServerUrl { get; set; }

        [Option('i', "id", Required = true, HelpText = "ID of the benchmark to run")]
        public string BenchmarkId { get; set; }

        [Option('d', "dir", Required = false, DefaultValue = "run", HelpText = "Directory where to run")]
        public string Directory { get; set; }

        [Option('n', "runs", Required = false, DefaultValue = 1, HelpText = "How many times to run each combination")]
        public int NumberOfRuns { get; set; }

        [Option('v', "verbose", Required = false, DefaultValue = false, HelpText = "Print output from the run program")]
        public bool PrintOutput { get; set; }

        [Option('t', "test", Required = false, DefaultValue = false, HelpText = "Don't send results to server")]
        public bool TestRun { get; set; }

        [Option('p', "priority", Required = true, HelpText = "Minimal priority for parameters")]
        public short MinPriority { get; set; }

        [Option('a', "deviation", Required = false, DefaultValue = 2,
            HelpText = "Maximal allowed deviation between results if running each combination more than once")]
        public int MaxDeviation { get; set; }

        [OptionList('c', "parameters", Required = false, Separator = ',', DefaultValue = null,
            HelpText = "Run just choosen parameters (enter parameter ids")]
        public IList<string> ChoosenParameters { get; set; }

        [HelpOption]
        public string GetUsage()
        {
            return HelpText.AutoBuild(this, (HelpText current) => HelpText.DefaultParsingErrorsHandler(this, current));
        }

    }
}
