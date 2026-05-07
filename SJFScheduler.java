import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class SJFScheduler {

    public void schedule(Queue<Process> readyQueue) {

        // copy processes from queue to list
        List<Process> processes = new ArrayList<>(readyQueue);

        // sort by shortest burst time
        processes.sort(Comparator
        .comparingInt((Process p) -> p.burstTime)
        .thenComparingInt(p -> p.id));

        int currentTime = 0;

        System.out.println("\n===== SJF Scheduling =====");
        System.out.println("Gantt Chart:");

        for (Process p : processes) {

            // set start time
            p.startTime = currentTime;

            // calculate waiting time
            p.waitingTime = p.startTime - p.arrivalTime;

            System.out.print("| P" + p.id + " [" + currentTime);

            // execute process
            currentTime += p.burstTime;

            // set finish time
            p.finishTime = currentTime;

            // calculate turnaround time
            p.turnaroundTime = p.finishTime - p.arrivalTime;

            System.out.print(" - " + currentTime + "] ");
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