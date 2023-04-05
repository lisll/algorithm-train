package com.datapipeline.algorithm.LinkedList;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author ly
 * @Date Create in 9:19 2021/4/6 0006
 * @Description
 */
public class Node {
    Node next;
    T data;
    // 构造一个新节点,新元素与链表结合节点
    public Node(T data){
        this.data = data;
    }
}
