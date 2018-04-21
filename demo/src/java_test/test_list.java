package java_test;

import java.util.*;

/*
 * java_list总结
 */
public class test_list {
    public static void main(String[] arge){
        // ArrayList基本例子
        List<String> arrayList = new ArrayList<String>();
        arrayList.add("test1");
        arrayList.add("test2");
        arrayList.add("test3");
        for (int i = 0; i < arrayList.size();i++){
            System.out.println(arrayList.get(i));
        }

        // linkedList基本例子
        List<String> linkedList = new LinkedList<String>();
        linkedList.add("test1");
        linkedList.add("test2");
        linkedList.add("test3");
        for (String data : linkedList){
            System.out.println(data);
        }

        // vector基本例子
        Vector<String> vector = new Vector<String>();
        vector.add("test1");
        vector.add("test2");
        vector.add("test3");
        for (int i = 0; i < vector.size();i++){
            System.out.println(vector.get(i));
        }

        // stack基本例子
        Stack<String> stack = new Stack<String>();
        stack.push("test1");
        stack.push("test2");
        stack.push("test3");
        for (int i = 0; i < 3;i++){
            System.out.println(stack.pop());
        }

        Queue<String> queue = new LinkedList<String>();
        queue.add("test1");
        queue.add("test2");
        queue.add("test3");
        System.out.println(queue.size());
        for (int i = 0; i < 3;i++){
            System.out.println(queue.poll());
        }
        System.out.println(queue.size());
        // 结果:
        // 3
        // test1
        // test2
        // test3
        // 0
    }
}
