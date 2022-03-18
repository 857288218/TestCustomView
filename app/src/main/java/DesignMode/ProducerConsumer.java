package DesignMode;

// 生产者和消费者在同一时间段内共用同一个存储空间，生产者往存储空间中添加产品，消费者从存储空间中取走产品，当存储空间为空时，消费者阻塞，当存储空间满时，生产者阻塞
// https://juejin.cn/post/6844903486895865864
public class ProducerConsumer {

    static int count = 0;
    static final int MAX = 10;
    static Object LOCK = new Object();

    class Producer implements Runnable {
        @Override
        public void run() {
            synchronized (LOCK) {
                while (count == MAX) {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count++;
                LOCK.notifyAll();
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            synchronized (LOCK) {
                while (count == 0) {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count--;
                LOCK.notifyAll();
            }
        }
    }

}
