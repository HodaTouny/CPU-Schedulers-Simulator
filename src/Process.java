import java.util.Vector;

public class Process {
    public String Name;
    public String Color;
    public int arrivalTime;
    public int Burst_Time;
    public int Priority_Number;
    public int originalBurstTime;
    public int AgingFactor;
    public int Start_Time;
    public int End_Time;
    public int TernARound;
    public int WaitingTime;
    public int AGFactor;
    public Vector<Integer> quantumTime;
    public Vector<Duration> durations;

    Process(String name, String color, int arrivalTime, int burstTime, int priority, int quantum) {
        this.Name = name;
        this.Color = color;
        this.arrivalTime = arrivalTime;
        this.Burst_Time = burstTime;
        this.Priority_Number = priority;
        this.originalBurstTime = burstTime;
        this.quantumTime = new Vector<>();
        this.quantumTime.add(quantum);
        this.AgingFactor =0;
        this.TernARound = 0;
        this.WaitingTime =0;
        this.Start_Time = 0;
        this.End_Time = 0;
        this.AGFactor = 0;
        this.durations = new Vector<>();

    }

    public int getBurstTime() {
        return Burst_Time;
    }


    public int getAgingFactor() {
        return AgingFactor;
    }
}
