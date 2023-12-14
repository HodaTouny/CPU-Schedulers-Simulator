import java.util.*;

public class SRTF_Scheduling implements SchedulingAlgorithm {
    Vector<Process> Arrived_Processes = new Vector<>();
    Vector<Process> Answer = new Vector<>();
    Process OldProcess = null;
    Process currentProcess = null;
    Vector<Process> finalAns = new Vector<>();
    public int Current_Time = 0;
    @Override
    public void CPUScheduling(Vector<Process> processes) {
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        while (!processes.isEmpty() || !Arrived_Processes.isEmpty()) {
            Iterator<Process> iterator = processes.iterator();
            while (iterator.hasNext()) {
                Process process = iterator.next();
                if (Current_Time >= process.arrivalTime) {
                    Arrived_Processes.add(process);
                    iterator.remove();
                }
            }
            Arrived_Processes = sortProcesses(Arrived_Processes);

            if (!Arrived_Processes.isEmpty()) {
                OldProcess = currentProcess;
                currentProcess = Arrived_Processes.get(0);
                if(!Answer.contains(currentProcess)) {
                    currentProcess.Start_Time = Current_Time;
                }
                currentProcess.Burst_Time--;
                Answer.add(currentProcess);
                if (currentProcess.Burst_Time == 0) {
                    Arrived_Processes.remove(currentProcess);
                    currentProcess.End_Time = Current_Time +1;
                    int TurnAround = currentProcess.End_Time - currentProcess.arrivalTime;
                    int waitingTime = TurnAround - currentProcess.originalBurstTime;
                    currentProcess.TernARound = TurnAround;
                    currentProcess.WaitingTime = waitingTime;
                    finalAns.add(currentProcess);
                }

                for (Process process : Arrived_Processes) {
                    process.AgingFactor++;
                }
            }
            Current_Time++;
        }

        if(!Answer.isEmpty()){
            print(Answer);
        }else{
            System.out.println("No Process Enter!!");
        }
    }

    public static Vector<Process> sortProcesses(Vector<Process> processes) {
        Vector<Process> sortedProcesses = new Vector<>(processes);
        Collections.sort(sortedProcesses, Comparator.comparing(Process::getBurstTime).thenComparing(Process::getAgingFactor));
        return sortedProcesses;
    }

    public void print(Vector<Process> answer) {
        int TO = 1,From=0;
        float TotalTurnAround=0,TotalWaiting=0;
        for (int i = 1; i < answer.size(); i++) {
            if (answer.get(i).Name.equals(answer.get(i - 1).Name)) {
                TO++;
            } else {
                System.out.println(" - " + answer.get(i - 1).Name + " " +From+"  ->  "+TO);
                Duration duration = new Duration();
                duration.setStartTime(From); duration.setEndTime(TO);
                int index = finalAns.indexOf(answer.get(i-1));
                finalAns.get(index).durations.add(duration);
                From = TO;
                TO++;
            }
        }
        System.out.println(" - " + answer.lastElement().Name + " "+From+"  ->  "+TO);
        Duration duration = new Duration();
        duration.setStartTime(From); duration.setEndTime(TO);
        int index = finalAns.indexOf(answer.lastElement());
        finalAns.get(index).durations.add(duration);


        for(Process p:finalAns){
            TotalWaiting += p.WaitingTime;
            TotalTurnAround += p.TernARound;

        }
        System.out.println("TurnAround Time For Each Process");
        for(Process p:finalAns){
            System.out.println(p.Name + " " + p.TernARound);
        }
        System.out.println("Waiting Time For Each Process");
        for(Process p:finalAns){
            System.out.println(p.Name + " " + p.WaitingTime);
        }
        System.out.println("Average TurnAround Time : " + (float) TotalTurnAround/finalAns.size());
        System.out.println("Average Waiting Time : " + (float) TotalWaiting/finalAns.size());

    }
}
