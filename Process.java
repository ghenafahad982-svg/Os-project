public class Process { // Represents a process (PCB)
    
    int id; // Process ID
    int burstTime; // Total CPU burst time
    int remainingTime; // Remaining execution time
    int arrivalTime; // Arrival time (all = 0)
    int priority; // Process priority (smaller = higher priority)
    int memory; // Required memory in MB

    int startTime; // Time when execution starts
    int finishTime; // Time when execution ends
    int waitingTime; // Time spent waiting in queue
    int turnaroundTime; // Total time from arrival to completion
    String state; // Current state (New, Ready, Running, etc.)

    int originalPriority; // To remember the starting priority during aging
    boolean isStarved;    // Flag to mark if the process suffered starvation

    // Constructor to initialize process
    public Process(int id, int burstTime, int priority, int memory) {
        this.id = id; // Set process ID
        this.burstTime = burstTime; // Set burst time
        this.remainingTime = burstTime; // Initially equals burst time
        this.arrivalTime = 0; // All processes arrive at time 0
        this.priority = priority; // Set priority
        this.originalPriority = priority;
        this.memory = memory; // Set memory requirement

        this.startTime = -1; // Not started yet
        this.finishTime = 0; // Not finished yet
        this.waitingTime = 0; // Initial waiting time
        this.turnaroundTime = 0; // Initial turnaround time
        this.state = "New"; // Initial state

        this.isStarved = false; // By default
    }

    // Display process basic info
    public void displayInfo() {
        System.out.println("Process ID: " + id
                + ", Burst Time: " + burstTime
                + ", Priority: " + priority
                + ", Memory: " + memory + " MB");
    }
}