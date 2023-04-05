package com.datapipeline.tools.moreJobs;



import com.datapipeline.algorithm.marginCalculation.DiffCal;
import com.datapipeline.tools.FileUtil;
import java.util.Set;

/**
 * @author ly
 * @Date Create in 13:43 2021/4/1 0001
 * @Description
 */
public class Job1 {
    public static void main(String[] args) {
        String s1 = "C:\\Users\\Administrator\\Desktop\\2.4.0升级\\逐步升级\\lte升级\\需要区别的表.txt";
        String s2 = "C:\\Users\\Administrator\\Desktop\\2.4.0升级\\逐步升级\\lte升级\\需要区别的表2.txt";
        Set<String> set1 = FileUtil.readFileForList(s1);
        Set<String> set2 = FileUtil.readFileForList(s2);
        Set<String> common = DiffCal.commonValue(set1, set2);
        for(String s : common){
//            System.out.println(s);
        }
        Set<String> diff1 = DiffCal.diffValue(common, set2);
        System.out.println("set1集合独有的： ");
        for(String d1:diff1){
            System.out.println(d1);
        }
    }
}
