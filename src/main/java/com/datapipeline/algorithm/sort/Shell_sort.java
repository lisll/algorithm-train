package com.datapipeline.algorithm.sort;

import java.util.Arrays;

/**
 * Company: www.hecom.cn
 *
 * @description: 希尔排序
 * @author: ly
 */

public class Shell_sort {
    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1, -1, 89,4,999,35,2};
        int[] ints = shellSort(arr);
        int[] arr1 = { 8, 9, 1, 7, 2, 3, 5, 4, 6, 0 };
//        demo(arr1);
        System.out.println(Arrays.toString(ints));
    }

    public static void demo(int[] arr){
        // 希尔排序的第 1 轮排序
        // 因为第 1 轮排序，是将 10 个数据分成了 5
        for(int i = 5;i<arr.length;i++){
            for(int j = i-5;j>=0;j-=5){
                // 如果当前元素大于加上步长后的那个元素，说明交换
                if(arr[j]>arr[j+5]){
                    int temp = arr[j];
                    arr[j]= arr[j+5];
                    arr[j+5]=temp;
                }
            }
        }
        System.out.println("希尔排序 1 轮后=" + Arrays.toString(arr));
    }

    public static int[] shellSort(int[] arr) {

        // 增量 gap, 并逐步的缩小增量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {  // 定义增量序列，这个是固定写法
        // 从第 gap 个元素，逐个对其所在的组进行直接插入排序
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j - gap]) {
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                      //移动
                        arr[j] = arr[j-gap];
                        j -= gap;
                    }
                    //当退出 while 后，就给 temp 找到插入的位置
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }
}
