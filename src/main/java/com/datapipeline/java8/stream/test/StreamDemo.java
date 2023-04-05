package com.datapipeline.java8.stream.test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**对java8中新增的Stream类型进行测试
 * Company: www.hecom.cn
 *
 * @author ly
 */
public class StreamDemo {
    public static void main(String[] args) throws Exception{
//    operateStream();
//        testReduce();
//        testLimitAndSkip();
        testLimitAndSkipwithSorted();
    }



    // 1,根据不同方式构建 Stream流
    public static Stream<String> buildStream() throws Exception {
        //（1）从Collection和数组获得
        Stream<String> stream1 = new ArrayList<String>().stream();
        String[] string = new String[3];
        string[0]="o";
        string[0]="p";
        string[0]="q";
        Stream<String> stream2 = Arrays.stream(string);


        //（2）从BufferedReader获得
//        Stream<String> lines = new BufferedReader(new FileReader("")).lines();
        //（3）静态工厂
        IntStream range = IntStream.range(1,10);
        //（4）自己构建
        // (5) 其他
        IntStream ints = new Random().ints();
//        Stream<JarEntry> stream3 = new JarFile("").stream();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("a","1");
        hashMap.put("d","4");
        hashMap.put("b","2");
        hashMap.put("c","3");
        hashMap.put("e","5");
        hashMap.put("f","6");
        Stream<String> stream = hashMap.values().stream();
        return  stream;
    }

    // 2,将流转换为其他数据结构
    public static void transf(Stream<String> stream){
        String[] objects = stream.toArray(String[]::new);
        List<String> collect = stream.collect(Collectors.toList());
        ArrayList<String> collect1 = stream.collect(Collectors.toCollection(ArrayList<String>::new));
        Set<String> collect2 = stream.collect(Collectors.toSet());
        Stack stack = new Stack();
        Stack<String> collect3 = stream.collect(Collectors.toCollection(Stack::new));
    }

    // 3，操作流
    public static void operateStream() throws Exception{
        buildStream()
                .map(line->new Integer(line))
                .filter(data->data<5)
                .map(line->line*line)
                .reduce(9,(a,b)->a+b)
                ;
//                .collect(Collectors.toList())
//                .forEach(line->System.out.println(line));

        Stream.of("one","two","three","four")
                .filter(data->data.length()>3)
                .peek(e->System.out.println("value: "+e)).map(String::toLowerCase)
                .peek(e->System.out.println("after peek: "+e))
                .collect(Collectors.toList())
                .forEach(data->System.out.println(data));
    }

    public static void testReduce(){
                Optional<String> reduce = Stream.of("1", "2","3").reduce(String::concat);
                String mm = reduce.orElse("mm");
                System.out.println(mm);
                // 求最小值，minValue = -3.0
                double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
                System.out.println(minValue);
                // 求和，sumValue = 10, 有起始值
                int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
                System.out.println("sumValue: "+sumValue);
                // 过滤，字符串连接，concat = "ace"
             String reduce1 = Stream.of("a", "B", "c", "D", "e", "F").filter(x -> x.compareTo("Z") < 0).reduce("", String::concat);
            System.out.println(reduce1);

    }

    //limit 和 skip 对运行次数的有影响
    public static void testLimitAndSkip() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<String> personList2 = persons.stream().
                map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(personList2);
    }
    private static class Person {
        public int no;
        private String name;
        public Person (int no, String name) {
            this.no = no;
            this.name = name;
        }
        public String getName() {
            String threadName = Thread.currentThread().getName();
            System.out.println(this.name+"->  threadName: "+threadName);
            return this.name;
        }
    }

    //// limit 和 skip 对 sorted 后的运行次数无影响
    public static void testLimitAndSkipwithSorted() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
//        List<Person> personList2 = persons.stream().sorted((p1, p2) ->
//                p1.getName().compareTo(p2.getName())).limit(2).collect(Collectors.toList());
//        List<Person> personList2 = persons.stream().sorted(Comparator.comparing(Person::getName)).limit(2).collect(Collectors.toList());
        List<Person> personList2 = persons.stream().limit(2).sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
        System.out.println(personList2);


    }

}
