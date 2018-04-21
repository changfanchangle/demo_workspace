package java_thread;

import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class demo_01_AtomicLong {
    /*
     * 尝试存在竞态的类
     * 使用非线程安全的Long和线程安全的AtomicLong的区别
     */
    static final private int runNumber = 100000;
//    public static void main(String[] arge) throws InterruptedException {
//        // 使用long
//        Thread thread[] = new Thread[runNumber];
//        for (int i = 0; i < thread.length; i++) {
//            thread[i] = new Thread(new MyThread_Long());
//            thread[i].start();
//        }
//        // 结果（由于myLoing++是多步骤的，原子性的，导致问题）：
//        // .....
//        // myThread_Long:9999745(用时:9517)
//    }
//    public static void main(String[] arge) throws InterruptedException {
//
//        // 使用AtomicLong
//        Thread thread[]=new Thread[runNumber];
//        for(int i=0;i<thread.length;i++){
//            thread[i]=new Thread(new MyThread_AtomicLong());
//            thread[i].start();
//        }
//        // 结果（使用AtomicLong,用incrementAndGet自增，原子）：
//        // .....
//        // MyThread_AtomicLong:10000000(用时:9598)
//
//    }
//    public static void main(String[] arge) throws InterruptedException {
//        // 使用AtomicLong
//        Thread thread[]=new Thread[runNumber];
//        for(int i=0;i<thread.length;i++){
//            thread[i]=new Thread(new MyThread_nosafe_AtomicLong());
//            thread[i].start();
//        }
//        // 结果（使用AtomicLong,但自增操作非原子）：
//        // .....
//        // MyThread_nosafe_AtomicLong:9999118(用时:9886)
//    }
    public static void main(String[] arge) throws InterruptedException {
        // 使用AtomicLong
        Thread thread[]=new Thread[runNumber];
        MyThread_Long_synchronized myThread_Long_synchronized = new MyThread_Long_synchronized();  // 注意，所有线程使用同一个对象，不然synchronized无效
        for(int i=0;i<thread.length;i++){
            thread[i]=new Thread(myThread_Long_synchronized);
            thread[i].start();
        }
        // 结果（使用同步锁，可行，但慢）：
        // .....
        // MyThread_Long_synchronized:10000000(用时:13420)
    }
}

abstract class MyThreadBase{
    static long startTime = 0;
    MyThreadBase(){
        if (startTime == 0){
            startTime = new Date().getTime();
        }
    }
    protected void show(String msg, Long num){
        System.out.println(msg + num + "(用时:"+(new Date().getTime()-startTime)+")");
    }
    abstract void increment();
}

// 使用long
class MyThread_Long extends MyThreadBase implements Runnable {
    static private long myLoing = 0;
    @Override
    public void run() {
        for (int i = 0;i < 100;i++) {
            increment();
        }
        super.show("myThread_Long:", myLoing);
    }
    @Override
    void increment(){
        myLoing++;
    }
}

// 使用AtomicLong
class MyThread_AtomicLong extends MyThreadBase implements Runnable {
    static private AtomicLong myLoing = new AtomicLong(0);
    @Override
    public void run() {
        for (int i = 0;i < 100;i++) {
            increment();
        }
        super.show("MyThread_AtomicLong:",myLoing.longValue());
    }
    @Override
    void increment(){myLoing.incrementAndGet();}
}

// 使用AtomicLong但操作费线程安全
class MyThread_nosafe_AtomicLong extends MyThreadBase implements Runnable {
    static private AtomicLong myLoing = new AtomicLong(0);
    @Override
    public void run() {
        for (int i = 0;i < 100;i++) {
            increment();
        }
        super.show("MyThread_nosafe_AtomicLong:",myLoing.longValue());
    }
    @Override
    void increment(){
        myLoing.set(myLoing.addAndGet(1));
    }
}

// 使用long，synchronized
class MyThread_Long_synchronized extends MyThreadBase implements Runnable {
    static private long myLoing = 0;
    @Override
    public void run() {
        for (int i = 0;i < 100;i++) {
            increment();
        }
        super.show("MyThread_Long_synchronized:", myLoing);
    }
    @Override
    synchronized void increment(){
        myLoing++;
    }
}