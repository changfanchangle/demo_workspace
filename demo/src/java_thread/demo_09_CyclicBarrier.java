package java_thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class demo_09_CyclicBarrier {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.nanoTime();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        for (int i = 0;i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程"+Thread.currentThread().getName()+"准备就绪" + (System.nanoTime() - startTime));
                        cyclicBarrier.await();
                        System.out.println("线程"+Thread.currentThread().getName()+"执行完成" + (System.nanoTime() - startTime));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Thread.sleep(500);
        }
    }
}
