package java_thread;

import java.util.*;
import java.util.concurrent.*;

/**
 * CompletionService
 * 摘自https://blog.csdn.net/cbjcry/article/details/70154897
 */

public class demo_14_CompletionService {
    // 具有返回值的测试线程
    class MyThread implements Callable<String> {
        private String name;
        public MyThread(String name) {
            this.name = name;
        }

        @Override
        public String call() {
            int sleepTime = new Random().nextInt(1000);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println(name + " 超时中断...");
                Thread.interrupted();
            }

            // 返回给调用者的值
            String str = name + " 等待时间:" + sleepTime;
            System.out.println(name + " 已结束...");

            return str;
        }
    }

    private final int POOL_SIZE = 5;
    private final int TOTAL_TASK = 20;

    // 方法一，自己写集合来实现获取线程池中任务的返回结果
    public void testByQueue() throws Exception {
        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
//        BlockingQueue<Future<String>> queue = new LinkedBlockingQueue<Future<String>>();
        // 向里面扔任务
//        for (int i = 0; i < TOTAL_TASK; i++) {
//            Future<String> future = pool.submit(new MyThread("Thread" + i));
//            queue.add(future);
//        }
        List<MyThread> tarks = new ArrayList<MyThread>();
        for (int i = 0; i < TOTAL_TASK; i++) {
            tarks.add(new MyThread("Thread" + i));
        }
        List<Future<String>> futureList = pool.invokeAll(tarks,500, TimeUnit.MILLISECONDS);

        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
            try {
                System.out.println("取得testByQueue的结果:" + futureList.get(i).get());
            }catch (CancellationException e){
                System.out.println("CancellationException");
            }
        }
        // 关闭线程池
        pool.shutdown();
    }

    // 方法二，通过CompletionService来实现获取线程池中任务的返回结果
    public void testByCompetion() throws Exception {
        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        CompletionService<String> cService = new ExecutorCompletionService<String>(pool);
        // 向里面扔任务
        System.out.println("testByCompetion开始创建线程");
        for (int i = 0; i < TOTAL_TASK; i++) {
            cService.submit(new MyThread("Thread" + i));
        }
        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
            System.out.println("testByCompetion开始取得第"+(i+1)+"个结果");
            Future<String> future = cService.take();
            System.out.println("取得testByCompetion的结果:" + future.get());
        }
        System.out.println("testByCompetion完毕");
        // 关闭线程池
        pool.shutdown();
    }

    public static void main(String[] args) throws Exception {
        demo_14_CompletionService t = new demo_14_CompletionService();
        t.testByQueue();
//        t.testByCompetion();
    }
}