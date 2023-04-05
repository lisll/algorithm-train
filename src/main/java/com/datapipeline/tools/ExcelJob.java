package com.datapipeline.tools;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author ly
 * @Date Create in 12:30 2021/3/29 0029
 * @Description
 */
public class ExcelJob {

    // 这个静态代码块，必须要放在最上面，否则它不是最先执行（不知道为什么）
    static{
        if (System.getProperty("os.name").contains("Windows")) {
            properties = FileUtil.getProperties(System.getProperty("user.dir") + "\\resources\\ExcelFile.properties");
        } else {
            properties = FileUtil.getProperties("/dinglicom/mr/conf/config.properties");
        }
    }

    public static Properties properties;
    // excel 表格（全路径名称）
    public static String excelFileName = properties.getProperty("excelFileName");
    // sheet页的名字
    public static  String sheetName = properties.getProperty("sheetName");
    // 写入目的地 (txt格式)
    public static String writeFilePath = properties.getProperty("writeFilePath");
    //写入目的地 (xlsx格式)
    public static String writeFileXlsxPath = properties.getProperty("writeFileXlsxPath");

    // 用于建测试表时候的表名
    public static String table_name = writeFilePath.split("\\\\")[writeFilePath.split("\\\\").length-1].split("\\.")[0];



    public static String table_name_insert =  properties.getProperty("table_name");

    //建表时的维度字段
    public static String dimension_file = properties.getProperty("dimension_file");


    public static String readFile = properties.getProperty("readFile");



    public static int yingwenziduan = Integer.parseInt(properties.getProperty("yingwenziduan"));
    public static int tiaojianlie = Integer.parseInt(properties.getProperty("tiaojianlie"));
    public static int jieguolie = Integer.parseInt(properties.getProperty("jieguolie"));
    public static int shujuyuanlie = Integer.parseInt(properties.getProperty("shujuyuanlie"));



    public static LinkedHashMap<Integer, LinkedHashMap<Integer, Object>> operExcelMap(){
        return OperExcel.judegeVersion(excelFileName, sheetName);
    }


    /**
     * @Description:  将列索引替换成每一列的标题（即用每列的标题来指明这是哪一列的数据）
     * @param @return
     * @return LinkedHashMap<Integer,LinkedHashMap<Integer,Object>>
     * @throws
     */
    public static LinkedHashMap<Integer, LinkedHashMap<Integer, Object>>  dealHeadLine(){
        LinkedHashMap<Integer, LinkedHashMap<Integer, Object>> judegeVersion = OperExcel.judegeVersion(excelFileName, sheetName);
        Set<Map.Entry<Integer, LinkedHashMap<Integer, Object>>> entrySet = judegeVersion.entrySet();

        return null;
    }


