package com.datapipeline.algorithm.LinkedList;


/**
 * @author ly
 * @Date Create in 9:28 2021/4/6 0006
 * @Description
 */
public class LinkedList<E> {
    class Node<T> {
        //包可见性
        Node<T> next;
        T data;

        /**
         * 构造函数
         *
         * @auther T-Cool
         * @description 构造一个新节点
         * 新元素与链表结合节点
         */
        public Node(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            System.out.println("调用了？？？？");
            System.out.println("怎么肥事");

            return  data==null ? null : data.toString();
        }
    }

    private Node<E> head; // 链表表头
    private int size; // 链表大小

    public LinkedList() {
        head = new Node<E>(null);
    }

    public Node<E> getHead() {
        return head;
    }

    /**
     * @description 向链表中指定位置的元素(0 - size),返回新节点
     * @param data
     * @param index
     * @throws Exception
     */
    public Node<E> add(E data, int index) throws Exception {

        if (index > size) {
            throw new Exception("超出范围...");
        }

        Node<E> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        Node<E> node = new Node<E>(data); // 将新元素链入链表
        cur.next = node;
        size++;
        return node;
    }

    /**
     * @description 向链表末尾添加元素,返回新节点
     * @param data
     * @throws Exception
     */
    public Node<E> add(E data) throws Exception {
        return add(data, size);
    }

    /**
     * @description 向链表尾部添加新节点
     * @param node
     */
    public void add(Node<E> node){
        Node<E> cur = head;
        while(cur.next != null){
            cur = cur.next;
        }
        cur.next = node;

        while(node != null){
            size ++;
            node = node.next;
        }
    }

    /**
     * @description 删除链表中指定位置的元素(0 ~ size-1)
     * @param index
     * @return
     * @throws Exception
     */
    public E remove(int index) throws Exception {
        if (index > size - 1 || index < 0) {
            throw new Exception("超出范围...");
        }

        Node<E> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }

        Node<E> temp = cur.next;
        cur.next = temp.next;
        temp.next = null;

        size--;
        return temp.data;
    }

    /**
     * @description 向链表末尾删除元素
     * @return
     * @throws Exception
     */
    public E remove() throws Exception {
        return remove(size - 1);
    }

    /**
     * @description 删除链表中的重复元素(外循环 + 内循环)
     * 时间复杂度：O(n^2)
     */
    public void removeDuplicateNodes() {
        Node<E> cur = head.next;
        while (cur != null) { // 外循环
            Node<E> temp = cur;
            while (temp != null && temp.next != null) { // 内循环
                if (cur.data.equals(temp.next.data)) {
                    Node<E> duplicateNode = temp.next;
                    temp.next = duplicateNode.next;
                    duplicateNode.next = null;
                    size --;
                }
                temp = temp.next;
            }
            cur = cur.next;
        }
    }

    /**
     * @description 找出单链表中倒数第 K 个元素(双指针法,相差 K-1 步)
     * @param k
     * @return 时间复杂度：O(n)
     */
    public Node<E> getEndK(int k) {
        Node<E> pre = head.next;
        Node<E> post = head.next;
        for (int i = 1; i < k; i++) { // pre 先走 k-1 步
            if (pre != null) {
                pre = pre.next;
            }
        }
        if (pre != null) {
            // 当 pre 走到链表末端时，post 正好指向倒数第 K 个节点
            while (pre != null && pre.next != null) {
                pre = pre.next;
                post = post.next;
            }
            return post;
        }
        return null;
    }

    /**
     * @description 返回链表的长度
     * @return
     */
    public int size(){
        return size;
    }

    //显示节点信息
    public void display(){
        if(size >0){
            Node node = head;
            int tempSize = size;
            if(tempSize == 1){//当前链表只有一个节点
                System.out.println("["+node.data+"]");
                return;
            }
            while(tempSize>0){
                if(node.equals(head)){
                    System.out.print("["+node.data+"->");
                }else if(node.next == null){
                    System.out.print(node.data+"]");
                }else{
                    System.out.print(node.data+"->");
                }
                node = node.next;
                tempSize--;
            }
            System.out.println();
        }else{//如果链表一个节点都没有，直接打印[]
            System.out.println("[]");
        }

    }

    public static void main(String[] args) throws Exception {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.display();
    }
}
