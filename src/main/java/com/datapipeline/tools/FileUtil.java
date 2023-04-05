package com.datapipeline.tools;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ly
 * @Date Create in 11:23 2021/3/29 0029
 * @Description
 */
public class FileUtil {


    public static Properties getProperties(String path) {
        Properties configProperties = null;
        InputStream inputStream = null;
        try {
            configProperties = new Properties();
            inputStream  = new BufferedInputStream(new FileInputStream(path));
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));//解决配置文件中的中文乱码问题
            configProperties.load(bf);
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return configProperties;
    }

    /**
     * @Description:  判断一个字符串中是否存在汉字
     * @param @param countname
     * @param @return
     * @return boolean
     * @throws
     */
    public static boolean existChinese(String countname){
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(countname);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * @Description:  创建一个空白的目录（即使要创建的这个目录的父目录不存在也可以把父目录一块创建）
     * @param @param destDirName  需要创建的目录的名字
     * @param @return
     * @return boolean
     * @throws
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录,可以创建必需但不存在的父目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }

    /**
     * @Description:  创建一个新的空白的文件
     * @param @param destFileName
     * @param @return
     * @return boolean
     * @throws
     */
    public static boolean createFile(String destFileName) {

        File file = new File(destFileName);
        if(file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if(!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }



    public static void test(String s){
        File file = new File(s);
        File[] listFiles = file.listFiles();
        for(int i =0;i<listFiles.length;i++){
//	    		System.out.println(listFiles[i]);
//	    		String ss = listFiles[i].toString().split(File.separator)[9];
            String ss = listFiles[i].toString().substring(48);
            System.out.println(ss);
        }

    }


    public static void getModifiedTime(String fileName){
        File dir = new File(fileName);
        File[] listFiles = dir.listFiles();
        for(File file:listFiles){
//	    		File f = new File(file2);
            boolean directory = file.isDirectory();
            if(directory){
                File[] listFiles2 = file.listFiles();
                for(int i = 0;i<listFiles2.length;i++){
                    String string = listFiles2[i].toString();
                    Calendar cal = Calendar.getInstance();
                    long time = listFiles2[i].lastModified();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cal.setTimeInMillis(time);
                    String format = formatter.format(cal.getTime());
                    String string2 = format.split(" ")[0].split("-")[2];
//						System.out.println(Integer.parseInt(string2));
                    if(Integer.parseInt(string2)==26){
                        System.out.println(listFiles2[i].getParent().substring(48));
                    }
//						System.out.println("修改时间 " + formatter.format(cal.getTime()));
                }
            }
//		        Calendar cal = Calendar.getInstance();
//		        long time = file.lastModified();
//		        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		        cal.setTimeInMillis(time);
//		        System.out.println("修改时间 " + formatter.format(cal.getTime()));
        }

    }


    /**
     * @Description:  将目标目录复制为指定目录(也可以用于复制文件)
     * @param @param src  源路径
     * @param @param dest  目标路径
     * @param @throws IOException
     * @return void
     * @throws
     */
    public static void copyDir(File src, File dest)  {
        try {
            if(!src.exists()) {        // 检查源路径是否存在
                System.out.println("源目录不存在!");
            } else if(src.isFile()) {    // 如果源路径是一个文件
                if(dest.isDirectory()) {
                    // 不能将文件复制为一个目录
                    System.out.println("目标路径不是一个文件!");
                }else {
                    // 复制文件
                    FileInputStream fis = new FileInputStream(src);
                    FileOutputStream fos = new FileOutputStream(dest);
                    byte[] arr = new byte[1024 * 8];
                    int len;
                    while((len = fis.read(arr)) != -1) {
                        fos.write(arr, 0, len);
                    }
                    fis.close();
                    fos.close();
                }
            } else {    // 如果源路径是一个目录
                if(dest.isFile()) {
                    // 不能将目录复制为一个文件
                    System.out.println("目标路径不是一个目录!");
                    System.exit(0);
                } else {
                    // 先检查目标是否存在, 不存在则创建
                    if(!dest.exists()) {
                        dest.mkdirs();
                    }
                    // 如果目标路径是一个目录, 递归调用本方法进行复制
                    // 获取源目录的子文件/目录
                    String[] subFiles = src.list();
                    // 遍历源目录进行复制
                    for (String subFile : subFiles) {
                        copyDir(new File(src, subFile), new File(dest, subFile));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }



    public static Set<String> readFileForList(String path){
        FileReader fileReader = null;
        Set<String> hashSet = new HashSet<>();
        try {
            fileReader = new FileReader(path);
            BufferedReader bf = new BufferedReader(fileReader);
            String line ;
            while((line = bf.readLine())!=null){
                hashSet.add(line.toLowerCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  hashSet;
    }



    // 测试
    public static void main(String[] args) throws IOException {
        //String ss = "C:\\Users\\SLL\\Desktop\\2.4.0升级\\宽表\\任务\\待现网测试\\新建的表\\DW_WY_CUSTOMER_4CTL_60\\DW_WY_CUSTOMER_4CTL_60_v1_task.sql";
//	    	String s = "C:\\Users\\SLL\\Desktop\\2.4.0升级\\宽表\\任务\\待现网测试\\需要修改的表";
//	    	test(s);
//			System.out.println(createFile(ss));
//	    	getModifiedTime("C:\\Users\\SLL\\Desktop\\卡顿时长占比指标清洗问题核查\\需要修改的表\\替换现网\\");

//        copyDir(new File("C:\\Users\\Administrator\\Desktop\\2.4.0升级\\亿阳\\SQL备份\\DW_CSFB_YIY_TAC8_1440"), new File("C:\\Users\\Administrator\\Desktop\\2.4.0升级\\亿阳\\test\\DW_CSFB_YIY_TAC8_1440"));
    }
}
