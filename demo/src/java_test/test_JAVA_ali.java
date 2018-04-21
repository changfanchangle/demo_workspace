package java_test;

import java.util.*;

public class test_JAVA_ali {
    /*
     * 【阿里巴巴JAVA开发手册.pdf】阅读笔记相关练习
     */
    public static void main(String[] args) {
        // 【推荐】使用索引访问用 String 的 split 方法得到的数组时，需做最后一个分隔符后有无
        // 内容的检查，否则会有抛 IndexOutOfBoundsException 的风险。
        // 正常:
        String[] test_01 = "AAA,BBB,CCC".split(",");
        System.out.println(Arrays.asList(test_01).toString());
        // 结果：[AAA, BBB, CCC]

        // 最后多一个,:
        String[] test_02 = "AAA,BBB,CCC,".split(",");
        System.out.println(Arrays.asList(test_02).toString());
        // 结果：[AAA, BBB, CCC]

        // 最后多多个,:
        String[] test_03 = "AAA,BBB,CCC,,".split(",");
        System.out.println(Arrays.asList(test_03).toString());
        // 结果：[AAA, BBB, CCC]

        // 中间存在,的空值:
        String[] test_04 = "AAA,BBB,,CCC".split(",");
        System.out.println(Arrays.asList(test_04).toString());
        // 结果：[AAA, BBB, , CCC]

        // 中间存在多个,的空值:
        String[] test_05 = "AAA,BBB,,,CCC".split(",");
        System.out.println(Arrays.asList(test_05).toString());
        // 结果：[AAA, BBB, , , CCC]

        // 中间存在多个,的空值,带间隔空格:
        String[] test_06 = "AAA,BBB, ,,CCC".split(",");
        System.out.println(Arrays.asList(test_06).toString());
        // 结果：[AAA, BBB,  , , CCC]

       // 【推荐】集合初始化时，指定集合初始值大小。
        long time1 = System.currentTimeMillis();
        HashMap<String,String> testMap = new HashMap<String,String>();
        for (int i = 0; i < 1000000 ;i++){
           testMap.put(String.valueOf(i),String.valueOf(i));
        }
        System.out.println(System.currentTimeMillis() - time1);
        // 结果:1299

        long time2 = System.currentTimeMillis();
        HashMap<String,String> testMap2 = new HashMap<String,String>(1000000);
        for (int i = 0; i < 1000000 ;i++){
            testMap2.put(String.valueOf(i),String.valueOf(i));
        }
        System.out.println(System.currentTimeMillis() - time2);
        // 结果:2458

        long time3 = System.currentTimeMillis();
        ArrayList<String> testList = new ArrayList<String>();
        for (int i = 0; i < 1000000 ;i++){
            testList.add(String.valueOf(i));
        }
        System.out.println(System.currentTimeMillis() - time3);
        // 结果:63

        long time4 = System.currentTimeMillis();
        ArrayList<String> testList2 = new ArrayList<String>(1000000);
        for (int i = 0; i < 1000000 ;i++){
            testList2.add(String.valueOf(i));
        }
        System.out.println(System.currentTimeMillis() - time4);
        // 结果:144



    }
}
