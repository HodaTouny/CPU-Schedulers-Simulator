import java.util.*;

import static java.lang.Math.ceil;

public class AG_Scheduling implements SchedulingAlgorithm {
    Queue<Process> readyQueue = new LinkedList<>();
    Queue<Process> dieQueue = new LinkedList<>();
    Vector<Object> temp = new Vector<>();
    int currentTime = 0;
    int tempQuantum = 0;
    Process Current = null;
    boolean ProcessFinished = false;
    Process oldProcess = null;

    int totalWaitingTime = 0;
    int totalTurnaroundTime = 0;


    public Queue<Process> addArrivedProcesses(int currentTime, Vector<Process> processes, Queue<Process> readyQueue, Process current, Queue<Process> dieQueue) {
        for (Process p : processes) {
            if (currentTime >= p.arrivalTime && !readyQueue.contains(p) && current != p && !dieQueue.contains(p)) {
                readyQueue.add(p);
            }
        }
        return readyQueue;
    }

    public void AG_calculation(Vector<Process> processes) {
        for (Process process : processes) {
            int rand = new Random().nextInt(20);
            if (rand < 10) {
                process.AGFactor = rand + process.arrivalTime + process.Burst_Time;
            } else if (rand == 10) {
                process.AGFactor = process.Priority_Number + process.arrivalTime + process.Burst_Time;
            } else {
                process.AGFactor = 10 + process.arrivalTime + process.Burst_Time;
            }
        }
    }

    public static Vector<Object> Preemptive(Process Current, int currentTime, int tempQuantum) {
        boolean isFinished = false;
        Current.Burst_Time--;
        currentTime++;
        tempQuantum--;
        if (Current.Burst_Time == 0) {
            isFinished = true;
        }
        Vector<Object> temp = new Vector<>();
        temp.add(currentTime);
        temp.add(tempQuantum);
        temp.add(isFinished);
        return temp;
    }

    public static Vector<Object> nonPreemptive(Process current, int currentTime, int tempQuantum,
                                               Queue<Process> dieQueue, Vector<Process> processes) {
        boolean isFinished = false;
        int npTime = (int) ceil(current.quantumTime.get(current.quantumTime.size() - 1) / 2.0);
        if (npTime > current.Burst_Time) {
            npTime = current.Burst_Time;
        }
        currentTime += npTime;
        current.Burst_Time -= npTime;
        if (current.Burst_Time > 0) {
            tempQuantum -= npTime;

        } else {
            current.End_Time=currentTime;
            current.TernARound=current.End_Time-current.arrivalTime;
            current.WaitingTime=current.TernARound-current.originalBurstTime;
//            waitingTimes.put(current, current.WaitingTime);
//            turnaroundTimes.put(current, current.TernARound);

            Duration duration = current.durations.get(current.durations.size()-1);
            duration.setEndTime(currentTime);

            current.quantumTime.add(0);
            dieQueue.add(current);
            processes.remove(current);
            isFinished = true;
        }
        System.out.println("Executing " + current.Name);
        Vector<Object> temp = new Vector<>();
        temp.add(currentTime);
        temp.add(tempQuantum);
        temp.add(isFinished);
        return temp;
    }


    public Vector<Object> updateQuantum(Process current, int tempQuantum,
                                        Queue<Process> dieQueue, Vector<Process> processes) {
        Vector<Object> temp = new Vector<>();
        boolean isFinished = false;
        if (tempQuantum == 0 && current.Burst_Time != 0){
            int meanQuantum = calculateMeanQuantum(processes);
            int newQuantum = (int) Math.ceil(1.1 * meanQuantum);
            current.quantumTime.add(newQuantum);
            tempQuantum = newQuantum;
            isFinished = true;
        } else if (current.Burst_Time==0){
            Current.End_Time=currentTime;
            Current.TernARound=Current.End_Time-Current.arrivalTime;
            Current.WaitingTime=Current.TernARound-Current.originalBurstTime;

            Duration duration = current.durations.get(current.durations.size()-1);
            duration.setEndTime(currentTime);

            current.quantumTime.add(0);
            dieQueue.add(current);
            processes.remove(current);
            tempQuantum = 0;
            isFinished = true;
        }
        else {
            current.quantumTime.add(current.quantumTime.get(current.quantumTime.size() - 1) + tempQuantum);
        }
        temp.add(tempQuantum);
        temp.add(isFinished);
        return temp;
    }

