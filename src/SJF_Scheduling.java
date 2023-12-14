import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class SJF_Scheduling implements SchedulingAlgorithm {
    int contextSwitchingTime;

    public SJF_Scheduling(int contextSwitchingTime) {
        this.contextSwitchingTime = contextSwitchingTime;
    }

    @Override
    public void CPUScheduling(Vector<Process> processes ) {
        int Size = processes.size();
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        double totalWaitTime = 0;
        double totalTurnaroundTime = 0;
        int WaitTime;
        int TurnaroundTime;

        while (!processes.isEmpty()) {
            Process lowest_burst = null;
            boolean found = false;


            for (Process process : processes) {
                if (process.arrivalTime <= currentTime
                     && (!found || process.Burst_Time < lowest_burst.Burst_Time)) {
                    lowest_burst = process;
                    found =true;
                }
            }

            if (!found) {
                currentTime++;
            } else {
                processes.remove(lowest_burst);
                System.out.println("Executing: " + lowest_burst.Name);
                currentTime += lowest_burst.Burst_Time+ contextSwitchingTime;
                TurnaroundTime = currentTime - lowest_burst.arrivalTime;
                System.out.println("turn around time: " + TurnaroundTime);
                WaitTime = TurnaroundTime - lowest_burst.originalBurstTime;
                System.out.println("waiting time: " + WaitTime);
                totalWaitTime += WaitTime;
                totalTurnaroundTime += TurnaroundTime;
            }
        }
        System.out.println("Average Wait Time: " + (float) totalWaitTime / Size);
        System.out.println("Average Turnaround Time: " + (float)totalTurnaroundTime / Size);
    }

}

