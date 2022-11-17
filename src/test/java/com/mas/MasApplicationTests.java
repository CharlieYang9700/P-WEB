package com.mas;


import com.mas.concurrency.LoggingWidget;
import com.mas.concurrency.Widget;
import com.mas.zip.HuffmanNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MasApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("测试单元");
    }

    @Test
    void LockTest(){
        Widget widget = new Widget();
        LoggingWidget loggingWidget = new LoggingWidget();
        loggingWidget.doSomething();
    }


    @Test
    void HuffmanNodeTest(){
        HuffmanNode root = new HuffmanNode(new Byte("111"),1);
        HuffmanNode node1 = new HuffmanNode(new Byte("112"),2);
        HuffmanNode node2 = new HuffmanNode(new Byte("113"),3);
        HuffmanNode node3 = new HuffmanNode(new Byte("114"),4);


        root.setLeft(node1);
        root.setRight(node2);
        node2.setLeft(node3);

        root.preOrder();


        System.out.println(node1.compareTo(node2));
        System.out.println(node1.compareTo(node3));
        System.out.println(node1.compareTo(node1));
        System.out.println(node2.compareTo(node1));

    }

}
