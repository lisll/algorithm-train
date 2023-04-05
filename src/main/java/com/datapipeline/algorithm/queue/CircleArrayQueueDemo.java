package com.datapipeline.algorithm.queue;

/**
 * Company: www.hecom.cn
 *
 * @description: 使用数组模拟环形队列
 * @author: ly
 */

public class CircleArrayQueueDemo {
    public static void main(String[] args) {
    CircleArray circleArray = new CircleArray(3);
    circleArray.addQueue(10);
    circleArray.addQueue(20);
    circleArray.addQueue(30);
    circleArray.showQueue();

    System.out.println("取出的元素为："+circleArray.getQueue());
    System.out.println("取出的元素为："+circleArray.getQueue());
    circleArray.showQueue();
//  circleArray.getQueue();
    circleArray.addQueue(30);
    circleArray.addQueue(50);
    System.out.println("此时队列头部元素为："+circleArray.headQueue());
    circleArray.addQueue(40);
    circleArray.getQueue();
    System.out.println("此时队列头部元素为："+circleArray.headQueue());
    circleArray.showQueue();
    }
}

class CircleArray{
    private int maxSize ; //数组的最大容量
    private int front;  //指向队列的第一个元素,也就是说 arr[front] 就是队列的第一个元素
    private int rear;   //指向队列的最后一个元素的后一个位置
    private int[] arr;  //实际存放数据的数组

    public CircleArray(int maxSize){
        this.maxSize=maxSize;
        arr = new int[maxSize];
    }

    // 判断队列是否满
    //这个位置的判断必须要==front而不能==0，否则这个队列就不能循环
    public boolean isFull() { return (rear + 1) % maxSize == front; }
    // 判断队列是否为空
    public boolean isEmpty() { return rear == front; }

    // 添加数据到队列
    public void addQueue(int n) {
        // 判断队列是否满
        if (isFull()) { System.out.println("队列满，不能加入数据~"); return; }
        //直接将数据加入
        arr[rear] = n;
        //将 rear 后移, 这里必须考虑取模
        rear = (rear + 1) % maxSize;   
    }

    // 获取队列的数据, 出队列
    public int getQueue(){
        // 判断队列是否空
        if(isEmpty()){
        // 通过抛出异常
        throw new RuntimeException("队列空，不能取数据");
        }
        //注意，想做成环形队列关键就在这个位置
        /**
         * 假如说，maxsize==5，我们一直往外取数据，当取到最后一个front==4时
         * 此时通过算法 front = (front + 1) % maxSize;会将front的值重新置位0（即初始值）
         * 所以又可以得到一个全新的数组
         */
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }

    // 显示队列的所有数据
    public void showQueue() {
        // 遍历
        if (isEmpty()) {
            System.out.println("队列空的，没有数据~~");
            return;
        }
        // 思路：从 front 开始遍历，遍历多少个元素
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    // 求出当前队列有效数据的个数
    public int size() {
        // rear = 2
        // front = 1
        // maxSize = 3
        return (rear + maxSize - front) % maxSize;
    }

    // 显示队列的头数据， 注意不是取出数据
    public int headQueue() {
        // 判断
        if (isEmpty()) {
            throw new RuntimeException("队列空的，没有数据~~");
        }
        return arr[front];
    }
}
