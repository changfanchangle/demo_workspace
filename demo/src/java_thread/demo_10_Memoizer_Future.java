package java_thread;

import java.util.concurrent.*;

public class demo_10_Memoizer_Future {
    static Memoizer memoizer = new Memoizer(new Computable<String,String>(){
        @Override
        public String compute(String arg) throws InterruptedException {
            Thread.sleep(2000);
            return "compute执行结果" + arg;
        }
    });
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread thread[]=new Thread[10];
        for(int i=0;i<thread.length;i++){
            Thread.sleep(500);
            thread[i]=new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("开始计算1000");
                    try {
                        System.out.println(memoizer.compute("1000"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread[i].start();
        }
    }
}

interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
class Memoizer <A, V> implements Computable<A, V> {//继承Computable，其中有compute方法
    private final ConcurrentMap<A, Future<V>> cache
            = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {//尽量将域声明为final类型，除非需要它们是可变的。不可变对象一定是线程安全的。
        while (true) {      //一直操作直到被停止
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = new Callable<V>() {
                    public V call() throws InterruptedException {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(eval);
                f = cache.putIfAbsent(arg, ft);//底层的Map中的put是复合操作（“若没有则添加”），属于非原子性的“先检查再执行”操作
                if (f == null) { //所以要对f进行判断
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();//若结果已经计算出来，那么将立刻返回。如果其他线程正在计算该结果，那么信道的线程将一直等待这个结果被计算出来。
            } catch (CancellationException e) {
                cache.remove(arg, f);  //如果发现计算被取消或失败，将Future从缓存中移出
            } catch (ExecutionException e) {
                //getCause来获得被封装的初始异常
//                throw LaunderThrowable.launderThrowable(e.getCause());
            }
        }
    }
}