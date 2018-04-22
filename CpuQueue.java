package lab2;

import java.util.LinkedList;
import java.util.List;

public class CpuQueue<T> {
    private List<T> dataBuffer;
    private int capacity;
    private int maxSize = 0;

    private int currentSize = 0;

    CpuQueue(int capacity) {
        this.capacity = capacity;
        this.dataBuffer = new LinkedList<>();
    }

    private void setMaxSize() {
        if (this.maxSize < this.currentSize) {
            this.maxSize = this.currentSize;
        }
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    private boolean Empty() {
        return (currentSize == 0);
    }

    private boolean isFull() {
        return (currentSize == capacity);
    }

    public synchronized void put(T data) {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.dataBuffer.size() == 0) {
            notifyAll();
        }
        dataBuffer.add(data);
        currentSize++;
        this.setMaxSize();

    }

    public synchronized boolean isEmpty() {
        return currentSize == 0;
    }

    public synchronized int size() {
        return currentSize;
    }

    public synchronized T take() {
        while (Empty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.dataBuffer.size() == this.capacity) {
            notifyAll();
        }

        currentSize--;

        return this.dataBuffer.remove(0);
    }
}
