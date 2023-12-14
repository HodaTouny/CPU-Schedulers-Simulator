import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class PriorityScheduling implements SchedulingAlgorithm {

    public PriorityScheduling() {
    }

    @Override
    public void CPUScheduling(Vector<Process> processes) {
        int size = processes.size();
        int currentTime = 0;
        double totalWaitTime = 0;
        double totalTurnaroundTime = 0;
        int completionTime;
        int waitTime;
        int turnaroundTime;
        Vector<Process> arrivedProcesses = new Vector<>();
        while (!processes.isEmpty()) {
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime) {
                    arrivedProcesses.add(process);
                }
            }
            Collections.sort(arrivedProcesses, Comparator.comparingInt(p -> p.Priority_Number));

            if (arrivedProcesses.isEmpty()) {
                currentTime++;
                continue;
            }
            Process highestPriority = arrivedProcesses.remove(0);
            for (Process arrivedProcess : arrivedProcesses) {
                for (Process process : processes) {
                    if (arrivedProcess.Name.equals(process.Name)) {
                        process.Priority_Number--;}
                }
            }

            arrivedProcesses.clear();
            processes.remove(highestPriority);
            System.out.println("Executing: " + highestPriority.Name);
            completionTime = currentTime + highestPriority.Burst_Time;
            System.out.println("Completion time: " + completionTime);
            turnaroundTime = completionTime - highestPriority.arrivalTime;
            System.out.println("Turnaround time: " + turnaroundTime);
            totalTurnaroundTime += turnaroundTime;
            waitTime = turnaroundTime - highestPriority.originalBurstTime;
            System.out.println("Waiting time: " + waitTime);
            totalWaitTime += waitTime;
            currentTime += highestPriority.Burst_Time;
        }
        double averageWaitTime = totalWaitTime / size;
        double averageTurnaroundTime = totalTurnaroundTime / size;
        System.out.println("Average Waiting Time: " + averageWaitTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
    }
}