package com.datapipeline.algorithm.LinkedList;

/**
 * @author ly
 * @Date Create in 11:14 2021/4/6 0006
 * @Description
 */
public class LinkedListQueue<E> implements Queue<E> {
    private class Node{
        public E e;
        public Node next;

        public Node(E e,Node next){
            this.e = e;
            this.next = next;
        }

        public Node(E e){
            this(e,null);
        }

        public Node(){
            this(null,null);
        }

//        @Override
//        public String toString(){
//            return e.toString();
//        }
    }

    private Node head, tail;
    private int size;

    public LinkedListQueue(){
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    // 往队列（链表尾部追加元素）
    @Override
    public void enqueue(E e){

        if(tail == null) {
        Node node = new Node(e);
        head = node;
        tail = node;
        }else{
           Node node = new Node(e);
           tail.next = node;   // 将新创建的元素赋值给next
           tail = tail.next;

//            tail.next = new Node(e);
//            tail = tail.next;

//            tail = new Node(e);
        }
        size ++;
    }

    @Override
    public E dequeue(){
        if(isEmpty())
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");

        Node retNode = head;
        head = head.next;
        retNode.next = null;
        if(head == null) //考虑极端情况，当删除操作之前队列中只有一个元素时 tail此时还指向retNode
            tail = null;
        size --;
        return retNode.e;
    }

    @Override
    public E getFront(){
        if(isEmpty())
            throw new IllegalArgumentException("Queue is empty.");
        return head.e;
    }

    //显示节点信息
    public void display() {
        if (size > 0) {
            Node node = head;
            int tempSize = size;
            if (tempSize == 1) {//当前链表只有一个节点
                System.out.println("[" + node.e + "]");
                return;
            }
            while (tempSize > 0) {
                if (node.equals(head)) {
                    System.out.print("[" + node.e + "->");
                } else if (node.next == null) {
                    System.out.print(node.e + "]");
                } else {
                    System.out.print(node.e + "->");
                }
                node = node.next;
                tempSize--;
            }
            System.out.println();
        } else {//如果链表一个节点都没有，直接打印[]
            System.out.println("[]");
        }
    }

        public static void main(String[] args) {
        LinkedListQueue linkedListQueue = new LinkedListQueue();
        linkedListQueue.enqueue("a");
        linkedListQueue.enqueue("b");
        linkedListQueue.enqueue("c");
        linkedListQueue.enqueue("d");
        linkedListQueue.enqueue("e");
        linkedListQueue.display();
    }
}
