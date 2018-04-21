package java_thread;

import java.util.Random;

class Student {
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

public class demo_04_ThreadLocal implements Runnable {

    ThreadLocal studentLocal = new ThreadLocal();

    public static void main(String[] args) {
        demo_04_ThreadLocal t = new demo_04_ThreadLocal();
        new Thread(t, "t1").start();
        new Thread(t, "t2").start();
    }

    @Override
    public void run() {
        accessStudent();
    }

    private void accessStudent() {
        Student s = this.getStudent();
        Random random = new Random();
        int age = random.nextInt(100);
        System.out.println("当前线程第一次设置年龄 " + Thread.currentThread()+ ":" + age);
        s.setAge(age);
        System.out.println("当前线程第一次取得年龄 " + Thread.currentThread() + ":" + s.getAge());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程第二次取得年龄 " + Thread.currentThread() + ":" + s.getAge());
    }

    public Student getStudent() {
        Student s = (Student) studentLocal.get();
        if (s == null) {
            s = new Student();
            studentLocal.set(s);
        }
        return s;
    }
}