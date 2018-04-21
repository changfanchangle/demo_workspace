package java_thread;

import java.util.concurrent.*;

/**
 * 闭锁FutureTask的demo
 *
 */
public class demo_08_FutureTask {
        public static void main(String[] args) throws ExecutionException, InterruptedException {
//            // 第一种方式:Future + ExecutorService
//            Task task = new Task();
//            ExecutorService service = Executors.newCachedThreadPool();
//            Future<Integer> future = service.submit(task);
//            service.shutdown();
//            // 结果
//            // Thread [pool-1-thread-1] is running

//            // 第二种方式: FutureTask + ExecutorService
//            ExecutorService executor = Executors.newCachedThreadPool();
//            Task task = new Task();
//            FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
//            executor.submit(futureTask);
//            executor.shutdown();
//            // 结果
//            // Thread [pool-1-thread-1] is running
//            // Thread [main] is running
//            // Task is not done
//            // result is 4950

            /**
             * 第三种方式:FutureTask + Thread
             */
            // 2. 新建FutureTask,需要一个实现了Callable接口的类的实例作为构造函数参数
            Task task = new Task();
            FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
            FutureTask<Integer> futureTask2 = new FutureTask<Integer>(task);
            // 3. 新建Thread对象并启动
            Thread thread = new Thread(futureTask);
            thread.setName("Task thread");
            thread.start();
            // 结果
            // Thread [pool-1-thread-1] is running
            // Thread [main] is running
            // Task is not done
            // result is 4950

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");

            // 4. 调用isDone()判断任务是否结束
            if(!futureTask2.isDone()) {
                System.out.println("Task is not done");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int result = 0;
            try {
                // 5. 调用get()方法获取任务结果,如果任务没有执行完成则阻塞等待
                result = futureTask2.get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("result is " + result);
        }
    }
// 继承Callable接口,实现call()方法,泛型参数为要返回的类型
class Task  implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");
        int result = 0;
        for(int i = 0; i < 100;++i) {
            result += i;
        }
        Thread.sleep(3000);
        return result;
    }
}


