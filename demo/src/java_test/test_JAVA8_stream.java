package java_test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class test_JAVA8_stream {
    /*
        JAVA8的stream测试
     */
    public static void main(String[] args) {
        /*
         * 简单对象的排序，筛选，去重，截取
         */
        // 排序：sorted
        Stream<Integer> sorted = Arrays.asList(7,1,55,22,15,34).stream().sorted();
        System.out.println(Arrays.toString(sorted.toArray()));   // 结果:[1, 7, 15, 22, 34, 55]
        // 筛选
        Stream<Integer> filter = Arrays.asList(7,1,55,22,15,34).stream().filter(v -> v > 10);
        System.out.println(Arrays.toString(filter.toArray()));   // 结果:[55, 22, 15, 34]
        // 去重
        Stream<Integer> distinct = Arrays.asList(11,22,22,22,22,22,33,44,55,11,33,44).stream().distinct();
        System.out.println(Arrays.toString(distinct.toArray()));   // 结果:[11, 22, 33, 44, 55]
        // 截取-limit
        Stream<Integer> limit = Arrays.asList(7,1,55,22,15,34).stream().limit(3);
        System.out.println(Arrays.toString(limit.toArray()));   // 结果:[7, 1, 55]
        // 截取-skip
        Stream<Integer> skip = Arrays.asList(7,1,55,22,15,34).stream().skip(3);
        System.out.println(Arrays.toString(skip.toArray()));   // 结果:[22, 15, 34]

        /*
         * 复杂对象处理
         */
        List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
        listMap.add(new HashMap<String,String>(){{
            put("A","2");put("B","aaaa");put("C","001");
        }});
        listMap.add(new HashMap<String,String>(){{
            put("A","1");put("B","bbbb");put("C","002");
        }});
        listMap.add(new HashMap<String,String>(){{
            put("A","3");put("B","cccc");put("C","003");
        }});
        System.out.println(Arrays.toString(listMap.stream().sorted(Comparator.comparing(map -> map.get("A"))).toArray()));   // 结果:[{A=1, B=bbbb, C=002}, {A=2, B=aaaa, C=001}, {A=3, B=cccc, C=0003}]
        System.out.println(Arrays.toString(listMap.stream().sorted(Comparator.comparing(map -> map.get("B"))).toArray()));   // 结果:[{A=2, B=aaaa, C=001}, {A=1, B=bbbb, C=002}, {A=3, B=cccc, C=0003}]
        System.out.println(Arrays.toString(listMap.stream().sorted(Comparator.comparing(map -> map.get("C"))).toArray()));   // 结果:[{A=3, B=cccc, C=0003}, {A=2, B=aaaa, C=001}, {A=1, B=bbbb, C=002}]

        /*
         * 复杂对象处理
         */
        // 测试用内部类
        class student {
            String name = ""; // 姓名
            int age = 0;  // 年龄
            String className = ""; // 课程
            int score = 0; // 课程分数
            public student(String name, int age, String className, int score) {
                this.name = name;
                this.age = age;
                this.className = className;
                this.score = score;
            }
            public String getName() {return name;}
            public void setName(String name) {this.name = name;}
            public int getAge() {return age;}
            public void setAge(int age) {this.age = age;}
            public String getClassName() {return className;}
            public void setClassName(String className) {this.className = className;}
            public int getScore() {return score;}
            public void setScore(int score) {this.score = score;}
            @Override
            public String toString() {
                return "student{" +"name='" + name + '\'' +", age=" + age +", className='" + className + '\'' +", score=" + score +'}';
            }
        }
        List<student> listObject = new ArrayList<student>(){{
            add(new student("张三",12,"语文",90));
            add(new student("李四",13,"语文",93));
            add(new student("李四",13,"数学",60));
            add(new student("王五",11,"语文",70));
        }};
        System.out.println(Arrays.toString(listObject.stream().sorted(Comparator.comparing(student::getAge)).toArray()));
        // 结果:[student{name='王五', age=11, className='语文', score=70}, student{name='张三', age=12, className='语文', score=90}, student{name='李四', age=13, className='语文', score=93}, student{name='李四', age=13, className='数学', score=60}]
        System.out.println(Arrays.toString(listObject.stream().sorted(Comparator.comparing(student::getScore)).toArray()));
        // 结果:[student{name='李四', age=13, className='数学', score=60}, student{name='王五', age=11, className='语文', score=70}, student{name='张三', age=12, className='语文', score=90}, student{name='李四', age=13, className='语文', score=93}]

        /*
         * 输出方法的测试
         */
        // 取最大
        Integer max = Arrays.asList(7,1,55,22,15,34).stream().max(Integer::compare).get();
        System.out.println(max);   // 结果:55
        // 取最小
        Integer min = Arrays.asList(7,1,55,22,15,34).stream().min(Integer::compare).get();
        System.out.println(min);   // 结果:1
        // 取非数字情况
        String maxString = Arrays.asList("5","323","124","235").stream().max((v,k) -> Integer.parseInt(v)- Integer.parseInt(k)).get();
        System.out.println(maxString);   // 结果:323
        // reduce迭代处理汇聚
        Double reduce = Arrays.asList(1.0,2.0,3.0,4.0,5.0,6.0,7.0).stream().reduce((a,b) -> (a+b)/2).get();
        System.out.println(reduce);   // 结果:6.015625
        // count统计数量
        long count = Arrays.asList(7,1,55,22,15,34).stream().filter(v -> v > 20).count();
        System.out.println(count);   // 结果:3
        // 输出List
        List list =  Arrays.asList(7,1,55,22,15,34).stream().collect(Collectors.toList());
        System.out.println(list);


        /*
         * 组合collect
         */
        // sum统计
        Integer sum = Arrays.asList(7,1,55,22,15,34).stream().collect(Collectors.summingInt(i->i));
        System.out.println(sum);   // 结果:134
        // avg平均
        double avg = Arrays.asList(7,1,55,22,15,34).stream().collect(Collectors.averagingDouble(i->i));
        System.out.println(avg);   // 结果:22.333333333333332
        // joining拼合
        String joining = Arrays.asList("A","B","C","D").stream().collect(Collectors.joining(",","(",")"));
        System.out.println(joining);   // 结果:(A,B,C,D)



        /*
         * 一些高级应用
         */
        // 分组
        Map<Object, List<Integer>> collect = Arrays.asList(7,1,55,22,15,34).stream().collect(Collectors.groupingBy(i->i/10));
        System.out.println(collect.toString()); // 结果{0=[7, 1], 1=[15], 2=[22], 3=[34], 5=[55]}
        // 分组并统计总数
        Map mapCollectSum = Arrays.asList(7,1,55,22,15,34).stream().collect(Collectors.groupingBy(i->i/10, Collectors.summingDouble(i->i)));
        System.out.println(mapCollectSum.toString()); // 结果{0=8.0, 1=15.0, 2=22.0, 3=34.0, 5=55.0}
        // 利用map抽取特定值
        List<Map<String,String>> listMap01 = new ArrayList<Map<String,String>>(){{
            add(new HashMap<String,String>(){{put("A","2");put("B","aaaa");put("C","001");}});
            add(new HashMap<String,String>(){{put("A","1");put("B","bbbb");put("C","002");}});
            add(new HashMap<String,String>(){{put("A","3");put("B","cccc");put("C","003");}});
        }};
        List listMapRet01 = listMap01.stream().map(v->v.get("A")).collect(Collectors.toList());
        System.out.println(listMapRet01.toString()); // 结果[2, 1, 3]







    }
}
