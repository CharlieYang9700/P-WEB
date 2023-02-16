package com.mas;


import cn.hutool.dfa.WordTree;
import com.alibaba.excel.EasyExcel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mas.concurrency.LoggingWidget;
import com.mas.concurrency.Widget;
import com.mas.model.UserInfo;
import com.mas.utils.BCrypt;
import com.mas.zip.HuffmanNode;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


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

    @Test
    void getTokenPayload (){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NzU5MTQ0OTMsInVzZXJJZCI6IjgzMDM3ODE2MDM2OTIxMzQ0MCIsImlhdCI6MTY3NTkwNzI5M30.D3MwhUmqeJVWFWb9oLpgayE9WCA0HPOeV2joJOfMZoE";
        DecodedJWT decode = JWT.decode(token);
        Claim userId = decode.getClaim("userId");
        System.out.println(userId);
    }
    @Test
    void BCryptText(){
        String salt = BCrypt.gensalt(10, new SecureRandom());
        String hashpw = BCrypt.hashpw("123456", salt);
        System.out.println(BCrypt.checkpw("123456",hashpw));

    }


    @Test
    void tuoMin(){
        Trie<String, String> trie = new PatriciaTrie<>();
        trie.put("Abigail", "student");
        trie.put("Abi", "doctor");
        trie.put("Annabel", "teacher");
        trie.put("Christina", "student");
        trie.put("Chris", "doctor");
        Assertions.assertTrue(trie.containsKey("Abigail"));
        assertEquals("{Abi=doctor, Abigail=student}", trie.prefixMap("Abi").toString());
        assertEquals("{Chris=doctor, Christina=student}", trie.prefixMap("Chr").toString());
    }

    @Test
    void test3(){
        WordTree wordTree = new WordTree();
        wordTree.addWord("大");
        wordTree.addWord("大憨憨");
        wordTree.addWord("憨憨");
        String text = "那人真是个大憨憨！";
        // 获得第一个匹配的关键字
        String matchStr = wordTree.match(text);
        System.out.println(matchStr);
        // 标准匹配，匹配到最短关键词，并跳过已经匹配的关键词
        List<String> matchStrList = wordTree.matchAll(text, -1, false, false);
        System.out.println(matchStrList);
        //匹配到最长关键词，跳过已经匹配的关键词
        List<String> matchStrList2 = wordTree.matchAll(text, -1, false, true);
        System.out.println(matchStrList2);
    }

}
