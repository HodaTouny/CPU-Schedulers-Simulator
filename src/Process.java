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

    Process(String name, String color, int arrivalTime, int burstTime, int priority, int quantum,int StartTime,int EndTime, int AGFactor) {
        this.Name = name;
        this.Color = color;
        this.arrivalTime = arrivalTime;
        this.Burst_Time = burstTime;
        this.Priority_Number = priority;
        this.originalBurstTime = burstTime;
        this.AgingFactor =0;
        this.TernARound = 0;
        this.WaitingTime =0;
        this.Start_Time = StartTime;
        this.End_Time = EndTime;
        this.quantumTime = new Vector<>();
        this.quantumTime.add(quantum);
        this.AGFactor = AGFactor;

    }

    public int getBurstTime() {
        return Burst_Time;
    }


    public int getAgingFactor() {
        return AgingFactor;
    }
}
