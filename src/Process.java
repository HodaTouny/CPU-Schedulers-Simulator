public class Process {
    public  String Name;
    public  String Color;
    public  int arrivalTime;
    public  int  Burst_Time;
    public int  Priority_Number;
    public int originalBurstTime;
    public int AgingFactor;



    Process(String name, String color, int arrivalTime, int burstTime, int priority, int quantum) {
        this.Name = name;
        this.Color = color;
        this.arrivalTime = arrivalTime;
        this.Burst_Time = burstTime;
        this.Priority_Number = priority;
        this.originalBurstTime = burstTime;

    }
}
