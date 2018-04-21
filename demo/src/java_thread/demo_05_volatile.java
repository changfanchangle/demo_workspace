package java_thread;

/**
 * 例子摘自：https://blog.csdn.net/feier7501/article/details/20001083
 * 竞测试正确，有概率性
 */
public class demo_05_volatile {
//    private static volatile boolean bChanged;
    private static boolean bChanged;
    /*
     * 结果：
     * 重点在这里，没volatile，概率死循环。有volatile稳定输出“bChanged自身不相等”并结束循环
     * 原因：
     * 一般来说，bChanged被另一个线程不断的修改，
     * 但bChanged == !bChanged一般来说不会执行。原因在于java内存机制，
     * 对正常的bChanged == !bChanged来说，第一次把bChanged从内存取出来了，第二次bChanged的时候，一般就不会再取一次，而是直接用
     * 但加上volatile之后，bChanged == !bChanged的第二次bChanged被要求强制再取一次，假如中间正好切换的话，条件判断通过
     */

    public static void main(String[] args) throws InterruptedException {
        new Thread() {

            @Override
            public void run() {
                for (;;) {
                    if (bChanged == !bChanged) {
                        System.out.println("bChanged自身不相等");
                        System.exit(0);
                    }else{
                    }
                }
            }
        }.start();
        Thread.sleep(1000);
        System.out.println("--------------------------------------");
        new Thread() {
            @Override
            public void run() {
                for (;;) {
                    bChanged = !bChanged;
                }
            }
        }.start();
    }

}
