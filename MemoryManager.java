/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package osproject;

/**
 * MemoryManager class is responsible for managing the system memory.
 * The total available memory is limited to 2048 MB.
 */
public class MemoryManager {

    private final int MAX_MEMORY = 2048; // Maximum memory capacity
    private int usedMemory = 0;          // Currently used memory

    /**
     * Checks if there is enough memory to allocate a process
     * @param size memory required by the process
     * @return true if enough memory is available
     */
    public synchronized boolean canAllocate(int size) {
        return (usedMemory + size) <= MAX_MEMORY;
    }

    /**
     * Allocates memory for a process
     * @param size memory to allocate
     */
    public synchronized void allocate(int size) {
        usedMemory += size;
        System.out.println("[Memory] Allocated: " + size + " MB | Used: " + usedMemory);
    }

    /**
     * Frees memory after process finishes execution
     * @param size memory to release
     */
    public synchronized void deallocate(int size) {
        usedMemory -= size;
        System.out.println("[Memory] Freed: " + size + " MB | Used: " + usedMemory);
    }

    /**
     * Returns available memory
     */
    public synchronized int getAvailableMemory() {
        return MAX_MEMORY - usedMemory;
    }
}
