package error_test_001;
import java.util.*;

/*
 * 
 */

public class error_test {
	public static void main(String[] arge) throws InterruptedException {
		Test ttmtransService = new Test();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					ttmtransService.StartTask();

					Thread.sleep(5000);
					ttmtransService.CloseTimer();
					ttmtransService.StartTask();

					Thread.sleep(10000);
					ttmtransService.CloseTimer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
        Thread thread[] = new Thread[100];
        for (int i = 0; i < thread.length; i++) {
            thread[i] = new Thread(runnable);
            thread[i].start();
        }
	}
}
class Test{
	/**返回结果数据**/
	private Map<String,Object> ResultData=new HashMap<String,Object>();
	/**定时器**/
	public static Timer TaskTimer;
	/**查询任务**/
	private TaskQuery mytask;
	/** 执行中flg **/
	private boolean flgRunning = false;

	/**开始执行任务**/
	public synchronized void StartTask(){
		if(TaskTimer == null){
			System.out.print("StartTask...");
			TaskTimer=new Timer();
			mytask=new TaskQuery();
			TaskTimer.schedule(mytask, new Date(),1000);
		}
	}

	/**
	 * 关闭定时器
	 */
	public synchronized void CloseTimer(){
		if(TaskTimer != null){
			System.out.print("CloseTimer...");
			TaskTimer.cancel();
			TaskTimer.purge();
			TaskTimer = null;
		}
	}

	public class TaskQuery extends TimerTask{
		/** 原来时间戳 **/
		private long time;
		/** 现在时间戳 **/
		private long now;
		public void run(){
			if (flgRunning){
				return;
			}
			flgRunning = true;
			try {
				System.out.print("开始执行...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("结束执行...");
			}finally {
				// 结束时，flag
				flgRunning = false;
			}
		}
	}
}
