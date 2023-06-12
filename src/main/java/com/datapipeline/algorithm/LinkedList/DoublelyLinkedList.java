package com.datapipeline.algorithm.LinkedList;

/**
 * 实现双向链表
 */
public class DoublelyLinkedList<T> {

    private static int size;
    private Node<T> head;  // 首节点
    private Node<T> tail;   // 尾节点

    // 存储的数据 T data、直接前驱节点 Node pri、直接后继节点 Node next
    public static class Node<T>{
        T data;
        Node pred;   // 前驱节点
        Node next;   // 后驱节点

        public Node(T data){
            this.data = data;
        }

        public Node getPred() {
            return pred;
        }

        public void setPred(Node pred) {
            this.pred = pred;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    public static int getSize() {
        return size;
    }


    /**
     * 头插法
     * 如果链表为空
     *      头结点 = 插入的节点 = 尾结点
     * 不为空则:
     *      1、将头节点赋值给插入节点的next节点(将插入节点的next节点指向头节点)  （此时头节点是有值的）
     *      2、将插入节点赋值给头节点的前驱节点(调整头节点的前驱节点为插入节点)
     *      3、将新节点设置为头结点
     *      4、size++
     * @param data
     * @return
     */
    public boolean addFirst(T data){
     Node<T> node = new Node<>(data);
        if(size==0){
            head = node;
            tail = head;
        }else{
             node.next = head;    // node后驱指向head  (head赋值给node的后驱节点) 注意"指向"这个词，A指向B，指的是A=B 即B赋值给A
             head.pred = node;    // head前驱指向node  (node赋值给head的前驱节点)
             head = node;        //  将新节点设置为node
        }
        size++;
        return true;
    }

    /**
     * 尾插法
     * 为空时：
     *      等价于头插，直接调用addFirst
     * 不为空：
     *      1、调整尾结点的后继next指向新节点
     *      2、调整新节点的前驱pri指向尾结点
     *      3、更新尾结点为新的节点
     *      4、size++
     * @param data
     * @return
     */
    public boolean addLast(T data){
        Node<T> node = new Node<>(data);
       if(size==0){
           return addFirst(data);
       }else{
           tail.next = node;   // tail后驱指向node   (node赋值给tail的后驱节点)
           node.pred = tail;   // node前驱指向tail   (tail赋值给node的前驱节点）
           tail = node;        // tail指向node      (node赋值给tail)
           size++;
       }
       return true;
    }

    public void detailPrint(){
        if (size == 0) {
            System.out.println("该链表为空!");
        }
        Node<T> node = head;
        while (node != null) {
            System.out.print("前驱节点值：");
            System.out.printf("%-5s",node.pred == null ? "null\t" : node.pred.getData()+"\t");
            System.out.print("当前节点值：");
            System.out.printf("%-6s",node.getData() + "\t");
            System.out.print("后继节点值：");
            System.out.printf("%-5s",node.next == null ? "null\t" : node.next.getData()+"\t");
            System.out.println();
            node = node.getNext();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        DoublelyLinkedList<Integer> list = new DoublelyLinkedList<>();
//        list.addFirst(1);
//        list.addFirst(2);
//        list.addFirst(3);
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.detailPrint();

    }
}
