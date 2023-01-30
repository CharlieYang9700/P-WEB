package com.mas.controller;

import com.mas.annotation.Login;
import com.mas.export.CSVUtils;
import com.mas.model.Student;
import com.mas.model.UserInfo;
import com.mas.service.UserInfoService;
import com.mas.utils.InsertNullValue;
import com.sun.deploy.net.HttpRequest;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("test/")
public class TestController {
    @Autowired
    private RedisTemplate redisTemplate;
    public static Integer num = 1;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("hello")
    @CrossOrigin
    @Login
    public List<Student> test() throws Exception {
        //redisTemplate.opsForHash().put("redisHash","stock",10);
        System.out.println("调用"+ num++ +"次");
        Student s1 = new Student("李四", "男");
        Student s2 = new Student("张三", "男");
        ArrayList<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
/*        for (Student student : students) {
            InsertNullValue.setObjectEmptyValue(student,null);
        }*/
        return students;
    }

    @PostMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        List exportData = new ArrayList<Map>();
        Map row1 = new LinkedHashMap<String, String>();
        row1.put("1", "11");
        row1.put("2", "12");
        row1.put("3", "13");
        row1.put("4", "14");
        exportData.add(row1);
        row1 = new LinkedHashMap<String, String>();
        row1.put("1", "21");
        row1.put("2", "22");
        row1.put("3", "23");
        row1.put("4", "24");
        exportData.add(row1);
        LinkedHashMap map = new LinkedHashMap();

        //设置列名
        map.put("1", "第一列名称");
        map.put("2", "第二列名称");
        map.put("3", "第三列名称");
        map.put("4", "第四列名称");
        //这个文件上传到路径，可以配置在数据库从数据库读取，这样方便一些！
        String path = "D:\\CVSFile\\";

        CSVUtils.exportDataFile(response,exportData,map,path,"test");
    }

    @GetMapping("user")
    public List<UserInfo> getAllUser(){
        return userInfoService.getAllUser();
    }


}
