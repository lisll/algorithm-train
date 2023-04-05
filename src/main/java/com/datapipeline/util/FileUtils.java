package com.datapipeline.util;

import java.io.*;

/**
 * @author ly
 * @Date Create in 12:27 2021/3/23 0023
 * @Description
 */
public class FileUtils {

    public static void main(String[] args) {
        readFile();
    }
    public static void readFile()  {
        try {
            FileInputStream fi = new FileInputStream("C:\\Users\\Administrator\\Desktop\\AAA生产需求\\dwd_ntw_account_oltonubras_dm.txt");
            InputStreamReader is = new InputStreamReader(fi);
            BufferedReader bufferedReader = new BufferedReader(is);
            String strTmp = "";
            StringBuilder stringBuilder = new StringBuilder();
            while((strTmp=bufferedReader.readLine())!=null){
                String substring = strTmp.substring(strTmp.indexOf("`")+1, strTmp.lastIndexOf("`"));
                stringBuilder.append(substring).append("\r\n");
            }
            bufferedReader.close();
            fileWrite(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void fileWrite(String content){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\AAA生产需求\\test.txt")), "UTF-8"));
            bw.write(content);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
