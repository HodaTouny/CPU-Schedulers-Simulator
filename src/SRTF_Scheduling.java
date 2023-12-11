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
                if (process.arrivalTime <= currentTime && process.burstTime > 0) {
                    if (Lowest_Burst == null ||
                            process.burstTime < Lowest_Burst.burstTime ||
                            (process.burstTime == Lowest_Burst.burstTime && process.agingFactor > Lowest_Burst.agingFactor)) {
                        Lowest_Burst = process;
                    }
                }
            }
            if (Lowest_Burst == null) {
                currentTime++;
            } else {
                System.out.println("Executing process " + Lowest_Burst.name + " at time " + currentTime);
                currentTime++;
                Lowest_Burst.burstTime--;

                for (Process process : processes) {
                    if (process != Lowest_Burst && process.arrivalTime <= currentTime && process.burstTime > 0) {
                        process.agingFactor++;
                    }
                }

                if (Lowest_Burst.burstTime == 0) {
                    cntCompletedProcesses++;
                    turnaroundTime.put(Lowest_Burst.name, currentTime - Lowest_Burst.arrivalTime);
                    waitingTime.put(Lowest_Burst.name, turnaroundTime.get(Lowest_Burst.name) - Lowest_Burst.originalBurstTime);
                    System.out.println("Executing process " + Lowest_Burst.name + " completed at time " + currentTime);
                }
            }
        }

        for (Process process : processes) {
            System.out.println("Process " + process.name +
                    " - Waiting Time: " + waitingTime.get(process.name) +
                    ", Turnaround Time: " + turnaroundTime.get(process.name));
        }
        double avgWaitingTime = waitingTime.values().stream().mapToInt(Integer::intValue).average().orElse(0);
        double avgTurnaroundTime = turnaroundTime.values().stream().mapToInt(Integer::intValue).average().orElse(0);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
}
