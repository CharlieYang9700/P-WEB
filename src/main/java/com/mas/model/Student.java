package com.mas.model;

import com.mas.annotation.FieldFlag;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

@Data
public class Student {
    public Student(){}
    public Student(String name,String sex){
        this.name = name;
        this.sex = sex;
    }
    private String name;
    private String sex;

    private String nickname;

    @FieldFlag(value = "编码")
    private String code;

    private String phisCode;

    private String targetCode;

    private String comPareDesc;

    public static void main(String[] args) {
        ArrayList<Field> fields = new ArrayList<>();
        Student student = new Student("乔峰","男");
        Class temptClass = student.getClass();
        System.out.println(temptClass.getName());
        while (temptClass != null && temptClass.getName().toLowerCase().equals("com.mas.model.Student")){
            System.out.println("#################");
            fields.addAll(Arrays.asList(temptClass.getDeclaredFields()));
        }
        fields.addAll(Arrays.asList(temptClass.getDeclaredFields()));
        fields.forEach(System.out::println);
    }

}