package com.mas.utils;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;

public class IdGeneratorSnowflake {


    private static final int SEED = 255;

    private static DefaultKeyGenerator defaultKeyGenerator = new DefaultKeyGenerator();

    static {
        DefaultKeyGenerator.setWorkerId(new Random().nextInt(SEED));
    }

    /**
     * 生成流水号
     * @return string
     */
    public static long generateId() {
        return defaultKeyGenerator.generateKey().longValue();
    }

}
