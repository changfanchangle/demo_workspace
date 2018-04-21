package java_thread;

/**
 * 《Java 并发编程实践》看到3.2章里中引用逸出的例子。
 * 不太理解，测试下（有问题例子）
 */
public class demo_02_ThisEscape {
    public static void main(String[] arge) {
        new ThisEscape(new ThisEscape.EventSource(){
            @Override
            public void registerListener(ThisEscape.EventListener e) {
                System.out.println(e);
                // 结果： java_thread.ThisEscape$1@470e2030
            }
        });
    }

}
class ThisEscape {
    int num = 0;
    public ThisEscape(EventSource source) {
        source.registerListener(new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        });
        num = 100;
    }
    void doSomething(Event e) {
    }
    interface EventSource {
        void registerListener(EventListener e);
    }
    interface EventListener {
        void onEvent(Event e);
    }
    interface Event {
    }
}