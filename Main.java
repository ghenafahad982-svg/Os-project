package osproject;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

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

        // ===== Menu =====
        Scanner input = new Scanner(System.in);

        while (true) {

         System.out.println("\nChoose Scheduling Algorithm:");
          System.out.println("1. SJF");
         System.out.println("2. Round Robin");
         System.out.println("3. Exit");

         int choice = input.nextInt();

         if (choice == 1) {

        SJFScheduler sjf = new SJFScheduler();
        sjf.schedule(readyQueue);

         } else if (choice == 2) {

        RRScheduler rr = new RRScheduler();
        rr.schedule(readyQueue);

         } else if (choice == 3) {

        System.out.println("Program ended.");
        break; 

         } else {

        System.out.println("Invalid choice");
    }
}

        // Print Job Queue (should be empty after moving)
        System.out.println("\nAll processes in Job Queue:");
        for (Process p : jobQueue) {
            p.displayInfo();
        }
    }
}
