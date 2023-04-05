package com.datapipeline.sort;

import java.util.Arrays;

/**
 *这是冒泡排序算法
 * @Description
 */
public class Bubble_Sort {
    public static void main(String[] args) {
        int[] arr = new int[]{1,3,4,9,0,5,6,4,8,-8,19};
        Arrays.stream(bubbleSourt(arr)).forEach(value -> System.out.println(value));
    }

    public static int[] bubbleSourt(int[] arrSort){
        int[] returnArr = null;
        if(arrSort==null || arrSort.length<=0){
            return returnArr;
        }
        // 外层循环第一次遍历
        for(int i =0;i<arrSort.length;i++){
            for(int j=0;j<arrSort.length-i-1;j++){
                if(arrSort[j]>arrSort[j+1]){
                    swap(arrSort,j,j+1);
                    returnArr = arrSort;
                }
            }
        }
        return returnArr;
    }

    //交换数组中两个位置上的元素
    public static void swap(int[] arr,int i1,int i2){
           int temp = arr[i2];
            arr[i2]=arr[i1];
            arr[i1]=temp;
    }
}
