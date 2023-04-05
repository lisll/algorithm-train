package com.datapipeline.test;

import java.util.function.Supplier;

/**
 * Company: www.hecom.cn
 *
 * @author ly
 */
public class SupplierDemo {
    public static void main(String[] args) {
        Supplier<Persion> sup= Persion::new;
        System.out.println("............");
        Persion persion = sup.get();
        System.out.println("persion: "+persion);
        Persion persion1 = sup.get();
        System.out.println("persion1 : "+persion1);
    }
}

class Persion{
    int age ;
    public Persion(){
        System.out.println("age: "+age);
    }
}
