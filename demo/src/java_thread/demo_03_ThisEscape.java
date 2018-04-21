package java_thread;

/**
 * 《Java 并发编程实践》看到3.2章里中引用逸出的例子。
 * 不太理解，测试下（安全例子）
 */
public class demo_03_ThisEscape {
    public static void main(String[] arge) {
        SafeListener safeListener = new SafeListener();
//        safeListener.doSomething();
    }
}

class SafeListener {
    private final EventListener listener;
    protected SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }

    public static SafeListener newInstance(EventSource source) {
        SafeListener safe = new SafeListener();
        source.registerListener(safe.listener);
        return safe;
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