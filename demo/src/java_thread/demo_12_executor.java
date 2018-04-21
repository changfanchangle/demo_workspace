package java_thread;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * executor线程池
 */
public class demo_12_executor {
    // 创建线程池：10
    final private static int NUM = 10;
    final private static Executor executor = Executors.newFixedThreadPool(NUM);

    public static void main(String[] arge) throws InterruptedException{
        for (int i = 0;i<100;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程"+Thread.currentThread().getName()+"执行完毕");
                }
            });
            Thread.sleep(100);
        }

    }
}
