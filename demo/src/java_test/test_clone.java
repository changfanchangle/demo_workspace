package java_test;

import java.util.HashMap;
import java.util.Map;

/*
     * java的clone，拷贝的练习
     */
public class test_clone {
    public static void main(String[] arge){
        // JAVA对象本身的clone
        Student student_01 = new Student("张三",12,new HashMap<String,String>(){{
            put("语文","100");
        }});
        Student student_02 = (Student) student_01.clone();
        student_02.setAge(18);
        student_02.setName("李四");
        student_02.getMyClass().put("数学","60");
        System.out.println(student_01);
        // 结果：Student{name='张三', age=12, myClass={数学=60, 语文=100}}
        System.out.println(student_02);
        // 结果：Student{name='李四', age=18, myClass={数学=60, 语文=100}}
        // 结论：1，简单类型直降复制，2,String复制地址，但是"xxxx"本身是个新地址的字符串，3,对象还是复制的地址

    }


}

// 测试用内部类
class Student  implements Cloneable{  // 注意，这里实现了java.lang.Cloneable
    String name = ""; // 姓名
    int age = 0;  // 年龄
    Map<String,String> myClass; // 课程
    public Student(String name, int age, Map<String, String> myClass) {
        this.name = name;
        this.age = age;
        this.myClass = myClass;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, String> getMyClass() {
        return myClass;
    }

    public void setMyClass(Map<String, String> myClass) {
        this.myClass = myClass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", myClass=" + myClass +
                '}';
    }

    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}