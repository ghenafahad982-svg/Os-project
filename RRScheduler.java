import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RRScheduler {

    private final int quantum = 5;

    public void schedule(Queue<Process> readyQueue) {

        Queue<Process> queue = new LinkedList<>();
        List<Process> processes = new ArrayList<>();

        // copy processes and reset values
        for (Process p : readyQueue) {

            p.remainingTime = p.burstTime;
            p.startTime = -1;

            queue.add(p);
            processes.add(p);
        }

        int currentTime = 0;

        System.out.println("\n===== Round Robin =====");
        System.out.println("Gantt Chart:");

        while (!queue.isEmpty()) {

            Process p = queue.poll();

            // first time execution
            if (p.startTime == -1) {
                p.startTime = currentTime;
            }

            int execTime = Math.min(quantum, p.remainingTime);

            System.out.print("| P" + p.id + " [" + currentTime);

            currentTime += execTime;
            p.remainingTime -= execTime;

            System.out.print(" - " + currentTime + "] ");

            if (p.remainingTime > 0) {

                // not finished → back to queue
                queue.add(p);

            } else {

                // finished → calculate times
                p.finishTime = currentTime;
                p.turnaroundTime = p.finishTime - p.arrivalTime;
                p.waitingTime = p.turnaroundTime - p.burstTime;
            }
        }

        System.out.println("|");

        printTable(processes);
    }

    private void printTable(List<Process> processes) {

        double totalWaiting = 0;
        double totalTurnaround = 0;

        System.out.println("\nID\tBurst\tStart\tFinish\tWaiting\tTurnaround");

        for (Process p : processes) {

            totalWaiting += p.waitingTime;
            totalTurnaround += p.turnaroundTime;

            System.out.println(p.id + "\t" + p.burstTime + "\t" + p.startTime
                    + "\t" + p.finishTime + "\t" + p.waitingTime
                    + "\t" + p.turnaroundTime);
        }

        System.out.println("\nAverage Waiting = " + (totalWaiting / processes.size()));
        System.out.println("Average Turnaround = " + (totalTurnaround / processes.size()));
    }
}