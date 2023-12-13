import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.ceil;

public class AG_Scheduling implements SchedulingAlgorithm {
    Queue<Process> readyQueue = new LinkedList<>();
    Queue<Process> newArrived = new LinkedList<>();
    Queue<Process> dieQueue = new LinkedList<>();
    Vector<Integer> temp = new Vector<>();
    int currentTime = 0;
    int tempQuantum = 0;
    Process Current= null;
    boolean flag = false;
    boolean flagIf = false;
    @Override
    public void CPUScheduling(Vector<Process> processes) {
        processes = processes.stream().sorted(Comparator.comparingInt(o -> o.arrivalTime)).collect(Collectors.toCollection(Vector::new));
        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            newArrived = addArrivedProcesses(currentTime, processes, readyQueue, dieQueue, Current);
            if (!newArrived.isEmpty()) {
                Current = newArrived.peek();
                newArrived.remove(Current);
                for (Process p : newArrived) {
                    readyQueue.add(p);
                }
            } else if (!readyQueue.isEmpty()) {
                Current = readyQueue.poll();
            }
        }
        newArrived.clear();
        tempQuantum = Current.quantumTime.get(Current.quantumTime.size() - 1);
        temp = nonPreemptive(Current, currentTime, tempQuantum, readyQueue, dieQueue, processes);
        currentTime = temp.get(0);
        tempQuantum = temp.get(1);
        while (true) {
            newArrived = addArrivedProcesses(currentTime, processes, readyQueue, dieQueue, Current);
            if ((!newArrived.isEmpty() && Current.AGFactor >= newArrived.peek().AGFactor) || tempQuantum == 0) {
                UpdateQuantum(Current, tempQuantum, readyQueue, dieQueue, processes);
                flagIf = true;
                break;
            } else if (newArrived.isEmpty()) {
                for (Process p : readyQueue) {
                    if (Current.AGFactor >= p.AGFactor) {
                        Current = p;
                        flagIf = true;
                        break;
                    }
                }
                break;
            }
            if (!flagIf) {
                Current.Burst_Time--;
                currentTime++;
                tempQuantum--;
                if (Current.Burst_Time == 0) {
                    Current.quantumTime.add(0);
                    dieQueue.add(Current);
                    processes.remove(Current);
                    System.out.println("quantum didn't finish and done");
                    break;
                }
            }
        }
    }

    public Queue<Process> addArrivedProcesses(int currentTime, Vector<Process> processes,Queue<Process> readyQueue,Queue<Process> dieQueue,Process Current) {
        Queue<Process> arrivedProcesses = new LinkedList<>();
        for (Process p : processes) {
            if (currentTime >= p.arrivalTime &&!readyQueue.contains(p)&&!dieQueue.contains(p)&&!p.equals(Current)) {
                arrivedProcesses.add(p);
            }
        }
        return arrivedProcesses;
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
    public static Vector<Integer>  nonPreemptive(Process current, int currentTime, int tempQuantum, Queue<Process> readyQueue,
                                      Queue<Process> dieQueue, Vector<Process> processes) {
        int npTime = (int) ceil(current.quantumTime.get(current.quantumTime.size() - 1) / 2.0);
        currentTime += npTime;
        current.Burst_Time -= npTime;
        System.out.println("Executing " + current.Name);
        if (current.Burst_Time > 0) {
            tempQuantum -= npTime;

        } else {
            current.quantumTime.add(0);
            dieQueue.add(current);
            processes.remove(current);
        }
        Vector<Integer> temp = new Vector<>();
        temp.add(currentTime);
        temp.add(tempQuantum);
        return temp;
    }
    public  int UpdateQuantum(Process current, int tempQuantum, Queue<Process> readyQueue,
                              Queue<Process> dieQueue, Vector<Process> processes) {
        if(tempQuantum == 0) {
            if (current.Burst_Time == 0) {
                current.quantumTime.add(0);
                dieQueue.add(current);
                processes.remove(current);
                System.out.println("quantum finished and  done");


            } else {
                int meanQuantum = calculateMeanQuantum(processes);
                int newQuantum = (int) Math.ceil(1.1 * meanQuantum);
                current.quantumTime.add(newQuantum);
                readyQueue.add(current);
                tempQuantum = newQuantum;
                System.out.println("quantum finished and not done");
            }
        }else{
                current.quantumTime.add(current.quantumTime.get(current.quantumTime.size() - 1) + tempQuantum);
                readyQueue.add(current);
            System.out.println("quantum didn't finish and not done");

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
}

