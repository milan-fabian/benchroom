using Benchroom.Executor.Model;
using log4net;
using Newtonsoft.Json;
using System;
using System.Net;

namespace Benchroom.Executor
{
    class Connector
    {
        private static readonly ILog logger = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        private const string URL_BENCHMARK_DATA = "/connector/benchmark_data";
        private const string URL_BENCHMARK_RESULT = "/connector/benchmark_result";

        public static RunData getRunData(String server, String id)
        {
            using (WebClient webClient = new WebClient())
            {
                String url = server + URL_BENCHMARK_DATA + "?id=" + id + "&platform=WINDOWS_X86_64";
                logger.Info("Getting data to run from \"" + url + "\"");
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
                    logger.Info("Data for run send to server");
                }
            } catch (Exception ex)
            {
                logger.Warn("Can't send data to server: " + ex);
            }
        }
    }
}
