using CommandLine;
using CommandLine.Text;

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

        [HelpOption]
        public string GetUsage()
        {
            return HelpText.AutoBuild(this, (HelpText current) => HelpText.DefaultParsingErrorsHandler(this, current));
        }

    }
}
