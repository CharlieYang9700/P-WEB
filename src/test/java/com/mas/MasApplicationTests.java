package com.mas;


import com.alibaba.excel.EasyExcel;
import com.mas.concurrency.LoggingWidget;
import com.mas.concurrency.Widget;
import com.mas.model.UserInfo;
import com.mas.zip.HuffmanNode;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Random;

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
    @Test
    void easyExcelExportText(){
        ArrayList<UserInfo> userInfos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(Long.parseLong(i+""));
            userInfo.setUsername("名字"+i);
            userInfo.setPassword("密码"+i);
            userInfos.add(userInfo);
        }
        EasyExcel.write("用户表.xlsx", UserInfo.class).sheet().doWrite(userInfos);

    }

}
