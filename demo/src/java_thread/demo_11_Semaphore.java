package java_thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class demo_11_Semaphore {
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(0);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0;i<10;i++){
            // 追加线程：获取资源后执行完毕
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程"+Thread.currentThread().getName()+"准备就绪");
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程"+Thread.currentThread().getName()+"执行完毕");
                }
            });
        }
        Thread.sleep(2000);
        for (int i = 0;i<10;i++){
            Thread.sleep(1000);
            semaphore.release();
        }
    }
}
