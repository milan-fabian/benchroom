using CommandLine;
using CommandLine.Text;

namespace Benchroom.Executor
{
    class Options
    {
        [Option('s', "server", Required = true, HelpText = "URL of the Benchroom server")]
        public string serverUrl { get; set; }

        [Option('i', "id", Required = true, HelpText = "ID of the benchmark to run")]
        public string benchmarkId { get; set; }

        [HelpOption]
        public string GetUsage()
        {
            return HelpText.AutoBuild(this, (HelpText current) => HelpText.DefaultParsingErrorsHandler(this, current));
        }

    }
}
