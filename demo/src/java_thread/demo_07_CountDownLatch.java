package java_thread;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁CountDownLatch的demo
 * CountDownLatch这个类能够使一个线程等待其他线程完成各自的工作后再执行
 * 如下：
 *
 */
public class demo_07_CountDownLatch {

    public static void main(String[] args) throws InterruptedException {
        timeTasks(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable 执行");
            }
        });
    }

    static long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for(int i = 0;i < nThreads ; i++){
            Thread t = new Thread(){
                public void  run(){
                    try {
                        System.out.println("startGate 等待");
                        startGate.await();
                        task.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        endGate.countDown();
                    }
                }
            };
            t.start();
        }
        long start = System.nanoTime();
        System.out.println("startGate 减少");
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        System.out.println("计算时间");
        return end - start;
    }
}
