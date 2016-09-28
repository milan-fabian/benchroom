using CommandLine;
using CommandLine.Text;
using System.Collections.Generic;

namespace Benchroom.Executor
{
    class Options
    {
        [Option('i', "id", Required = false, HelpText = "ID of the benchmark to run")]
        public string BenchmarkId { get; set; }

        [Option('n', "runs", Required = false, DefaultValue = 1, HelpText = "How many times to run each combination")]
        public int NumberOfRuns { get; set; }

        [Option('v', "verbose", Required = false, DefaultValue = false, HelpText = "Print output from the run program")]
        public bool PrintOutput { get; set; }

        [Option('t', "test", Required = false, DefaultValue = false, HelpText = "Don't send results to server")]
        public bool TestRun { get; set; }

        [Option('p', "priority", Required = false, HelpText = "Minimal priority for parameters")]
        public short MinPriority { get; set; }

        [OptionList('c', "parameters", Required = false, Separator = ',', DefaultValue = null,
            HelpText = "Run just choosen parameters (enter parameter ids)")]
        public IList<string> ChoosenParameters { get; set; }

        [Option('r', "alsorun", Required = false, DefaultValue = false, 
            HelpText = "Run also with parameter combination which were run before")]
        public bool AlsoRun { get; set; }

        [Option('s', "subscribe", Required = false, DefaultValue = false,
        HelpText = "Subscribe for commands from server")]
        public bool Subscribe { get; set; }

        [HelpOption]
        public string GetUsage()
        {
            return HelpText.AutoBuild(this, (HelpText current) => HelpText.DefaultParsingErrorsHandler(this, current));
        }

    }
}
