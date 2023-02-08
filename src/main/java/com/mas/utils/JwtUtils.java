package com.mas.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.mas.model.UserInfo;
import jdk.internal.org.objectweb.asm.Handle;

import java.util.*;

public class JwtUtils {
    private static String secret = "Xaljenxhshdlj";
    public static String createToken(String userId){
        HashMap<String, String> mapClam  = new HashMap<>();
        mapClam.put("userId",userId);
        Integer expireSeconds = 2 * 60 * 60;//两小时

        return createToken(mapClam,expireSeconds);
    }

    public static String createToken(Map<String,String> clam,Integer expiresSeconds){
        String token = null;
        //头部
        HashMap<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        JWTCreator.Builder jwtBuilder = JWT.create();
        jwtBuilder.withHeader(header);

        //载荷
        if(Objects.nonNull(clam) && !clam.isEmpty()){
            Set<Map.Entry<String, String>> entries = clam.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                jwtBuilder.withClaim(entry.getKey(),entry.getValue());
            }
        }
        //过期时间
        if(Objects.nonNull(expiresSeconds) && expiresSeconds > 0){
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.SECOND,expiresSeconds);
            jwtBuilder.withExpiresAt(instance.getTime());
        }
        jwtBuilder.withIssuedAt(new Date());

        token = jwtBuilder.sign(Algorithm.HMAC256(secret));
        return token;
    }
}
