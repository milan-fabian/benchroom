using Newtonsoft.Json;
using System;
using System.Net;

namespace Benchroom.Executor
{
    class Program
    {
        private const string URL_BENCHMARK_DATA = "/connector/benchmark_data";

        static void Main(string[] args)
        {
            Options options = new Options();
            if (CommandLine.Parser.Default.ParseArguments(args, options))
            {
                RunData runData = getRunData(options.serverUrl, options.benchmarkId);
                Runner runner = new Runner(runData);
                runner.runBenchmarks();
            }
        }

        private static RunData getRunData(String server, String id)
        {
            using(WebClient webClient = new WebClient())
            {
                String url = server + URL_BENCHMARK_DATA + "?id=" + id + "&platform=WINDOWS_X86_64";
                Console.WriteLine("- Getting data to run from \"" + url + "\"");
                String data = webClient.DownloadString(url);
                return JsonConvert.DeserializeObject<RunData>(data);
            }
        }

    }
}
