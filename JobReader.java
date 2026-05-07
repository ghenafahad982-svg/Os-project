import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Queue;

public class JobReader extends Thread{
    
    // Shared queue to store all processes after reading from file
    private Queue<Process> jobQueue;

    public JobReader(Queue<Process> jobQueue) {
        this.jobQueue = jobQueue;
    }

    @Override
    public void run() {
        try {
            // Open the input file
            BufferedReader br = new BufferedReader(new FileReader("job.txt"));
            String line;
            // Read file line by line
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // Ignore empty lines
                if (line.isEmpty()) {
                    continue;
                }
                // Split line using : and :
                String[] parts = line.split("[:;]");

                int id = Integer.parseInt(parts[0]);
                int burst = Integer.parseInt(parts[1]);
                int priority = Integer.parseInt(parts[2]);
                int memory = Integer.parseInt(parts[3]);
                // Create process object
                Process p = new Process(id, burst, priority, memory);
                // Add process to job queue
                jobQueue.add(p);

                System.out.println("Added Process " + id + " to Job Queue");
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
