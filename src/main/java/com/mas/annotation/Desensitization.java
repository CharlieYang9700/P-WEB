package com.mas.annotation;

import com.mas.annotation.plan.DesensitizationPlanAbstract;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 脱敏注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Desensitization {
    Class <? extends DesensitizationPlanAbstract> method() default DesensitizationPlanAbstract.class;
}
