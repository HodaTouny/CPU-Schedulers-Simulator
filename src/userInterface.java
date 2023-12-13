import java.util.Scanner;
import java.util.Vector;

public class userInterface{
    public static void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();
//        System.out.print("Enter the round-robin quantum: ");
//        int roundRobinQuantum = scanner.nextInt();
//        System.out.print("Enter the context switching time: ");
//        int contextSwitchingTime = scanner.nextInt();

        Vector<Process>processes=new Vector<>();
        for (int i = 1; i <= numProcesses; i++) {
            System.out.println("Enter details for Process " + i + ":");
            System.out.print("Process Name: ");
            String name = scanner.next();
//
//            System.out.print("Process Color: ");
            String color ="0";

            System.out.print("Process Arrival Time: ");
            int arrivalTime = scanner.nextInt();

            System.out.print("Process Burst Time: ");
            int burstTime = scanner.nextInt();
//
            System.out.print("Process Priority: ");
            int priority= scanner.nextInt();
//
//            System.out.print("Process Quantum: ");
            int quantum=0;
            int StartTime = 0;
            int EndTime =0;
            Process process = new Process(name, color, arrivalTime, burstTime, priority, quantum,StartTime,EndTime,0);

            processes.add(process);
        }
        while (true) {
            System.out.println("\nChoose a scheduling algorithm:");
            System.out.println("1. Non-Preemptive Shortest Job First (SJF)");
            System.out.println("2. Shortest Remaining Time First (SRTF)");
            System.out.println("3. Non-preemptive Priority Scheduling");
            System.out.println("4. AG Scheduling");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();

            if (choice == 5) {
                break;
            }

            SchedulingAlgorithm scheduler = null;
            switch (choice) {
                case 1:
                    scheduler = new SJF_Scheduling(0);
                    break;
                case 2:
                    scheduler = new SRTF_Scheduling();
                    break;
                case 3:
                    scheduler = new PriorityScheduling();
                    break;
                case 4:
                    // scheduler = new AG_Scheduling();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    continue;
            }

            scheduler.CPUScheduling(processes);
        }

        System.out.println("Exiting the scheduler.");
    }



}