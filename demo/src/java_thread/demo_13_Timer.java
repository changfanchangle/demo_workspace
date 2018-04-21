package java_thread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletionService;

/**
 * Timer延迟，定时处理器
 */
public class demo_13_Timer {
    public static void main(String[] arge) throws InterruptedException{
        long startTime = System.nanoTime();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(250);
                    System.out.println("线程"+Thread.currentThread().getName()+"执行("+(System.nanoTime() - startTime) / (1000*1000)+")");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, new Date(),750);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    System.out.println("线程2"+Thread.currentThread().getName()+"执行("+(System.nanoTime() - startTime) / (1000*1000)+")");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, new Date(),500);
    }
}
