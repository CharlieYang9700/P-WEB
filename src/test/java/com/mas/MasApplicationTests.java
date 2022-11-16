package com.mas;


import com.mas.concurrency.LoggingWidget;
import com.mas.concurrency.Widget;
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

}
