package java_test;

// 增加计数功能定义
// 注意，接口只存在一个void increment()，所以所有实现它的对象中，
// 只存在public的void increment，其他的成员都是private
interface Incremental {
    void increment();
}
// 调用者：保存计数功能对象，并调用
class Caller{
    private Incremental callbackReference;
    Caller(Incremental incrementable){callbackReference = incrementable;}
    void go(){callbackReference.increment();}
}
// 实现class1：简单的实现增加计数
class Callee1 implements Incremental {
    private int num = 0;
    @Override
    public void increment() {
        System.out.println("Callee1：增加计数功能实现"+(++num));
    }
}
// 实现class2：在实现功能的同时实现回调
class Callee2 extends Callback implements Incremental {
    private int num = 0;
    @Override
    public void increment(){
        System.out.println("Callee2：增加计数功能实现"+(++num));
        super.increment();
    }
    // 闭包，内部类Closure调用了外部变量num
    private class Closure implements Incremental {
        @Override
        public void increment() {
            System.out.println("Callee2-Closure：增加计数功能实现"+(++num));
            Callee2.super.increment();
        }
    }
    public Incremental getCallbackReference(){
        return  new Closure();
    }
}
// 回调用类
class Callback {
    public void increment(){System.out.println("回调内容执行...");}
    static void fun(Callback myIncrement){myIncrement.increment();}
}

public class test_closure {
    // 执行部分
    public static void main(String[] arge){
        // 简单的实现，使用调用者调用
        Caller caller1 = new Caller(new Callee1());
        caller1.go();
        caller1.go();
        // 结果:
        // Callee1：增加计数功能实现1
        // Callee1：增加计数功能实现2

        // 追加回调方法的实现
        // 回调对象执行实现对象
        Callee2 callee2 = new Callee2();
        Callback.fun(callee2);
        // 结果:
        // Callee2：增加计数功能实现1
        // 回调内容执行...

        // 使用调用者调用
        Caller caller2 = new Caller(callee2.getCallbackReference());
        caller2.go();
        caller2.go();
        // 结果:
        // Callee2-Closure：增加计数功能实现2
        // 回调内容执行...
        // Callee2-Closure：增加计数功能实现3
        // 回调内容执行...
    }
}


