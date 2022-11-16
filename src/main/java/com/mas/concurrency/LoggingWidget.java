package com.mas.concurrency;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class LoggingWidget extends Widget{
    @Override
    public synchronized void doSomething() {
        System.out.println("先执行");
        super.doSomething();
    }




}