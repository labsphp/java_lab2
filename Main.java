package lab2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Main {
    public static void main(String[] argc) throws InterruptedException {

          //  BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<>(10, true);
            //BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<>(10, true);
            Object lock = new Object();

            CpuQueue<Integer> queue1 = new CpuQueue<>(10);
            CpuQueue<Integer> queue2 = new CpuQueue<>(10);
            int n = 5;
            int m = 9;



            CPUProcess cpuProcess1 = new CPUProcess(queue1, n, "process1",lock);
            CPUProcess cpuProcess2 = new CPUProcess(queue2, m, "process2",lock);

            Thread cpu1 = new CPU(queue1, queue2, cpuProcess1, cpuProcess2, "cpu1",lock);
            Thread cpu2 = new CPU(queue2, queue1, cpuProcess1, cpuProcess2, "cpu2",lock);

            cpu1.start();
            cpu2.start();
            cpuProcess1.start();
            cpuProcess2.start();
            cpu1.join();
            cpu2.join();
            cpuProcess1.join();
            cpuProcess2.join();
            System.out.println("*********************************************************************");

    }
}
