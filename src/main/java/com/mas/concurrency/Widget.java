package com.mas.concurrency;


import java.util.Vector;
import java.util.concurrent.locks.Lock;

public class Widget {
    static Vector<Integer> vector = new Vector<>();
    public synchronized void doSomething(){

        System.out.println("第一个方法");
    }

    public void VectorTest(Integer element){
        if(!vector.contains(element)){
            vector.add(element);
        }
    }


}

