package com.mas.utils;

import com.mas.annotation.FieldFlag;
import com.mas.model.Student;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class InsertNullValue {

    public static void setObjectEmptyValue(Object object, List<Student> data) throws Exception {

        if (object == null) {
            return;
        }
        // 拿到该类
        Class<?> clz = object.getClass();
        // 获取实体类的所有属性，返回Field数组
        Field name = clz.getDeclaredField("name");
        name.setAccessible(true);
        System.out.println("这是一个值："+name.get(object));


        Field[] fields = clz.getDeclaredFields();

        for (Field field : fields) {// --for() begin

            System.out.println("测试字段名"+field.getName());
            field.setAccessible(true);
            if (!field.getGenericType().toString().contains("class java.lang.String")) {
                continue;
            }
            System.out.println(field.get(object));
            if (field.get(object) != null) {
                continue;
            }
            FieldFlag annotation = field.getAnnotation(FieldFlag.class);
            if(annotation == null) {
                continue;
            }
            String phisKind = annotation.name();
            String fieldValue = (String)field.get(object);
//            for (Student datum : data) {
//                if(datum.getComPareDesc().equals(phisKind) && datum.getPhisCode().equals(fieldValue) ){
//                    field.set(object,datum.getTargetCode());
//                }
//            }

            System.out.println(annotation.name());
            System.out.println(annotation.value());
            field.set(object, "-");
            System.out.println("新值"+field.get(object));
            //System.out.println("属性名：" + field.getName());
            //System.out.println("属性类型：" + field.getGenericType());
            //System.out.println("属性值：" + String.valueOf(field.get(object)));
        }
    }

}
