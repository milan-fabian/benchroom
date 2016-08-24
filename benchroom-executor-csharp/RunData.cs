using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Benchroom.Executor
{
    public class RunData
    {
        public string runId { get; set; }

        public string runName { get; set; }

        public string sofwareSetup { get; set; }

        public string sofwareCleanup { get; set; }

        public string benchmarkSetup { get; set; }

        public string benchmarkCleanup { get; set; }

        public List<RunParameter> parameters { get; set; }

        public class RunParameter
        {

            public String parameterId { get; set; }

            public String commandLineArguments { get; set; }

            public String commandLineInput { get; set; }
        }
    }
}
