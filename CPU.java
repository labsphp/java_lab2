package lab2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class CPU extends Thread {
    //private final BlockingQueue<Integer> mainQueue;
    //private final BlockingQueue<Integer> additionalQueue;
     private final CpuQueue<Integer> mainQueue;
    private final CpuQueue<Integer> additionalQueue;

    private final int time;
    private final int a = 1;
    private final int b = 3;
    private final CPUProcess process1;
    private final CPUProcess process2;
    private final Object lock;


    //CPU(BlockingQueue<Integer> mainQueue, BlockingQueue<Integer> additionalQueue,
      //  CPUProcess process1, CPUProcess process2, String name, Object lock) {
        CPU(CpuQueue<Integer> mainQueue, CpuQueue<Integer> additionalQueue,
          CPUProcess process1, CPUProcess process2, String name, Object lock) {

        super(name);
        this.mainQueue = mainQueue;
        this.additionalQueue = additionalQueue;
        this.time = this.a + (int) (Math.random() * b);
        this.process1 = process1;
        this.process2 = process2;
        this.lock = lock;
    }

    @Override
    public void run() {
        System.out.println("take time from queue by " + Thread.currentThread().getName() + " = " + this.time);
        while (true) {
            if (!this.mainQueue.isEmpty()) {

                if (this.mainQueue.isEmpty()) continue;
                System.out.println("Size of main queue for " + Thread.currentThread().getName() + " is  " + this.mainQueue.size());
                try {
                    System.out.println(Thread.currentThread().getName() + " - " + mainQueue.take() + " took from main queue");
                    Thread.sleep(this.time * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (!this.additionalQueue.isEmpty()) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (this.additionalQueue.isEmpty()) continue;

                System.out.println("Size of additional queue for " + Thread.currentThread().getName() + " is  " + this.additionalQueue.size());
                try {
                    System.out.println(Thread.currentThread().getName() + " - " + additionalQueue.take() + " took from additionalQueue");
                    Thread.sleep(this.time * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if (this.process1.exit && this.process2.exit) {
                    break;
                } else {
                    try {
                        synchronized (lock) {
                            System.out.println("waiting "+ Thread.currentThread().getName());
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("Size of queue for " + Thread.currentThread().getName() + " is " + this.mainQueue.getMaxSize());
        System.out.println(Thread.currentThread().getName() + "end");
    }

}
