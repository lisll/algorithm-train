package com.datapipeline.marginCalculation;

import java.util.*;

/**比较两个集合的差值
 * 例如集合一和集合二，有一部分数据是重合的，有一部分是各自独有的
 * @author ly
 * @Date Create in 11:14 2021/3/26 0026
 * @Description
 */
public class DiffCal {


    // 提取两个集合中共有的值
    public static Set<String> commonValue(Set<String> oneSet, Set<String> twoSet){
            if(oneSet !=null && !oneSet.isEmpty() && twoSet!=null && !twoSet.isEmpty()){
                Map<String, String> dataMap = new HashMap<String, String>();
                for(String id:oneSet){
                    dataMap.put(id,id);
                }
                Set<String> hashSet = new HashSet<>();
                for(String key :  twoSet){
                    if(dataMap.containsKey(key)){
                        hashSet.add(key);
                    }
                }
                if(hashSet.size()==0){
                    System.out.println("两个集合中没有公共的元素");
                    return  null;
                }
                return  hashSet;

            }else{
                System.out.println("输入的list集合为空");
                return  null;
            }
    }

    // 获取两个集合中的特有的值，这个方法的其中一个参数来源于上个方法的返回值
    public static Set<String> diffValue(Set<String> oneSet, Set<String> twoSet){

        if(oneSet !=null && !oneSet.isEmpty() && twoSet!=null && !twoSet.isEmpty()){
            Map<String, String> dataMap = new HashMap<String, String>();
            int oneSize = oneSet.size();
            int twoSize = twoSet.size();
            Set<String> hashSet = new HashSet<>();
            if(oneSize>twoSize){
                for(String id:twoSet){
                    dataMap.put(id,id);
                }
                for(String key : oneSet){
                    if(!dataMap.containsKey(key)){
                        hashSet.add(key);
                    }
                }
            }else if(oneSize<twoSize){
                for(String id:oneSet){
                    dataMap.put(id,id);
                }
                for(String key :  twoSet){
                    if(!dataMap.containsKey(key)){
                        hashSet.add(key);
                    }
                }
            }else{
            System.out.println("其中有一个集合和共有集合内容一致");
                return  null;
            }
            if(hashSet.size()==0){
                System.out.println("两个集合中没有公共的元素");
                return  null;
            }
            return  hashSet;
        }else{
            System.out.println("输入的list集合为空");
            return null;
        }
    }

}
