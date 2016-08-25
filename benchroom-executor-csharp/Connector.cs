using Benchroom.Executor.Model;
using Newtonsoft.Json;
using System;
using System.Net;

namespace Benchroom.Executor
{
    class Connector
    {
        private const string URL_BENCHMARK_DATA = "/connector/benchmark_data";
        private const string URL_BENCHMARK_RESULT = "/connector/benchmark_result";


        public static RunData getRunData(String server, String id)
        {
            using (WebClient webClient = new WebClient())
            {
                String url = server + URL_BENCHMARK_DATA + "?id=" + id + "&platform=WINDOWS_X86_64";
                Console.WriteLine("- Getting data to run from \"" + url + "\"");
                String data = webClient.DownloadString(url);
                return JsonConvert.DeserializeObject<RunData>(data);
            }
        }

        public static void sendRun(String server, Run run)
        {
            try {
                using (WebClient webClient = new WebClient())
                {
                    String url = server + URL_BENCHMARK_RESULT;
                    webClient.Headers[HttpRequestHeader.ContentType] = "application/json";
                    webClient.UploadString(url, JsonConvert.SerializeObject(run));
                    Console.WriteLine("-- Data for run send to server");
                }
            } catch (Exception ex)
            {
                Console.WriteLine("-- Can't send data to server: " + ex);
            }
        }
    }
}
