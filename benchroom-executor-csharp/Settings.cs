using System;
using System.Globalization;
using System.IO;

namespace Benchroom.Executor
{
    class Settings
    {
        public static string ServerUrl { get; set; }

        public static string Directory { get; set; }

        public static float MaxDeviation { get; set; }

        public static void Load()
        {
            String[] lines = File.ReadAllLines("benchroom-executor-settings.properties");
            foreach (String line in lines)
            {
                String[] parts = line.Split('=');
                switch (parts[0])
                {
                    case "server_url":
                        ServerUrl = parts[1].Trim();
                        break;
                    case "directory":
                        Directory = parts[1].Trim();
                        break;
                    case "max_deviation":
                        MaxDeviation = float.Parse(parts[1].Trim(), NumberStyles.Any, CultureInfo.InvariantCulture);
                        break;
                }
            }
        }
    }
}
