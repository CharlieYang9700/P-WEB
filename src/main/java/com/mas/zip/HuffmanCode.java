package com.mas.zip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanCode {

    /**
     * 进行压缩
     * @param fileBytes
     */
    public void zip(byte[] fileBytes){
        //第一步 将读取的字节数组进行分类统计
        List<HuffmanNode> nodes = this.getNodes(fileBytes);
        //第二步 构建哈夫曼树
        HuffmanNode huffmanTree = this.createHuffmanTree(nodes);
        //第三步 获取哈夫曼编码
        //第四步 根据哈夫曼树和哈夫曼编码进行压缩
    }

    /**
     * 统计分类
     * @param fileBytes
     * @return
     */
    public List<HuffmanNode> getNodes(byte[] fileBytes){
        List<HuffmanNode> nodes = new ArrayList<>();
        HashMap<Byte, Integer> map = new HashMap<>();
        for (byte fileByte : fileBytes) {
            if(map.containsKey(fileByte)){   
                map.put(fileByte,map.get(fileByte)+1);
            }else {
                map.put(fileByte,1);
            }
        }
        for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
            nodes.add(new HuffmanNode(entry.getKey(),entry.getValue()));
        }
        return nodes;
    }

    public HuffmanNode createHuffmanTree(List<HuffmanNode> huffmanNodes){

        while (huffmanNodes.size()>1){
            huffmanNodes.sort(HuffmanNode::compareTo);

            HuffmanNode left = huffmanNodes.get(0);
            HuffmanNode right = huffmanNodes.get(1);
            HuffmanNode parent = new HuffmanNode(null,left.getWeight() + right.getWeight());

            parent.setLeft(left);
            parent.setRight(right);

            huffmanNodes.remove(0);
            huffmanNodes.remove(1);
            huffmanNodes.add(parent);

        }

        return huffmanNodes.get(0);
    }

    /**
     * 将构造好的哈夫曼树生成哈夫曼编码存入map中
     * @param root
     * @return
     */
    public Map<Byte,String> getHuffmanCode(HuffmanNode root ){
        StringBuilder stringBuilder = new StringBuilder();
        if(root == null){
            return null;
        }else {
            this.getHuffmanCode(root.getLeft(),"1",stringBuilder);
            this.getHuffmanCode(root.getRight(),"0",stringBuilder);
        }
        return null;
    }

    public void getHuffmanCode(HuffmanNode node,String code,StringBuilder tempCode){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(code);
        if(node != null){
            if(node.getData() != null){
                this.getHuffmanCode(node.getLeft(),"1",stringBuilder);
                this.getHuffmanCode(node.getRight(),"0",stringBuilder);
            }else {

            }

        }
    }

}
