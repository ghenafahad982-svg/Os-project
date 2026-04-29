/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package osproject;

import java.util.Queue;

/**
 * Thread 2:
 * Responsible for moving processes from Job Queue to Ready Queue
 * only if enough memory is available.
 */
public class QueueManager extends Thread {

    private Queue<Process> jobQueue;
    private Queue<Process> readyQueue;
    private MemoryManager memoryManager;

    public QueueManager(Queue<Process> jobQueue, Queue<Process> readyQueue, MemoryManager memoryManager) {
        this.jobQueue = jobQueue;
        this.readyQueue = readyQueue;
        this.memoryManager = memoryManager;
    }

    @Override
    public void run() {

        // Keep running until all jobs are loaded
        while (true) {

            synchronized (jobQueue) {

                // Stop when all processes are handled
                if (jobQueue.isEmpty()) {
                    break;
                }

                // Peek the next process without removing it
                Process process = jobQueue.peek();

                // Check if memory is sufficient
                if (memoryManager.canAllocate(process.memory)) {

                    // Allocate memory for the process
                    memoryManager.allocate(process.memory);

                    // Remove from Job Queue
                    jobQueue.poll();

                    // Add to Ready Queue safely
                    synchronized (readyQueue) {
                        process.state = "Ready";
                        readyQueue.add(process);
                    }

                    System.out.println("[Thread 2] Process " + process.id + " moved to Ready Queue");
                }
            }

            // Simulate 1 ms time unit
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("[Thread 2] All processes loaded successfully.");
    }
}