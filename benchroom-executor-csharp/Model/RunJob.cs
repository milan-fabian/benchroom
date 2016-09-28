using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Benchroom.Executor.Model
{
    class RunJob
    {
        public string id { get; set; }
        public long benchmarkSuiteId { get; set; }
        public long softwareVersionId { get; set; }
        public short minPriority { get; set; }
        public int numberOfRuns { get; set; }
    }
}
