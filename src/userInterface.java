import java.util.Scanner;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

public class userInterface {
    public void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();
        System.out.print("Enter Process Quantum: ");
        int quantum = scanner.nextInt();
        System.out.print("Enter the Context Switching: ");
        int context_switching = scanner.nextInt();

        Vector<Process> originalProcesses = new Vector<>();

        for (int i = 1; i <= numProcesses; i++) {
            System.out.println("Enter details for Process " + i + ":");
            System.out.print("Process Name: ");
            String name = scanner.next();

            System.out.print("Process Arrival Time: ");
            int arrivalTime = scanner.nextInt();

            System.out.print("Process Burst Time: ");
            int burstTime = scanner.nextInt();

            System.out.print("Process Priority: ");
            int priority = scanner.nextInt();

            String color = "0";
            Process process = new Process(name, color, arrivalTime, burstTime, priority, quantum);

            originalProcesses.add(process);
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

           Vector<Process> processesCopy = new Vector<>(originalProcesses);

            SchedulingAlgorithm scheduler = null;
            switch (choice) {
                case 1:
                    scheduler = new SJF_Scheduling(context_switching);
                    break;
                case 2:
                    scheduler = new SRTF_Scheduling();
                    break;
                case 3:
                    scheduler = new PriorityScheduling();
                    break;
                case 4:
                    scheduler = new AG_Scheduling();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    continue;
            }

            scheduler.CPUScheduling(processesCopy);
        }

        System.out.println("Exiting the scheduler.");
    }
}
