package java_test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * JAVA匿名内部类的测试
 */
public class test_JAVA_anonymousInternal {
    public static void main(String[] args) {
        // -- 开始，map初始化 --
        // 正常map初始化
        Map<String,Integer> mapTest01 = new HashMap<String,Integer>();
        mapTest01.put("张三",100);
        mapTest01.put("李四",50);
        mapTest01.put("王五",70);
        System.out.println(mapTest01.toString());
        // 结果:{李四=50, 张三=100, 王五=70}

        Map<String,Integer> mapTest02 = new HashMap<String,Integer>(){{
            put("张三",100);
            put("李四",50);
            put("王五",70);
        }};
        System.out.println(mapTest02.toString());
        // 结果:{李四=50, 张三=100, 王五=70}

        /*
        // -- 效率测试 --
        long time1 = System.currentTimeMillis();
        List<Map<String,Integer>> listMapTest01 = new ArrayList<Map<String,Integer>>();
        for (int i = 0; i < 5000000 ;i++){
            Map<String,Integer> mapTemp = new HashMap<String,Integer>();
            mapTemp.put("张三",100);
            mapTemp.put("李四",50);
            mapTemp.put("王五",70);
            listMapTest01.add(mapTemp);
        }
        System.out.println(System.currentTimeMillis() - time1);
        // 结果：15057

        long time2 = System.currentTimeMillis();
        List<Map<String,Integer>> listMapTest02 = new ArrayList<Map<String,Integer>>();
        for (int i = 0; i < 5000000 ;i++){
            listMapTest02.add(new HashMap<String,Integer>(){{
                put("张三",100);
                put("李四",50);
                put("王五",70);
            }});
        }
        System.out.println(System.currentTimeMillis() - time2);
        // 结果：6723
        */

        // 简单的线程
        new Thread(){
            @Override
            public void run(){
                for (int i =0; i < 3; i++){
                    System.out.println("Thread执行" + i);
                }
            }
        }.start();
        System.out.println("Thread开始");
        // 结果:
        // Thread开始
        // Thread执行0
        // Thread执行1
        // Thread执行2


        // 匿名内部类,不能使用静态
//        Map<String,Integer> mapTest03 = new HashMap<String,Integer>(){
//            static String name = "王五"; //  static语句出现编译异常：Inner classes cannot have static declarations
//            {
//                put("张三",100);
//            }
//        };

        // 匿名内部类，的参数使用
        String strName = "张三";
        Integer score = 100;
        Map<String,Integer> mapTest04 = new HashMap<String,Integer>(){{
            put(strName,score);
        }};


        // 匿名内部类，的参数使用
//        String strName1 = "张三";
//        Integer score1 = 100;
//        score1 = 40;
//        Map<String,Integer> mapTest05 = new HashMap<String,Integer>(){{
//            strName1 = "李四";    // strName1语句出现编译异常：Variable 'strName1' is accessed from within inner class, needs to be final or effectively final
//            put(strName1,score1); // score1语句出现编译异常：Variable 'score1' is accessed from within inner class, needs to be final or effectively final
//        }};


        int i = 0;
        try {
            i = 100 / i;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}



