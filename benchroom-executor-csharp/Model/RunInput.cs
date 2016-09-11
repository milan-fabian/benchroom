using System.Collections.Generic;

namespace Benchroom.Executor.Model
{
    public class RunInput
    {
        public string runId { get; set; }
        public string runName { get; set; }
        public string sofwareSetup { get; set; }
        public string sofwareCleanup { get; set; }
        public string benchmarkSetup { get; set; }
        public string bencharkAfterEachRunScript { get; set; }
        public string benchmarkCleanup { get; set; }
        public string commandLineArguments { get; set; }
        public List<List<RunParameter>> parameters { get; set; }
        public List<RunMonitor> monitors { get; set; }

        public class RunParameter
        {

            public long parameterId { get; set; }
            public string parameterName { get; set; }
            public string commandLineArguments { get; set; }
            public string setupScript { get; set; }
            public string cleanupScript { get; set; }
        }

        public class RunMonitor
        {
            public const string RUN_TIME = "RUN_TIME";
            public const string FILE_SIZE = "FILE_SIZE";
            public const string CPU_TIME = "CPU_TIME";

            public long monitorId { get; set; }
            public string type { get; set; }
            public string action { get; set; }
        }
    }
}
