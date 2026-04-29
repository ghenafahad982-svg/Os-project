package osproject;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    
    public static void main(String[] args) {

        // Create a queue to store processes
        Queue<Process> jobQueue = new LinkedList<>();

        // Create Thread 1 (JobReader) and pass the queue
        JobReader reader = new JobReader(jobQueue);

        // Start Thread 1
        reader.start();

        try {
            // Wait until JobReader finishes reading all processes
            reader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Thread 2 
        
        // Create Ready Queue
        Queue<Process> readyQueue = new LinkedList<>();

        // Create Memory Manager
        MemoryManager memoryManager = new MemoryManager();

        // Create and start Thread 2
        QueueManager thread2 = new QueueManager(jobQueue, readyQueue, memoryManager);
        thread2.start();

        try {
            // Wait until Thread 2 finishes
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print Ready Queue
        System.out.println("\nReady Queue:");
        for (Process p : readyQueue) {
            p.displayInfo();
        }

        // Print Job Queue (should be empty after moving)
        System.out.println("\nAll processes in Job Queue:");
        for (Process p : jobQueue) {
            p.displayInfo();
        }
    }
}