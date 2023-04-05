package com.datapipeline.tools;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**读取Excel表格的核心类
 *将Excel表格中的内容映射到Map集合中
 * @Description
 */
public class OperExcel {
    // 这个静态代码块，必须要放在最上面，否则它不是最先执行（不知道为什么）
    static{
        if (System.getProperty("os.name").contains("Windows")) {
            properties = FileUtil.getProperties(System.getProperty("user.dir") + "\\resources\\File.properties");
        } else {
            properties = FileUtil.getProperties("/dinglicom/mr/conf/config.properties");
        }
    }

    public static Properties properties;
    public static String isReadFirstLine = properties.getProperty("isReadFirstLine");

    // 使用 linkedHashMap 主要作用是为了保证添加顺序和取出顺序一致
    static LinkedHashMap<Integer, LinkedHashMap<Integer, Object>> excelMap = new LinkedHashMap<>();


    public static LinkedHashMap<Integer, LinkedHashMap<Integer, Object>> judegeVersion(String excelFileName,String sheetName ) {
        try {
            File file = new File(excelFileName);
            InputStream is = new FileInputStream(file);
            if (file.getName().endsWith("xlsx")) {
                return  readXlsx(excelFileName,sheetName);
            } else if (file.getName().endsWith("xls")) {
                return readXls(excelFileName,sheetName);
            }else {
                System.out.println("输入的文件不是Excel文件！！！");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    /**
     * @Description:   读取以xlsx结尾的Excel表格；（XSSF:操作07版本之后 后缀名xx.xlsx）  目前主要用的就是这个
     * @param @param excelFileName Excel文件名称
     * @param @param sheetName	 Excel中某一个sheet页的名称
     * @param @return
     * @return LinkedHashMap<Integer,String>
     * @throws
     */
    public static LinkedHashMap<Integer, LinkedHashMap<Integer, Object>> readXlsx(String excelFileName,String sheetName){
        try {
            //得到Excel工作簿对象（Excel文档对象）
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(excelFileName)));
            XSSFSheet sheet = null;  // Excel表单
            // 对所有的sheet页进行遍历，找到所需要的那个sheet页
            for(int i = 0 ; i <  workbook.getNumberOfSheets(); i++) {
                if(sheetName.toLowerCase().equals(workbook.getSheetAt(i).getSheetName().toLowerCase().trim())) {
                    sheet = workbook.getSheetAt(i);
                }
            }
            // 防止配置文件里面的sheetName写错
            if(sheet == null){
                System.out.println("程序已经关闭，请检查配置文件中的 sheetName属性是否正确");
                System.exit(0);
            }
            // 遍历指定sheet页中的每一行
            Boolean flag = Boolean.valueOf(isReadFirstLine);   // 是否读取第一行
            int san = flag ? 0 : 1;
            //获取总行号（从0开始）
            for(int i = san; i<= sheet.getLastRowNum();i++ ){

                XSSFRow row = sheet.getRow(i);
                if(!isEmptyRow(row)){
                    // 用于存储列索引，和每一列的数据的map集合
                    LinkedHashMap<Integer, Object> cellMap = new LinkedHashMap<>();
                    // 获取每一行中的每一列；  sheet.getRow(0) ： 指定的第一行，因为每一行有多少列，取决于第一行有多少列，因此这个sheet页最好有标题
                    //sheet.getRow(0).getPhysicalNumberOfCells() 指定列号（一共有多少列）
                    for(int k = 0; k < sheet.getRow(0).getPhysicalNumberOfCells(); k++){
                        if(row.getCell(k)!=null){
                            row.getCell(k).setCellType(XSSFCell.CELL_TYPE_STRING);
//							String cell =  row.getCell(k).getStringCellValue();
                            String cell = ExcelJob.removeEmptyCharLeftAndRight(row.getCell(k).getStringCellValue());
                            // 只能删除半角空格
//							String cell = row.getCell(k).getStringCellValue().trim();  // 获取每一行中的每个单元格
//							// 删除 Excel表格中的空格
//							cell  = cell.replaceAll("[\\s\\u00A0]+","").trim();
                            // 判断是不是全角空格
//							while (cell.startsWith("　")) {//这里判断是不是全角空格
//								cell = cell.substring(1, cell.length()).trim();
//								}
//								while (cell.endsWith("　")) {
//									cell = cell.substring(0, cell.length() - 1).trim();
//								}
                            cellMap.put(k, cell);
                        }else{
                            XSSFCell cell = row.getCell(k);
                            cellMap.put(k, cell);
                        }
                    }
                    excelMap.put(i,cellMap);
                    //一旦某一行是空白行，那么就终止对行号进行循环；
                }else{
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excelMap;
    }

    /**
     * @Description:  读取 以 xls 结尾的Excel表格；（HSSF:操作07版本之前 后缀名xxx.xls）
     * @param @param excelFileName  Excel文件名称
     * @param @param sheetName  Excel中某一个sheet页的名称
     * @param @return
     * @return LinkedHashMap<Integer,String>
     * @throws
     */
    public static LinkedHashMap<Integer, LinkedHashMap<Integer, Object>> readXls(String excelFileName,String sheetName){
        try {

            //得到Excel工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(excelFileName)));
            //得到Excel工作表对象,并对所有的sheet页进行遍历
            HSSFSheet sheet = null;
            for(int i = 0 ; i <  workbook.getNumberOfSheets(); i++) {
                if(sheetName.equals(workbook.getSheetAt(i).getSheetName())) {
                    sheet = workbook.getSheetAt(i);
                }
            }
            //遍历标签页中行
            for (int i = 0 ;i<sheet.getLastRowNum();i++) {   //获取最大行数
                HSSFRow row = sheet.getRow(i);
                if(row != null){
                    // 用于存储列索引，和每一列的数据的map集合
                    LinkedHashMap<Integer, Object> cellMap = new LinkedHashMap<>();
                    // 获取每一行的每一列；  sheet.getRow(0) ： 指定的第一行，因为每一行有多少列，取决于第一行有多少列，因此这个sheet页最好有标题
                    for(int k = 0; k < sheet.getRow(0).getPhysicalNumberOfCells(); k++){
                        if(row.getCell(k)!=null){
                            row.getCell(k).setCellType(XSSFCell.CELL_TYPE_STRING);
                            String cell = row.getCell(k).getStringCellValue();  // 获取每一行中的每个单元格
                            cellMap.put(k, cell);
                        }else{
                            HSSFCell cell = row.getCell(k);
                            cellMap.put(k, cell);
                        }
                    }
                    excelMap.put(i,cellMap);
                }
            }
        } catch (IOException e) {
        }

        return excelMap;

    }


    /**
     * @Description:  判断某一行是否为空行
     * 本只要判断一行是否为有数据就可以判定是不是空行，但是poi读取单元格的时候会认为有格式也不算空
     * @param @param row
     * @param @return
     * @return boolean
     * @throws
     */
    public static boolean isEmptyRow(Row row) {

        if (row == null || row.toString().isEmpty()) {
            return true;
        } else {
            Iterator<Cell> it = row.iterator();
            boolean isEmpty = true;
            while (it.hasNext()) {
                Cell cell = it.next();
                if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                    isEmpty = false;
                    break;
                }
            }
            return isEmpty;
        }
    }

}
