import java.util.Vector;

public class Main {
//    public static void addd(Vector<String> a){
//        a.remove("1");
//
//    }
    public static void main(String[] args) {

        Vector<Process> processes = new Vector<>();
        Process p1 = new Process("p1", "0", 0, 17, 0, 4, 0, 0, 25);
        Process p2 = new Process("p2", "0", 3, 6, 0, 4, 0, 0, 17);
        Process p3 = new Process("p3", "0", 4, 10, 0, 4, 0, 0, 16);
        Process p4 = new Process("p4", "0", 29, 4, 0, 4, 0, 0, 43);
        processes.add(p1);
        processes.add(p2);
        processes.add(p3);
        processes.add(p4);
        SchedulingAlgorithm sjf = new AG_Scheduling();
        sjf.CPUScheduling(processes);

    }

}