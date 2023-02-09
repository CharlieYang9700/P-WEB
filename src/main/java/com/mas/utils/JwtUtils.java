package com.mas.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

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
        //签证
        token = jwtBuilder.sign(Algorithm.HMAC256(secret));
        return token;
    }

    public static Long getUserId(String token) throws BizException {
        if(StringUtils.isEmpty(token)){
            throw new BizException("你还未登录，请先登录");
        }
        String userIdStr = JWT.decode(token).getClaim("userId").asString();
        if(StringUtils.isEmpty(userIdStr)){
            throw new BizException("你已经下线，请重新登录");
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        if(!pattern.matcher(userIdStr).matches()){
            throw new BizException("非法登录用户");
        }
        return Long.parseLong(userIdStr);
    }
}
