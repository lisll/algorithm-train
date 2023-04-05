package com.datapipeline.algorithm.sort;

import java.util.Arrays;

/**
 * Company: www.hecom.cn
 *
 * @description: 插入排序
 * @author: ly
 */

public class Insert_sort {
    public static void main(String[] args) {
       int[] arr = {101, 34, 119, 1, -1, 89,4,999,35};
//       int[] ints = insertSort(arr);
        int[] ints = insertionSort(arr);
        System.out.println(Arrays.toString(ints));
    }

    public static int[] insertSort(int[] arr) {

        for(int i = 1; i < arr.length; i++) {  //注意 i是从1开始的，即默认索引为0时，已经排好序了

           int insertVal = arr[i]; //定义待插入的数
           int insertIndex = i - 1; // 定义待插入的位置下标，即给 insertVal 找到插入的位置
            // 说明
            // 1. insertIndex >= 0 保证在给 insertVal 找插入位置，不越界
            // 2. insertVal < arr[insertIndex] 待插入的数，还没有找到插入位置
            // 3. 就需要将 arr[insertIndex] 后移
            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];// arr[insertIndex]
                insertIndex--;
                 }// 当退出 while 循环时，说明插入的位置找到

            // 如果循环到 i时，没有走while循环，就证明 i 这个位置的值不用动，此时 insertIndex + 1必然等于i,所以此时不用调位置
            // 这个if语句只是一个优化作用，没有这个if语句，结果是一样的；
             if(insertIndex + 1 != i) {
                 arr[insertIndex + 1] = insertVal;
             }
        }
        return arr;
    }



    public static int[] insertionSort(int[] array) {
        if (array.length == 0)
            return array;
        for (int i = 1; i < array.length; i++) {
            int current = array[i];  //待插入的数
            int preIndex = i-1;    //待插入的位置
            while (preIndex >= 0 && current < array[preIndex]) { // 如果后一个值小于前面一个值
                array[preIndex + 1] = array[preIndex]; // 将当前索引上的值(较大的值)赋值给后面索引上
                preIndex--;
            }
            //交换
            array[preIndex+1] = current;
        }
        return array;
    }
}
