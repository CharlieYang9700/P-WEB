package com.mas.zip;

import lombok.Data;

/**
 * 哈夫曼树节点数据结构
 */
@Data
public class HuffmanNode implements Comparable<HuffmanNode>{
    private Byte data;
    private int weight;//字符出现的次数（权重）
    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode() {
    }

    public HuffmanNode(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.weight-o.weight;
    }

    /**
     * 前序遍历
     */
    public void preOrder(){
        System.out.println(this);
        if(this.left != null){
            this.left.preOrder();
        }
        if(this.right != null){
            this.right.preOrder();
        }
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "data=" +data+","+
                "weight="+weight+
                "}";
    }
}
