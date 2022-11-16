package com.mas.zip;

/**
 * 哈夫曼树节点数据结构
 */
public class HuffmanNode implements Comparable<HuffmanNode>{
    Byte data;
    int weight;//字符出现的次数（权重 ）
    HuffmanNode left;
    HuffmanNode right;



    @Override
    public int compareTo(HuffmanNode o) {
        return this.weight-o.weight;
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "data=" +data+
                "weight="+weight+
                "}";
    }
}
