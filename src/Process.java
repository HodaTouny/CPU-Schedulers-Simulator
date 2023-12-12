public class Process {
<<<<<<< Updated upstream
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
=======
    public String Name;
    public String Color;
    public int arrivalTime;
    public int Burst_Time;
    public int Priority_Number;
    public int originalBurstTime;
    public int AgingFactor;
    public int Start_Time;
    public int End_Time;


    Process(String name, String color, int arrivalTime, int burstTime, int priority, int quantum,int StartTime,int EndTime) {
        this.Name = name;
        this.Color = color;
>>>>>>> Stashed changes
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priority;
        this.originalBurstTime = burstTime;
        this.Start_Time = StartTime;
        this.End_Time = EndTime;

    }

    public int getBurstTime() {
        return Burst_Time;
    }


    public int getPriorityNumber() {
        return Priority_Number;
    }
}
