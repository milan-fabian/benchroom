using System;
using System.Collections.Generic;
using System.Management;

namespace Benchroom.Executor
{
    class SystemParameters
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
        /* SYSTEM */
        private const string SYSTEM_NAME = "SYSTEM_NAME";

        private static readonly Object LOCK = new object();
        private static Dictionary<string, string> parameters;

        public static Dictionary<string, string> getParameters()
        {
            lock (LOCK)
            {
                if (parameters == null)
                {
                    parameters = new Dictionary<string, string>();

                    using (ManagementObjectSearcher searcher = new ManagementObjectSearcher("select * from Win32_Processor"))
                    {
                        foreach (ManagementObject mo in searcher.Get())
                        {
                            parameters.Add(CPU_MANUFACTURER, mo["Manufacturer"].ToString());
                            parameters.Add(CPU_FAMILY, mo["Description"].ToString());
                            parameters.Add(CPU_NAME, mo["Name"].ToString());
                            parameters.Add(CPU_NUM_CORES, mo["NumberOfCores"].ToString());
                            parameters.Add(CPU_NUM_THREADS, mo["NumberOfLogicalProcessors"].ToString());
                            parameters.Add(CPU_MAX_FREQUENCY, mo["MaxClockSpeed"].ToString());
                            break;
                        }
                    }
                    Microsoft.VisualBasic.Devices.ComputerInfo info = new Microsoft.VisualBasic.Devices.ComputerInfo();
                    parameters.Add(RAM_SIZE, new Microsoft.VisualBasic.Devices.ComputerInfo().TotalPhysicalMemory.ToString());

                    parameters.Add(OS_NAME, "Windows");
                    parameters.Add(OS_VERSION, getOSVersion());
                    parameters.Add(OS_KERNEL_VERSION, Environment.OSVersion.Version.Build.ToString());

                    parameters.Add(SYSTEM_NAME, System.Environment.MachineName);
                }
                return parameters;
            }
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

        private static string getOSVersion()
        {
            OperatingSystem os = Environment.OSVersion;
            Version vs = os.Version;

            if (os.Platform == PlatformID.Win32Windows)
            {
                switch (vs.Minor)
                {
                    case 0:
                        return "Windows 95";
                    case 10:
                        return "Windows 98";
                    case 90:
                        return "Windows Me";
                    default:
                        return "Unknown";
                }
            }
            switch (vs.Major)
            {
                case 3:
                    return "Windows NT 3.51";
                case 4:
                    return "Windows NT 4.0";
                case 5:
                    switch (vs.Minor)
                    {
                        case 0:
                            return "Windows 2000";
                        case 1:
                            return "Windows XP";
                        case 2:
                            return "Windows Server 2003";
                        default:
                            return "Unknown";
                    }
                case 6:
                    switch(vs.Minor)
                    {
                        case 0:
                            return "Windows Vista";
                        case 1:
                            return "Windows 7";
                        case 2:
                            return "Windows 8";
                        case 3:
                            return "Windows 8.1";
                        default:
                            return "Unknown";
                    }
                case 10:
                    return "Windows 10";
                default:
                    return "Unknown";
            }
        }
    }
}