    private static int calculateMeanQuantum(Vector<Process> processes) {
        int sumQuantum = 0;
        for (Process process : processes) {
            sumQuantum += process.quantumTime.get(process.quantumTime.size() - 1);
        }
        return sumQuantum / processes.size();
    }

    public boolean checkIfAnyProcessSmallerThanCurrent(Process current, Queue<Process> readyQueue) {
        for (Process p : readyQueue) {
            if (current.AGFactor > p.AGFactor) {
                return true;
            }
        }
        return false;
    }

    public static Process findSmallestProcess(Queue<Process> readyQueue) {
        if (readyQueue.isEmpty()) {
            return null;
        }
        Process smallestProcess = readyQueue.peek();
        for (Process process : readyQueue) {
            if (process.AGFactor < smallestProcess.AGFactor) {
                smallestProcess = process;
            }
        }
        readyQueue.remove(smallestProcess);
        return smallestProcess;
    }

    @Override
    public void CPUScheduling(Vector<Process> processes) {
        int Size=processes.size();
         AG_calculation(processes);
        while (!readyQueue.isEmpty() || !processes.isEmpty()) {
            readyQueue = addArrivedProcesses(currentTime, processes, readyQueue, Current, dieQueue);
            if (Current == null && readyQueue.isEmpty()) {
                currentTime++;
                continue;
            } else if (Current == null && !readyQueue.isEmpty()) {
                Current = findSmallestProcess(readyQueue);
                tempQuantum = Current.quantumTime.get(Current.quantumTime.size() - 1);
            } else {
                if (ProcessFinished) {
                    oldProcess = Current;
                    if (!dieQueue.contains(Current)){
                        readyQueue.add(Current);
                        Duration duration = Current.durations.get(Current.durations.size()-1);
                        duration.setEndTime(currentTime);
                    }
                    Current = readyQueue.remove();
                    tempQuantum = Current.quantumTime.get(Current.quantumTime.size() - 1);
                } else {
                    oldProcess = Current;
                    Process p = findSmallestProcess(readyQueue);
                    if (p != null && p.AGFactor < Current.AGFactor) {
                        Current = p;
                        tempQuantum = Current.quantumTime.get(Current.quantumTime.size() - 1);
                    }if (oldProcess != Current) {
                        readyQueue.add(oldProcess);
                        readyQueue.remove(Current);
                    }
                    if(oldProcess != Current) {
                        Duration duration = oldProcess.durations.get(oldProcess.durations.size() - 1);
                        duration.setEndTime(currentTime);
                    }
                }
            }

            if (Current.getBurstTime() == Current.originalBurstTime) {
                Current.Start_Time = currentTime;
            }

            if(oldProcess != Current){
                Duration duration = new Duration();
                duration.setStartTime(currentTime);
                Current.durations.add(duration);
            }

            temp = nonPreemptive(Current, currentTime, tempQuantum, dieQueue, processes);
            currentTime = (int) temp.get(0);
            tempQuantum = (int) temp.get(1);
            ProcessFinished = (boolean) temp.get(2);
            if (!ProcessFinished) {
                readyQueue = addArrivedProcesses(currentTime, processes, readyQueue, Current, dieQueue);
                while (!checkIfAnyProcessSmallerThanCurrent(Current, readyQueue) && !ProcessFinished) {
                    temp = Preemptive(Current, currentTime, tempQuantum);
                    currentTime = (int) temp.get(0);
                    tempQuantum = (int) temp.get(1);
                    ProcessFinished = (boolean) temp.get(2);
                    if (tempQuantum == 0) {
                        ProcessFinished = true;
                        break;
                    }
                    readyQueue = addArrivedProcesses(currentTime, processes, readyQueue, Current, dieQueue);
                }
                Vector<Object> temp = updateQuantum(Current, tempQuantum, dieQueue, processes);
                tempQuantum = (int) temp.get(0);
                ProcessFinished = (boolean) temp.get(1);
            }

        }

        for(Process p : dieQueue){
            System.out.println('\n'+ p.Name );
            System.out.println( "turn around: " + p.TernARound);
            System.out.println("wait time: "+ p.WaitingTime);
            System.out.println("Quantum history:");
            System.out.println( p.quantumTime);
            totalTurnaroundTime+=p.TernARound;
            totalWaitingTime+=p.WaitingTime;
        }
        System.out.println("total wait around time: "+ (float)totalWaitingTime/Size);
        System.out.println("total turn around time: "+ (float)totalTurnaroundTime/Size);

    }
}