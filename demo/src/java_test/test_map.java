package java_test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
 * java_map总结
 */
public class test_map {
    public static void main(String[] arge){
        // HashMap
        HashMap<String,String>  hashMap = new HashMap<String,String>();
        hashMap.put("aa","1");
        hashMap.put("ab","2");
        hashMap.put("bA","3");
        hashMap.put("bB","4");
        hashMap.put("bC","5");
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            System.out.println("key:"+entry.getKey()+",value:"+entry.getValue());
        }
        // 结果
        // key:aa,value:1
        // key:bB,value:4
        // key:ab,value:2
        // key:bC,value:5
        // key:bA,value:3

        // Hashtable
        Hashtable<String,String> hashtable = new Hashtable<String,String>();
        hashtable.put("aa","1");
        hashtable.put("ab","2");
        hashtable.put("bA","3");
        hashtable.put("bB","4");
        hashtable.put("bC","5");
        Iterator iter2 = hashtable.entrySet().iterator();
        while (iter2.hasNext()){
            Map.Entry entry = (Map.Entry)iter2.next();
            System.out.println("key:"+entry.getKey()+",value:"+entry.getValue());
        }
        // 结果
        // key:bC,value:5
        // key:ab,value:2
        // key:bB,value:4
        // key:aa,value:1
        // key:bA,value:3

        // LinkedHashMap
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<String,String>();
        linkedHashMap.put("aa","1");
        linkedHashMap.put("ab","2");
        linkedHashMap.put("bA","3");
        linkedHashMap.put("bB","4");
        linkedHashMap.put("bC","5");
        Iterator iter3 = linkedHashMap.entrySet().iterator();
        while (iter3.hasNext()){
            Map.Entry entry = (Map.Entry)iter3.next();
            System.out.println("key:"+entry.getKey()+",value:"+entry.getValue());
        }
        // 结果
        // key:aa,value:1
        // key:ab,value:2
        // key:bA,value:3
        // key:bB,value:4
        // key:bC,value:5


        Map<String, String> weak = new WeakHashMap<String, String>();
        weak.put(new String("1"), "1");
        weak.put(new String("2"), "2");
        weak.put(new String("3"), "3");
        weak.put(new String("4"), "4");
        weak.put(new String("5"), "5");
        weak.put(new String("6"), "6");
        System.out.println(weak.size());  // 结果：6
        System.gc();  //手动触发 Full GC
        try {
            Thread.sleep(50); //我的测试中发现必须sleep一下才能看到不一样的结果
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(weak.size());  // 结果：0


        TreeMap<String,String> treeMap = new TreeMap<String,String>();
        treeMap.put("bB","4");
        treeMap.put("bC","5");
        treeMap.put("aa","1");
        treeMap.put("ab","2");
        treeMap.put("bA","3");
        Iterator iter4 = treeMap.entrySet().iterator();
        while (iter4.hasNext()){
            Map.Entry entry = (Map.Entry)iter4.next();
            System.out.println("key:"+entry.getKey()+",value:"+entry.getValue());
        }
        // 结果
        // key:aa,value:1
        // key:ab,value:2
        // key:bA,value:3
        // key:bB,value:4
        // key:bC,value:5
    }
}