    /**
     * @Description:  写入一个普通的文件（一般指TXT结尾的文件）
     * @param @param value  需要写入文件的数据
     * @return void
     * @throws
     */
    public static void  WriteTextStirng( String value){
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(writeFilePath, true);  // true ： 追加写   false 覆盖写
            bw = new BufferedWriter(fw);
            bw.write(value);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void appendFile(File file, String content)  {
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(
                    new FileOutputStream(file, true), // true to append
                    "UTF-8"
            );
            out.write(content);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @Description:  采用utf-8格式换行写入一个文本文件
     * @param @param value
     * @return void
     * @throws
     */
    public static void writeTextUTF8(String value)  {
        FileOutputStream fos = null;
        OutputStreamWriter osw =null;
        BufferedWriter bw = null;
        try {
            //写入中文字符时解决中文乱码问题
            fos=new FileOutputStream(new File(writeFilePath),true);
            osw=new OutputStreamWriter(fos, "UTF-8");
            bw=new BufferedWriter(osw);
            bw.write(value);
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
                bw.close();
                osw.close();
                fos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    /**
     * @Description:  设置单元格的样式
     * @param @param wb
     * @param @param sheet
     * @return void
     * @throws
     */
    public static void changeStyle(XSSFWorkbook wb, XSSFSheet sheet){

        //设置单元格的样式  （不过目前貌似没有起作用）
        XSSFCellStyle curStyle = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setFontName("微软雅黑");		//设置字体
        font.setFontHeightInPoints((short)10);			//设置英文字体
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);		//加粗
        curStyle.setFont(font);
        curStyle.setBorderTop(XSSFCellStyle.BORDER_THICK);		//粗实线
        curStyle.setWrapText(true);		//换行  
        curStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);		//横向具右对齐
        curStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中


        // 设置列宽，（param1:列索引   param2：列值）
        sheet.setColumnWidth(0, 256*10);
        sheet.setColumnWidth(1, 256*15);
        sheet.setColumnWidth(2, 256*15);
        sheet.setColumnWidth(3, 256*25);
        sheet.setColumnWidth(4, 256*15);
        sheet.setColumnWidth(5, 256*15);
        sheet.setColumnWidth(6, 256*25);
        sheet.setColumnWidth(7, 256*20);

    }

    /**
     * @Description:  将处理后的数据输出为xlsx格式的文件；
     * @param @param map
     * @return void
     * @throws
     */
    public static void writeExcel( LinkedHashMap<Integer, LinkedHashMap<Integer, Object>> map){

        FileOutputStream out = null;
        try {
            //获得Excel文件输出流
            out = new FileOutputStream(new File(writeFileXlsxPath));
            //创建excel工作簿对象
            XSSFWorkbook wb = new XSSFWorkbook();

            //创建excel页（如果没有命名，默认为Sheet0，Sheet1...
            XSSFSheet sheet = wb.createSheet("poi操作");

            // 设置Excel表格样式
            changeStyle(wb,sheet);

            //创建表头
//			XSSFRow row1 = sheet.createRow(0);
//			//创建表头的单元格-------------------------------
//			XSSFCell cell1_1 = row1.createCell(0);
//			cell1_1.setCellValue("学号");
//			XSSFCell cell1_2 = row1.createCell(1);
//			cell1_2.setCellValue("姓名");
//			XSSFCell cell1_3 = row1.createCell(2);
//			cell1_3.setCellValue("年级");
//			XSSFCell cell1_4 = row1.createCell(3);
//			cell1_4.setCellValue("年龄");
//			XSSFCell cell1_5 = row1.createCell(4);
//			cell1_5.setCellValue("性别");
            //写入一行内容：
            // 填充数据
            Iterator<Map.Entry<Integer, LinkedHashMap<Integer, Object>>> iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<Integer, LinkedHashMap<Integer, Object>> next = iterator.next();
                XSSFRow row2 = sheet.createRow(next.getKey());
//				row2.setHeight((short)500);   // 设置行高（这个设置可有可无）
                LinkedHashMap<Integer, Object> value = next.getValue();
                Iterator<Map.Entry<Integer, Object>> iterator_value = value.entrySet().iterator();
                while(iterator_value.hasNext()){
                    Map.Entry<Integer, Object> next2 = iterator_value.next();
                    XSSFCell cell = row2.createCell(next2.getKey());
                    cell.setCellValue(next2.getValue()+"");
                }
            }
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if(out!=null)
                        out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @Description:  字符串去除两边空格(这个只是针对一个字符串两端去除空格，但是如果这个字符串本身（即字符串中间）有空格是无法去除的；
     * @param @param string
     * @param @return
     * @return String
     * @throws
     */
    public static String removeEmptyCharLeftAndRight(String string){
        string = removeEmptyCharLeft(string);
        string = removeEmptyCharright(string);
        return string;
    }
    /**
     * @Description:  字符串去除左边空格
     * @param @param string
     * @param @return
     * @return String
     * @throws
     */
    private static String removeEmptyCharLeft(String string) {
        if(StringUtils.isBlank(string)){
            return "";
        }
        char[] cs = string.toCharArray();
        boolean empty = false;
        for(char c:cs){
            empty = Pattern.matches("[　|\\s]", String.valueOf(c));
            if(empty){
                string = string.substring(1);
            }else{
                break;
            }
        }
        return string;
    }
    /**
     * @Description:  字符串去右边空格
     * @param @param string
     * @param @return
     * @return String
     * @throws
     */
    public static String removeEmptyCharright(String string) {
        if(StringUtils.isBlank(string)){
            return "";
        }
        char[] cs = string.toCharArray();
        boolean empty = false;
        for(int i=cs.length;i>0;i--){
            empty = Pattern.matches("[　|\\s]", String.valueOf(cs[i-1]));
            if(empty){
                string = string.substring(0,string.length()-1);
            }else{
                break;
            }
        }
        return string;
    }


    // 读取配置文件
    public static String readFile(String file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(file)));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                s=s.replaceAll("[\\s\\u00A0]+","").trim();     // 去除字符串中的空格
                result.append(s+",");
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
}
