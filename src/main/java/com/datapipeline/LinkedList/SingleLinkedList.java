package com.datapipeline.LinkedList;

/**
 * @author ly
 * @Date Create in 10:43 2021/4/6 0006
 * @Description
 */
public class SingleLinkedList {
        private int size;   // 链表大小
        private Node head;  // 头节点

        class Node{
         private Object data; // 链表中的元素
          private Node next;  // 下一个节点
          public Node(Object data){
                this.data=data;
            }
        }
        // 在链表头部添加元素
    public Object addHead(Object data){
            Node newHead = new Node(data);
            if(size ==0){
                head = newHead;
            }else{
                newHead.next=head;
                head = newHead;
            }
            size++;
            return data;
    }

    //显示节点信息
    public void display() {
        if (size > 0) {
            Node node = head;
            int tempSize = size;
            if (tempSize == 1) {//当前链表只有一个节点
                System.out.println("[" + node.data + "]");
                return;
            }
            while (tempSize > 0) {
                if (node.equals(head)) {
                    System.out.print("[" + node.data + "->");
                } else if (node.next == null) {
                    System.out.print(node.data + "]");
                } else {
                    System.out.print(node.data + "->");
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
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addHead("a");
        singleLinkedList.addHead("b");
        singleLinkedList.addHead("c");
        singleLinkedList.addHead("d");
        singleLinkedList.addHead("e");
        singleLinkedList.display();
    }

}
