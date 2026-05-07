import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class PriorityScheduler {

    public void schedule(Queue<Process> readyQueue) {

        // Copy processes from the ready queue to:
        // 1. a list of remaining processes (all from the queue)
        List<Process> remaining = new ArrayList<>(readyQueue);
        //2. a list of comleting processes (empty)
        List<Process> completed = new ArrayList<>();
        
        int currentTime = 0;

        System.out.println("\n===== Priority Scheduling (Non-Preemptive) =====");
        System.out.println("Gantt Chart:");

        // Loop until all processes are executed
        while (!remaining.isEmpty()) {
            
            // N is the number of processes currently in the ready queue
            int currentN = remaining.size(); 

            // Update waiting times, apply aging, and check for starvation before sorting
            for (Process p : remaining) {
                // Assuming all arrive at 0, wait time is the current time
                p.waitingTime = currentTime - p.arrivalTime; 

                // Aging: Every 4 ms, increase priority level (decrease priority number) by 1 
                int agingAmount = p.waitingTime / 4;
                p.priority = Math.max(1, p.originalPriority - agingAmount); // max is userd to to protect the priority from going below 1

                // Starvation: Process waits more than (N x 5 ms)
                if (p.waitingTime > (currentN * 5)) {
                    p.isStarved = true;
                }
            }

            // Sort by current priority (smallest integer = highest priority) 
            // If priorities are equal, sort by ID to represent arrival order 
            for (int i = 0; i < remaining.size(); i++) {
                for (int j = i + 1; j < remaining.size(); j++) {
                    
                    Process p1 = remaining.get(i);
                    Process p2 = remaining.get(j);
                    
                    boolean needsSwap = false;

                    // If p1 has a bigger priority number, it should be pushed back
                    if (p1.priority > p2.priority) {
                        needsSwap = true;
                    } 
                    // If the same, push back the one with the bigger ID, as orderd in job.txt
                    else if (p1.priority == p2.priority && p1.id > p2.id) {
                        needsSwap = true;
                    }

                    // If the rules say they are out of order, swap their positions in the list
                    if (needsSwap) {
                        remaining.set(i, p2);
                        remaining.set(j, p1);
                    }
                }
            }  

            // Save process with the highest priority
            Process p = remaining.remove(0);

            // Execute process (Non-Preemptive = runs until burst time ends) 
            p.startTime = currentTime;
            System.out.print("| P" + p.id + " [" + currentTime);

            currentTime += p.burstTime;
            
            p.finishTime = currentTime;
            p.turnaroundTime = p.finishTime - p.arrivalTime;

            System.out.print(" - " + currentTime + "] ");
            
            completed.add(p);
        }

        System.out.println("|");

        printTable(completed);
    }

    private void printTable(List<Process> processes) {

        double totalWaiting = 0;
        double totalTurnaround = 0;

        // Added a Starvation column specifically required for the Priority output
        System.out.println("\nID\tBurst\tStart\tFinish\tWaiting\tTurnaround\tStarved?");

        for (Process p : processes) {

            totalWaiting += p.waitingTime;
            totalTurnaround += p.turnaroundTime;

            String starvedStr = p.isStarved ? "Yes" : "No";

            System.out.println(p.id + "\t" + p.burstTime + "\t" + p.startTime
                    + "\t" + p.finishTime + "\t" + p.waitingTime
                    + "\t" + p.turnaroundTime + "\t\t" + starvedStr);
        }

        System.out.println("\nAverage Waiting = " + (totalWaiting / processes.size()));
        System.out.println("Average Turnaround = " + (totalTurnaround / processes.size()));
    }
}