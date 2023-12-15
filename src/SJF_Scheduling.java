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
//        int WaitTime;
//        int TurnaroundTime;

        Vector<Process> Answer = new Vector<>();

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
                lowest_burst.TernARound = currentTime - lowest_burst.arrivalTime;
                System.out.println("turn around time: " + lowest_burst.TernARound);
                lowest_burst.WaitingTime = lowest_burst.TernARound - lowest_burst.originalBurstTime;
                System.out.println("waiting time: " + lowest_burst.WaitingTime);
                totalWaitTime += lowest_burst.WaitingTime;
                totalTurnaroundTime += lowest_burst.TernARound;
                Answer.add(lowest_burst);
            }
        }
        System.out.println("Average Wait Time: " + (float) totalWaitTime / Size);
        System.out.println("Average Turnaround Time: " + (float)totalTurnaroundTime / Size);
    }

}

