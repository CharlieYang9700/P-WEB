package com.mas.utils;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionUtil {
    public static void doAfterTransaction (DoTransactionCompletion doTransactionCompletion){
        if(TransactionSynchronizationManager.isActualTransactionActive()){
            TransactionSynchronizationManager.registerSynchronization(doTransactionCompletion);
        }
    }

    @Transactional
    public void doTx(){
        //执行业务

        TransactionUtil.doAfterTransaction(new DoTransactionCompletion(()->{
            //mq发送消息
        }));
    }
}
class DoTransactionCompletion implements TransactionSynchronization {
    private Runnable runnable;

    public DoTransactionCompletion(Runnable runnable){
        this.runnable = runnable;
    }

    @Override
    public void afterCompletion(int status) {
        if(status == TransactionSynchronization.STATUS_COMMITTED){
            this.runnable.run();
        }
    }
}