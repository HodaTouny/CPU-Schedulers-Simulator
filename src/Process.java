public class Process {
    public  String name;
    public  String color;
    public  int arrivalTime;
    public  int burstTime;
    public int priorityNumber;
    public int originalBurstTime;
    public int agingFactor;


    Process(String name, String color, int arrivalTime, int burstTime, int priority, int quantum) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priority;
        this.originalBurstTime = burstTime;

    }
}
