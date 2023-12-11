import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class PriorityScheduling implements SchedulingAlgorithm {
    int contextSwitchingTime;

    public PriorityScheduling(int contextSwitchingTime) {
        this.contextSwitchingTime = contextSwitchingTime;
    }

    @Override
    public void CPUScheduling(Vector<Process> processes) {
        Collections.sort(processes, Comparator.comparingInt(p -> p.priorityNumber));
        int size = processes.size();
        int currentTime = 0;
        double totalWaitTime = 0;
        double totalTurnaroundTime = 0;
        int completionTime;
        int waitTime;
        int turnaroundTime;

        while (!processes.isEmpty()) {
            Process highestPriority = processes.get(0);
            if (highestPriority.arrivalTime <= currentTime) {
                processes.remove(highestPriority);
                System.out.println("Executing: " + highestPriority.name);
                completionTime = currentTime + highestPriority.burstTime;
                System.out.println("Completion time: " + completionTime);
                turnaroundTime = completionTime - highestPriority.arrivalTime;
                System.out.println("Turnaround time: " + turnaroundTime);
                totalTurnaroundTime += turnaroundTime;
                waitTime = turnaroundTime - highestPriority.originalBurstTime;
                System.out.println("Waiting time: " + waitTime);
                totalWaitTime += waitTime;
                currentTime += highestPriority.burstTime;
                continue;
            }

            currentTime++;
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime) {
                    process.priorityNumber--;
                }
            }
        }

        System.out.println("Average Wait Time: " + (float) totalWaitTime / size);
        System.out.println("Average Turnaround Time: " + (float) totalTurnaroundTime / size);
    }
}
