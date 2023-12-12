import java.util.*;

public class SRTF_Scheduling implements SchedulingAlgorithm {
    Vector<Process> Arrived_Processes = new Vector<>();
    Vector<Process> Answer = new Vector<>();
    public int Current_Time = 0;
    @Override
    public void CPUScheduling(Vector<Process> processes) {
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

<<<<<<< Updated upstream

        while (cntCompletedProcesses < processes.size()) {
            Process Lowest_Burst = null;

            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && process.burstTime > 0) {
                    if (Lowest_Burst == null ||
                            process.burstTime < Lowest_Burst.burstTime ||
                            (process.burstTime == Lowest_Burst.burstTime && process.agingFactor > Lowest_Burst.agingFactor)) {
                        Lowest_Burst = process;
                    }
=======
        while (!processes.isEmpty() || !Arrived_Processes.isEmpty()) {
            Iterator<Process> iterator = processes.iterator();
            while (iterator.hasNext()) {
                Process process = iterator.next();
                if (Current_Time == process.arrivalTime) {
                    Arrived_Processes.add(process);
                    iterator.remove();
>>>>>>> Stashed changes
                }
            }

            Arrived_Processes = sortProcesses(Arrived_Processes);
            if (!Arrived_Processes.isEmpty()) {
                Process currentProcess = Arrived_Processes.get(0);
                if(!Answer.contains(currentProcess)) {
                    currentProcess.Start_Time = Current_Time;
                }
                currentProcess.Burst_Time--;
                Answer.add(currentProcess);
                if (currentProcess.Burst_Time == 0) {
                    Arrived_Processes.remove(currentProcess);
                    currentProcess.End_Time = Current_Time;
                }
                for (Process process : Arrived_Processes) {
                    process.Priority_Number++;
                }
            }
            Current_Time++;
        }

        print(Answer);
    }

    public static Vector<Process> sortProcesses(Vector<Process> processes) {
        Vector<Process> sortedProcesses = new Vector<>(processes);

        Collections.sort(sortedProcesses, Comparator.comparing(Process::getBurstTime).thenComparing(Process::getPriorityNumber));

        return sortedProcesses;
    }

    public void print(Vector<Process> answer) {
        int TO = 1,From=0;
        for (int i = 1; i < answer.size(); i++) {
            if (answer.get(i).Name.equals(answer.get(i - 1).Name)) {
                TO++;
            } else {
<<<<<<< Updated upstream
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
=======
                System.out.println(" - " + answer.get(i - 1).Name + " " +From+"  ->  "+TO);
                From = TO;
                TO++;
            }
        }
        System.out.println(" - " + answer.lastElement().Name + " "+From+"  ->  "+TO);
>>>>>>> Stashed changes
    }
}
