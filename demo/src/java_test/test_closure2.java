package java_test;

// 自增长接口和实现类
interface Incrementable{
    void increment();
}
class Callee {
    private int num = 0;
    public Incrementable getIncrementable(){
        return new Incrementable(){
            @Override
            public void increment() {
                System.out.println("Callee：增加计数功能实现"+(++num));
            }
        };
    }
}
public class test_closure2 {
    // 执行部分
    public static void main(String[] arge){
        Callee callee = new Callee();
        callee.getIncrementable().increment();
        callee.getIncrementable().increment();
        callee.getIncrementable().increment();
        // 结果:
        // Callee：增加计数功能实现1
        // Callee：增加计数功能实现2
        // Callee：增加计数功能实现3
    }
}