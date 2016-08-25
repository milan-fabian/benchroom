using System;
using System.Collections.Generic;
using System.Management;

namespace Benchroom.Executor
{
    class Hardware
    {
        /* CPU */
        private const string CPU_MANUFACTURER = "CPU_MANUFACTURER";
        private const string CPU_FAMILY = "CPU_FAMILY";
        private const string CPU_NAME = "CPU_NAME";
        private const string CPU_NUM_CORES = "CPU_NUM_CORES";
        private const string CPU_NUM_THREADS = "CPU_NUM_THREADS";
        private const string CPU_BASE_FREQUENCY = "CPU_BASE_FREQUENCY";
        private const string CPU_MAX_FREQUENCY = "CPU_MAX_FREQUENCY";
        /* RAM */
        private const string RAM_SIZE = "RAM_SIZE";
        /* OS */
        private const string OS_NAME = "OS_NAME";
        private const string OS_VERSION = "OS_VERSION";
        private const string OS_KERNEL_VERSION = "OS_KERNEL_VERSION";


        public static Dictionary<string, string> getParameters()
        {
            Dictionary<string, string> result = new Dictionary<string, string>();

            using (ManagementObjectSearcher searcher = new ManagementObjectSearcher("select * from Win32_Processor"))
            {
                foreach (ManagementObject mo in searcher.Get())
                {
                    result.Add(CPU_MANUFACTURER, mo["Manufacturer"].ToString());
                    result.Add(CPU_FAMILY, mo["Description"].ToString());
                    result.Add(CPU_NAME, mo["Name"].ToString());
                    result.Add(CPU_NUM_CORES, mo["NumberOfCores"].ToString());
                    result.Add(CPU_NUM_THREADS, mo["NumberOfLogicalProcessors"].ToString());
                    result.Add(CPU_MAX_FREQUENCY, mo["MaxClockSpeed"].ToString());
                    break;
                }
            }

            result.Add(OS_KERNEL_VERSION, Environment.OSVersion.Version.Build.ToString());
            return result;
        }

        private static string getFromManagement(string name, string property)
        {
            using (ManagementObjectSearcher searcher = new ManagementObjectSearcher("select * from " + name))
            {
                foreach (ManagementObject mo in searcher.Get()) {
                    return mo[property].ToString();
                }
            }
            return "";
        }

    }
}
