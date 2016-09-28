using Benchroom.Executor.Model;
using log4net;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Net;
using System.Text;

namespace Benchroom.Executor
{
    class Connector
    {
        private static readonly ILog logger = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        private const string URL_BENCHMARK_DATA = "/connector/benchmark_data";
        private const string URL_RUNNED_COMBINATIONS = "/connector/runned_combinations";
        private const string URL_BENCHMARK_RESULT = "/connector/benchmark_result";
        private const string URL_SUBSCRIBE_CHECK = "/connector/subscribe/check";
        private const string URL_SUBSCRIBE_CHANGE = "/connector/subscribe/change";

        public static RunInput getRunData(String id, short minPriority, IList<string> choosenParameters)
        {
            using (WebClient webClient = new WebClient())
            {
                StringBuilder url = new StringBuilder();
                url.Append(Settings.ServerUrl).Append(URL_BENCHMARK_DATA).Append("?id=").Append(id)
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

        public static long[][] getRunnedCombinations(String id, short minPriority, IList<string> choosenParameters)
        {
            using (WebClient webClient = new WebClient())
            {
                StringBuilder url = new StringBuilder();
                url.Append(Settings.ServerUrl).Append(URL_RUNNED_COMBINATIONS).Append("?id=").Append(id)
                    .Append("&platform=WINDOWS_X86_64&minPriority=").Append(minPriority);
                if (choosenParameters != null)
                {
                    foreach (String param in choosenParameters)
                    {
                        url.Append("&choosenParameters=").Append(param);
                    }
                }
                logger.Info("Getting already runned parameter combinations from \"" + url + "\"");
                webClient.Headers[HttpRequestHeader.ContentType] = "application/json";
                String data = webClient.UploadString(url.ToString(), JsonConvert.SerializeObject(SystemParameters.getParameters()));
                return JsonConvert.DeserializeObject<long[][]>(data);
            }
        }

        public static void sendRun(RunOutput run)
        {
            try {
                using (WebClient webClient = new WebClient())
                {
                    String url = Settings.ServerUrl + URL_BENCHMARK_RESULT;
                    webClient.Headers[HttpRequestHeader.ContentType] = "application/json";
                    webClient.UploadString(url, JsonConvert.SerializeObject(run));
                    logger.Info("Data for run send to server");
                }
            } catch (Exception ex)
            {
                logger.Warn("Can't send data to server: " + ex);
            }
        }

        public static RunJob subscribeCheck()
        {
            try
            {
                using (WebClient webClient = new WebClient())
                {
                    String url = Settings.ServerUrl + URL_SUBSCRIBE_CHECK;

                    logger.Info("Sending subscribe check to \"" + url + "\"");
                    webClient.Headers[HttpRequestHeader.ContentType] = "application/json";
                    String data = webClient.UploadString(url.ToString(), JsonConvert.SerializeObject(SystemParameters.getParameters()));
                    return JsonConvert.DeserializeObject<RunJob>(data);
                }
            }
            catch (Exception ex)
            {
                logger.Warn("Can't send subscribe to server: " + ex);
                return null;
            }
        }

        public static void subscribeChange(string runJobId, String status)
        {
            try
            {
                using (WebClient webClient = new WebClient())
                {
                    StringBuilder url = new StringBuilder();
                    url.Append(Settings.ServerUrl).Append(URL_SUBSCRIBE_CHANGE).Append("?runJob=").Append(runJobId)
                        .Append("&status=").Append(status);
                    logger.Info("Sending subscribe change to \"" + url.ToString() + "\"");
                    webClient.DownloadData(url.ToString());
                }
            }
            catch (Exception ex)
            {
                logger.Warn("Can't send subscribe to server: " + ex);
            }
        }
    }
}
