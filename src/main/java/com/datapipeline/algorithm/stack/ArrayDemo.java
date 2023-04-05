package com.datapipeline.algorithm.stack;

/**
 * Company: www.hecom.cn
 *
 * @description: 使用数组来模拟栈
 * @author: ly
 */

public class ArrayDemo {
    public static void main(String[] args) {
        StackDemo stackDemo = new StackDemo(4);
        stackDemo.push(1);
        stackDemo.push(4);
        stackDemo.push(6);
        stackDemo.push(7);
        stackDemo.push(0);
        stackDemo.push(9);

        stackDemo.display();

        stackDemo.pop();
        stackDemo.pop();
        stackDemo.display();
        stackDemo.pop();
        stackDemo.display();
        stackDemo.pop();
        stackDemo.pop();
        stackDemo.display();

        //再次入栈
        stackDemo.push(100);
        stackDemo.display();
    }

}

// StackDemo 表示一个栈结构
class StackDemo{
    private int maxSize;    // 定义栈的容量
    private int[] stack ;  //使用数组来模拟栈
    int top = -1;  //定义栈顶
    // 初始化栈
    public StackDemo(int maxSize){
        this.maxSize= maxSize;
        stack = new int[maxSize];
    }

    // 判断栈满栈空
    public boolean isFull(){
        return top == maxSize-1;
    }
    public boolean isEmpty(){
        return top ==-1;
    }

    // 入栈
   public void push(int data){
        // 入栈之前必须要先判断是否栈满
       if(isFull()){
           System.out.println("栈已满，禁止入栈");
           return;
       }
        top++;
        stack[top]=data;
    }
    // 出栈
    public int pop(){
        // 出栈之前先判断是否栈空
      if(isEmpty()){
//          throw new RuntimeException("栈已空，不能出栈");
          System.out.println("栈已空，不能出栈");
          return 1000;
      }
      int value = stack[top];
      top--;
      return value;
    }

    // 遍历栈，注意遍历时要从栈顶开始遍历
    public void display(){
        if(isEmpty()){
            System.out.println("栈为空，无法遍历");
            return;
        }
       for(int i = top ;i>=0;i--){
           System.out.printf("stack[%d]=%d\n",i,stack[i]);
       }
    }
}
