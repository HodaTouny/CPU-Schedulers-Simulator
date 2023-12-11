import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class SRTF_Scheduling implements SchedulingAlgorithm {

    @Override
    public void CPUScheduling(Vector<Process> processes) {
        int currentTime = 0;
        int cntCompletedProcesses = 0;
        Map<String, Integer> waitingTime = new HashMap<>();
        Map<String, Integer> turnaroundTime = new HashMap<>();


        while (cntCompletedProcesses < processes.size()) {
            Process Lowest_Burst = null;

            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && process.Burst_Time > 0) {
                    if (Lowest_Burst == null ||
                            process.Burst_Time < Lowest_Burst.Burst_Time ||
                            (process.Burst_Time == Lowest_Burst.Burst_Time && process.AgingFactor > Lowest_Burst.AgingFactor)) {
                        Lowest_Burst = process;
                    }
                }
            }
            if (Lowest_Burst == null) {
                currentTime++;
            } else {
                System.out.println("Executing process " + Lowest_Burst.Name + " at time " + currentTime);
                currentTime++;
                Lowest_Burst.Burst_Time--;

                for (Process process : processes) {
                    if (process != Lowest_Burst && process.arrivalTime <= currentTime && process.Burst_Time > 0) {
                        process.AgingFactor++;
                    }
                }

                if (Lowest_Burst.Burst_Time == 0) {
                    cntCompletedProcesses++;
                    turnaroundTime.put(Lowest_Burst.Name, currentTime - Lowest_Burst.arrivalTime);
                    waitingTime.put(Lowest_Burst.Name, turnaroundTime.get(Lowest_Burst.Name) - Lowest_Burst.originalBurstTime);
                    System.out.println("Executing process " + Lowest_Burst.Name + " completed at time " + currentTime);
                }
            }
        }

        for (Process process : processes) {
            System.out.println("Process " + process.Name +
                    " - Waiting Time: " + waitingTime.get(process.Name) +
                    ", Turnaround Time: " + turnaroundTime.get(process.Name));
        }
        double avgWaitingTime = waitingTime.values().stream().mapToInt(Integer::intValue).average().orElse(0);
        double avgTurnaroundTime = turnaroundTime.values().stream().mapToInt(Integer::intValue).average().orElse(0);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
}
