package com.datapipeline.algorithm.queue;


/**
 * Company: www.hecom.cn
 *
 * @description: 使用数组模拟一个队列
 * @author: ly
 */

public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(3);
        arrayQueue.showQueue();  // 此时队列还为空
        arrayQueue.addQueue(10);
        arrayQueue.addQueue(20);
        arrayQueue.addQueue(30);
//        arrayQueue.addQueue(40);
        int queue = arrayQueue.getQueue();
        System.out.println("取出的数据为： "+queue);
        arrayQueue.getQueue();
//        arrayQueue.getQueue();
//        arrayQueue.getQueue();
        int peek = arrayQueue.peek();
        System.out.println("此时队列头部数据为: "+peek);
//        arrayQueue.peek();
        arrayQueue.showQueue();
    }
}


class ArrayQueue{
    /**
     * 入队列时，rear+1  front 不动
     * 出队列时，front+1,rear 不动
     * 所以当front==rear时，证明整个队列已经空了（初始条件下front==rear==-1）
     */
    private int maxSize; // 队列的最大容量
    private int front;  // 指向队列头部之前的一个指针
    private int rear ;  // 指向队列尾部的指针
    private int[] arr; //实际存储数据的数组

    public ArrayQueue(int maxSize){
        this.maxSize=maxSize;
        front=-1;
        rear=-1;
        arr = new int[maxSize];
    }
    //判断队列是否已满
    public boolean isFull(){
        return rear == maxSize-1;
    }
    //判断队列是否为空
    public boolean isEmpty(){
        return front==rear;
    }

    // 向队列中添加元素
    public void addQueue(int data){
        // 判断队列是否已满
        if(isFull()){
        throw new RuntimeException("队列已经满了，无法再插入数据");
        }
        rear++;   // rear 后移
        arr[rear]=data;
    }

    // 出队
    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列为空，无法取出元素");
        }
        front++;   // front 后移
        return arr[front];
    }

    // 显示队头元素，但是不是取出
    public int peek(){
        if(isEmpty()){
            throw new RuntimeException("队列为空，无法取出元素");
        }
        return arr[front+1];
    }

    //
    public void showQueue(){
        if(isEmpty()){
            System.out.println("队列空的，没有数据~~"); return;
        }
        for (int i = 0; i < arr.length; i++) { System.out.printf("arr[%d]=%d\n", i, arr[i]); }
    }
}
