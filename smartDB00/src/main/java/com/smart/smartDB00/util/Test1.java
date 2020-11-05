package com.smart.smartDB00.util;

import java.util.Arrays;

class A{
    static{
        System.out.println("A");
    }
    A(){
        System.out.println("A1");
    }
}

class B extends A{
    static{
        System.out.println("B");
    }
    B(){
        System.out.println("B1");
    }
}

public class Test1 {
    public static void main(String[] args) {
        A a = new B();
        System.out.println();
//        int [] a=new int[3];
//        int [] b=null;
//        System.out.println(Arrays.toString(a));
//        System.out.println(Arrays.toString(b));
    }

//    private static void get(int[]a,int[] b){
//        a[0]=1;
//        b=a;
//        b[1]=2;
//        a=b;
//        a=null;
//    }
}
