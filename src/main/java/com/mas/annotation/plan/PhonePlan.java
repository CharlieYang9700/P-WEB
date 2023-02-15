package com.mas.annotation.plan;

import org.apache.commons.lang3.StringUtils;

public class PhonePlan extends DesensitizationPlanAbstract<String>{
    private final static int BEFORE_LEN = 3;

    private final static int AFTER_LEN = 2;

    @Override
    public String replace(String value) {
        System.out.println("进入了方法");
        if(StringUtils.isEmpty(value)){
            return value;
        }

        int len = value.length();
        return String.format("%s****%s",value.substring(0,Math.min(BEFORE_LEN,len)),value.substring(Math.max(len-AFTER_LEN,0)));
    }
}
