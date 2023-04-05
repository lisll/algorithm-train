package com.datapipeline.algorithm.stack;

/**
 * Company: www.hecom.cn
 *
 * @description: 栈实现综合计算器
 * @author: ly
 */

public class Calculator {
    public static void main(String[] args) {
        String express = "7*2*3-5+6-51+3-4";  //需要计算的表达式
        //创建两个栈，要给数栈，一个符号栈
        ArrayStack numStack = new ArrayStack(10);
        ArrayStack operStack = new ArrayStack(10);
        //定义相关的变量
        int index = 0; //索引值，用于扫描
        int num1 = 0;
        int num2 = 0;
        int res = 0;
        int oper = 0;
        char ch =' ';  //将每次扫描的字符存放在ch变量中
        String keepNum = ""; //用于拼接 多位数
        while(true){
            //依次得到 expression 的每一个字符
            ch = express.substring(index,index+1).charAt(0);
            if(operStack.isOper(ch)){   //如果是运算符
                if(!operStack.isEmpty()){   //判断当前符号栈是否为空
        //如果符号栈有操作符，就进行比较,如果当前的操作符的优先级小于或者等于栈中的操作符, 就需要从数栈中 pop 出两个数,
        // 再从符号栈中 pop 出一个符号，进行运算，将得到结果，入数栈，然后将当前的操作符入符号栈
                if(operStack.priority(ch)<=operStack.priority(operStack.peek())){
                    num1 = numStack.pop();
                    num2 = numStack.pop();
                    oper = operStack.pop();
                    res = numStack.cal(num1,num2,oper);
                    //把运算结果入数栈
                    numStack.push(res);
                    //将当前操作符入符号栈
                    operStack.push(ch);
                }else {  // 如果当前操作符的优先级大于栈中的操作符，就直接入符号栈
                    operStack.push(ch);
                }
                }else { // 如果当前的符号栈为空，则直接入符号栈
                    operStack.push(ch);
                }
            }else{  // 如果当前的符号为数-字则直接进数栈
//                numStack.push(ch-48); //? "1+3" '1' => 1

                //1. 当处理多位数时，不能发现是一个数就立即入栈，因为他可能是多位数
                //2. 在处理数，需要向 expression 的表达式的 index 后再看一位,如果是数就进行扫描，如果是符号 才入栈
                //3. 因此我们需要定义一个变量 字符串，用于拼接
                //处理多位数
                keepNum += ch;
                //如果 ch 已经是 expression 的最后一位，就直接入栈
                if(index==express.length()-1){
                    numStack.push(Integer.parseInt(keepNum));
                }else{
                    //判断下一个字符是不是数字，如果是数字，就继续扫描，如果下一个是运算符，就将当前数字入栈
                    if(operStack.isOper(express.substring(index+1,index+2).charAt(0))){
                    //如果后一位是运算符，则入栈 keepNum = "1" 或者 "123"
                        numStack.push(Integer.parseInt(keepNum));
                        keepNum="";
                    }
                }
            }
        //让 index + 1, 并判断是否扫描到 expression 最后.
            index++;
            if(index>=express.length()){
                break;
            }
        }

        //当表达式扫描完毕，就顺序的从 数栈和符号栈中 pop 出相应的数和符号，并运行.
        while (true){
            //如果符号栈为空，则计算到最后的结果, 数栈中只有一个数字【结果】
            if(operStack.isEmpty()){
                break;
            }
            num1=numStack.pop();
            num2=numStack.pop();
            oper=operStack.pop();
            res=numStack.cal(num1,num2,oper);
            numStack.push(res);
        }

        //将数栈的最后数，pop 出，就是结果
        int res2 = numStack.pop();
        System.out.printf("表达式 %s = %d", express, res2);
    }

}

class ArrayStack{
    private int maxSize;    //表示栈的容量
    private int[] stack;
    private int top=-1; //表示栈顶

    public ArrayStack(int maxSize){
        this.maxSize=maxSize;
        stack=new int[maxSize];
    }

    //查看栈顶值，可以返回当前栈顶的值, 但是不是真正的 pop
    public int peek() { return stack[top]; }

    //栈满
    public boolean isFull(){
       return top==maxSize-1;
    }

    //栈空
    public boolean isEmpty(){
        return top==-1;
    }

    //入栈
    public void push(int data){
        if(isFull()){
            System.out.println("栈已满，无法入栈");
            return;
        }
        top++;
        stack[top]=data;   //将data赋值给当前当前索引
    }
    //出栈
    public int pop(){
        if(isEmpty()){
            System.out.println("栈已空，无法出栈");
            return 10000;
        }
        int value = stack[top];
        top--;
        return value;
    }

    // 遍历栈，注意这个时从栈顶开始遍历
    public void display(){
        if(isEmpty()){
            System.out.println("栈已空，无法遍历");
            return;
        }
        for(int i = top ; i>=0;i--){
            System.out.printf("stack[%d]=%d\n",i,stack[i]);
        }
    }
    //返回运算符的优先级，优先级是程序员自己确定，使用数字表示，数字越大，优先级越高
    public int priority(int oper){
        if(oper=='*' || oper =='/'){
            return 1;
        }else if(oper=='+' || oper=='-'){
            return 0;
        }else{
            return -1;
        }
    }
    // 判断是不是一个运算符
    public boolean isOper(char oper){
        return oper=='*' || oper=='/' || oper =='+' || oper=='-';
    }

    //计算方法
    public int cal(int num1,int num2,int oper){
        int result=0;   // 临时变量用于存放计算结果
        switch (oper){
            case '+':
                result = num1+num2;
                break;
            case '-':
                result = num2-num1; //注意顺序，搞反就错来
                break;
            case '*':
                result = num1*num2;
                break;
            case '/':
                result = num2/num1;
                break;
            default:
                break;
        }
        return result;
    }
}
