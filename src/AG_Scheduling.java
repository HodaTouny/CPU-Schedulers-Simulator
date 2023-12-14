import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.ceil;

public class AG_Scheduling implements SchedulingAlgorithm {
    Queue<Process> readyQueue = new LinkedList<>();
    Queue<Process> newArrived = new LinkedList<>();
    Queue<Process> dieQueue = new LinkedList<>();
    Vector<Object> temp = new Vector<>();
    int currentTime = 0;
    int tempQuantum = 0;
    Process Current= null;
    boolean ProcessFinished = false;
//    @Override
//    public void CPUScheduling(Vector<Process> processes) {
//            //AG_calc(processes);
//        processes = processes.stream().sorted(Comparator.comparingInt(o -> o.arrivalTime)).collect(Collectors.toCollection(Vector::new));
//            while(!processes.isEmpty() || !readyQueue.isEmpty()){
//                newArrived = addArrivedProcesses(currentTime, processes,readyQueue);
//                if(newArrived.peek()!=null&&readyQueue!=null&&newArrived.peek().AGFactor<Current.AGFactor){
//                    Current= newArrived.peek();
//                    newArrived.remove(Current);
//                    for(Process p: newArrived){
//                        readyQueue.add(p);
//                    }
//                }
//                else if (newArrived.peek()!=null&&readyQueue!=null&&newArrived.peek().AGFactor>readyQueue.peek().AGFactor){
//                    Current= readyQueue.poll();
//                    for(Process p: newArrived){
//                        readyQueue.add(p);
//                    }
//                } else {
//                    Current= readyQueue.poll();
//                }
//                newArrived.clear();
//                tempQuantum = Current.quantumTime.get(Current.quantumTime.size() - 1);
//                temp =nonPreemptive( Current, currentTime, tempQuantum, readyQueue, dieQueue, processes);
//                currentTime = (int) temp.get(0);
//                tempQuantum = (int) temp.get(1);
//                while(true) {
//                    readyQueue = addArrivedProcesses(currentTime, processes, readyQueue);
//                    if (!readyQueue.isEmpty() && Current.AGFactor >= readyQueue.peek().AGFactor) {
//                        checkQuantum(Current, tempQuantum, readyQueue, dieQueue, processes);
//                        break;
//
//                    } else {
//                        Current.Burst_Time--;
//                        currentTime++;
//                        tempQuantum--;
//                        if (Current.Burst_Time == 0) {
//                            Current.quantumTime.add(Current.quantumTime.get(Current.quantumTime.size() - 1) + tempQuantum);
//                            dieQueue.add(Current);
//                            processes.remove(Current);
//                            break;
//                        }
//
//                    }
//                }
//            }
//        }
    public Queue<Process> addArrivedProcesses(int currentTime, Vector<Process> processes,Queue<Process> readyQueue,Process current) {
        for (Process p : processes) {
            if (currentTime >= p.arrivalTime && !readyQueue.contains(p)&& current != p) {
                readyQueue.add(p);
            }
        }
        return readyQueue;
    }

    public void AG_calc(Vector<Process> processes) {
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
 public static Vector<Object> Preemptive(Process Current, int currentTime, int tempQuantum, Queue<Process> readyQueue,
                                         Queue<Process> dieQueue, Vector<Process> processes) {
     boolean isFinished = false;
     Current.Burst_Time--;
     currentTime++;
     tempQuantum--;
     if (Current.Burst_Time == 0) {
         Current.quantumTime.add(0);
         dieQueue.add(Current);
         processes.remove(Current);
         readyQueue.remove(Current);
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
        boolean isFinished=false;
        int npTime = (int) ceil(current.quantumTime.get(current.quantumTime.size() - 1) / 2.0);
        if(npTime>current.Burst_Time){
            npTime = current.Burst_Time;
        }
        currentTime += npTime;
        current.Burst_Time -= npTime;
        if (current.Burst_Time > 0) {
            tempQuantum -= npTime;

        } else {
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


    public  int checkQuantum(Process current, int tempQuantum, Queue<Process> readyQueue,
                                    Queue<Process> dieQueue, Vector<Process> processes) {
        if(tempQuantum == 0) {
            if (current.Burst_Time == 0) {
                current.quantumTime.add(0);
                dieQueue.add(current);
                processes.remove(current);

            } else {
                int meanQuantum = calculateMeanQuantum(processes);
                int newQuantum = (int) Math.ceil(1.1 * meanQuantum);
                current.quantumTime.add(newQuantum);
                readyQueue.add(current);
                tempQuantum = newQuantum;
            }
        }else{
                current.quantumTime.add(current.quantumTime.get(current.quantumTime.size() - 1) + tempQuantum);
                readyQueue.add(current);
            }
        return tempQuantum;
    }

    private static int calculateMeanQuantum(Vector<Process> processes) {
        int sumQuantum = 0;
        for (Process process : processes) {
            sumQuantum += process.quantumTime.get(process.quantumTime.size() - 1);
        }
        return sumQuantum / processes.size();
    }

    public boolean checkIfAnyProcessSmallerThanCurrent(Process current,Queue<Process>readyQueue){
        for(Process p:readyQueue){
            if(current.AGFactor>p.AGFactor){
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
            if (process.AGFactor< smallestProcess.AGFactor) {
                smallestProcess = process;
            }
        }
        readyQueue.remove(smallestProcess);
        return smallestProcess;
    }

    @Override
    public void CPUScheduling(Vector<Process> processes) {
        //AG_calc(processes);
        while (!readyQueue.isEmpty() || !processes.isEmpty()) {
            readyQueue = addArrivedProcesses(currentTime, processes, readyQueue, Current);

            if(Current == null && readyQueue.isEmpty()){
                currentTime++;
                continue;
            }else if(Current == null && !readyQueue.isEmpty()){
                Current = findSmallestProcess(readyQueue);
            }else{
                if (ProcessFinished) {
                    Current = readyQueue.poll();
                } else {
                    for (Process p : readyQueue) {
                        if (p.AGFactor < Current.AGFactor) {
                            Current = p;
                        }
                    }

                }
            }
            temp = nonPreemptive(Current, currentTime, tempQuantum, dieQueue, processes);
            currentTime = (int) temp.get(0);
            tempQuantum = (int) temp.get(1);
            ProcessFinished = (boolean) temp.get(2);
            if (!ProcessFinished) {
                readyQueue = addArrivedProcesses(currentTime, processes, readyQueue, Current);
                while (!checkIfAnyProcessSmallerThanCurrent(Current, readyQueue) && !ProcessFinished) {
                    temp = Preemptive(Current, currentTime, tempQuantum, readyQueue, dieQueue, processes);
                    currentTime = (int) temp.get(0);
                    tempQuantum = (int) temp.get(1);
                    ProcessFinished = (boolean) temp.get(2);
                    readyQueue = addArrivedProcesses(currentTime, processes, readyQueue, Current);
                }
            }
        }
    }
}

