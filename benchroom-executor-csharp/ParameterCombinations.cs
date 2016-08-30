using Benchroom.Executor.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Benchroom.Executor
{
    class ParameterCombinations
    {
        public static List<List<RunData.RunParameter>> getCombinations(List<List<RunData.RunParameter>> parameters)
        {
            // keep track of the size of each inner String array
            int[] sizeArray = new int[parameters.Count];

            // keep track of the index of each inner String array which will be used
            // to make the next combination
            int[] counterArray = new int[parameters.Count];

            // Discover the size of each inner array and populate sizeArray.
            // Also calculate the total number of combinations possible using the
            // inner String array sizes.
            int totalCombinationCount = 1;
            for (int i = 0; i < parameters.Count; ++i)
            {
                sizeArray[i] = parameters[i].Count;
                totalCombinationCount *= parameters[i].Count;
            }

            // Store the combinations in a List of String objects
            List<List<RunData.RunParameter>> combinations = new List<List<RunData.RunParameter>>(totalCombinationCount);

            for (int countdown = totalCombinationCount; countdown > 0; --countdown)
            {
                // Run through the inner arrays, grabbing the member from the index
                // specified by the counterArray for each inner array, and build a
                // combination string.
                List<RunData.RunParameter> temp = new List<RunData.RunParameter>();
                for (int i = 0; i < parameters.Count; ++i)
                {
                    temp.Add(parameters[i][counterArray[i]]);
                }
                combinations.Add(temp);  // add new combination to list

                // Now we need to increment the counterArray so that the next
                // combination is taken on the next iteration of this loop.
                for (int incIndex = parameters.Count - 1; incIndex >= 0; --incIndex)
                {
                    if (counterArray[incIndex] + 1 < sizeArray[incIndex])
                    {
                        ++counterArray[incIndex];
                        // None of the indices of higher significance need to be
                        // incremented, so jump out of this for loop at this point.
                        break;
                    }
                    // The index at this position is at its max value, so zero it
                    // and continue this loop to increment the index which is more
                    // significant than this one.
                    counterArray[incIndex] = 0;
                }
            }
            return combinations;
        }
    }
}
