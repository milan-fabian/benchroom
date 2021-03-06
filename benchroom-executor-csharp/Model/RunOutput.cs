﻿using System;
using System.Collections.Generic;

namespace Benchroom.Executor.Model
{
    class RunOutput
    {
        public string runId { get; set; }
        public List<long> parameterIds { get; set; }
        public DateTime whenStarted { get; set; }
        public List<RunResult> results { get; set; }
        public Dictionary<String, String> systemParameters { get; set; }

        public class RunResult
        {
            public long monitorId { get; set; }
            public double result { get; set; }
        }
    }
}
