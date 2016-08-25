using System;
using System.Collections.Generic;

namespace Benchroom.Executor.Model
{
    class Run
    {
        public string runId { get; set; }
        public long parameterId { get; set; }
        public DateTime whenStarted { get; set; }
        public List<RunResult> results { get; set; }
        public Dictionary<String, String> hardwareParameters { get; set; }

        public class RunResult
        {
            public long monitorId { get; set; }
            public double result { get; set; }
        }
    }
}
