package com.datapipeline.sort;

import java.util.Arrays;

/**
 * Company: www.hecom.cn
 *
 * @description: 选择排序
 * @author: ly
 */

public class Select_sort {
    public static void main(String[] args) {
    int[] array = {1,2,-10,9,4,6,8};
    int[] ints = selectionSort(array);
    System.out.println(Arrays.toString(ints));

    }

    public static int[] selectionSort(int[] array){
        if (array.length == 0) {
            return array;
        }

        for(int i =0;i<array.length;i++){
            int minIndex = i;   // 假定当前i就是当前这一轮循环中的最小值所在的索引
            int minValue = array[i];    // 最小索引对应的最小值
            for(int j = i+1;j<array.length;j++){    //循环后面的 array.length-1 的值
                if(minValue>array[j]){   //假如假定的最小值比后面的某个值大(能进入这个if语句)，则说明假定错误

                    // 将最小值和最小值所在的索引进行重置
                   minIndex = j;
                   minValue = array[j];
                }
            }
            if(minIndex!=i){
                // 将最小值放在array[0],array[1]...array[n]  ,这一步是完成交换
                //将当前值赋值给那个最小值所在的位置，将最小值赋值给当前值上
                array[minIndex]=array[i];   // 注意这个i =0，1，2，3...n
                array[i]=minValue;    //
            }
        }
        return array;
    }
}
