using Benchroom.Executor.Model;
using log4net;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;

namespace Benchroom.Executor
{
    class Connector
    {
        private static readonly ILog logger = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        private const string URL_BENCHMARK_DATA = "/connector/benchmark_data";
        private const string URL_BENCHMARK_RESULT = "/connector/benchmark_result";

        public static RunInput getRunData(String server, String id, short minPriority, IList<string> choosenParameters)
        {
            using (WebClient webClient = new WebClient())
            {
                StringBuilder url = new StringBuilder();
                url.Append(server).Append(URL_BENCHMARK_DATA).Append("?id=").Append(id)
                    .Append("&platform=WINDOWS_X86_64&minPriority=").Append(minPriority);
                if (choosenParameters != null)
                {
                    foreach (String param in choosenParameters)
                    {
                        url.Append("&choosenParameters=").Append(param);
                    }
                }
                logger.Info("Getting data to run from \"" + url.ToString() + "\"");
                String data = webClient.DownloadString(url.ToString());
                return JsonConvert.DeserializeObject<RunInput>(data);
            }
        }

        public static void sendRun(String server, RunOutput run)
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
