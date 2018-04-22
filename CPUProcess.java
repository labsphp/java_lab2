package lab2;

import java.util.concurrent.BlockingQueue;

public class CPUProcess extends Thread {
   // private final BlockingQueue<Integer> queue;
    private final CpuQueue<Integer> queue;

    private final int time;
    private int countProcess;
    boolean exit = false;
    private final int a = 2;
    private final int b = 5;
    private final Object lock;

      CPUProcess(CpuQueue<Integer> queue, int countProcess, String name, Object lock) {
    //CPUProcess(BlockingQueue<Integer> queue, int countProcess, String name, Object lock) {

        super(name);
        this.queue = queue;
        this.countProcess = countProcess;
        this.time = this.a + (int) (Math.random() * b);
        this.lock = lock;
    }

    @Override
    public void run() {
        System.out.println("put time in queue by " + Thread.currentThread().getName() + " = " + this.time);
        while (this.countProcess > 0) {
            int rand = this.a + (int) (Math.random() * b);
            try {
                System.out.println(Thread.currentThread().getName() + " put the element " + rand);

                queue.put(rand);
                synchronized (lock) {
                    System.out.println("notifyAll from " + Thread.currentThread().getName());
                    lock.notifyAll();
                }
                this.countProcess--;
                Thread.sleep(this.time * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName() + "end");
        this.exit = true;
        synchronized (lock) {
            System.out.println("notifyAll from " + Thread.currentThread().getName());
            lock.notifyAll();
        }
    }

}
