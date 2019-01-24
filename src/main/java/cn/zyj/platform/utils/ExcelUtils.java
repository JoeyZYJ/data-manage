package cn.zyj.platform.utils;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-26
 */
public class ExcelUtils {

  /*
   * 创建Excelworkbook
   */
  public static void getXSSFWorkbook(int sheetNum,String excelTitle,String sheetTitle, String[] columnName, List<Object[]> dataList,
    XSSFWorkbook workbook) {
    if (workbook == null) {
      workbook = new XSSFWorkbook();
    }
    try {
      // 创建工作表
      XSSFSheet sheet = workbook.createSheet(excelTitle);
      workbook.setSheetName(sheetNum,sheetTitle);
      // 设置表格第一列宽度
      //sheet.setColumnWidth(1,480);
      // 产生表格标题行
      XSSFRow rowm = sheet.createRow(0);
      // 设置表格标题行高度
      rowm.setHeight((short) 120);
      XSSFCell cellTiltle = rowm.createCell(0);
      //获取列头样式对象
      XSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);
      //单元格样式对象
      XSSFCellStyle style = getStyle(workbook);

      sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (columnName.length - 1)));
      cellTiltle.setCellStyle(columnTopStyle);
      cellTiltle.setCellValue(excelTitle);

      // 定义所需列数
      int columnNum = columnName.length;
      // 在索引2的位置创建行(最顶端的行开始的第二行)
      XSSFRow rowRowName = sheet.createRow(2);
      rowRowName.setHeight((short) 380);
      // 将列头设置到sheet的单元格中
      for (int n = 0; n < columnNum; n++) {
        //创建列头对应个数的单元格
        XSSFCell cellRowName = rowRowName.createCell(n);
        //设置列头单元格的数据类型
        cellRowName.setCellType(CellType.STRING);
        XSSFRichTextString text = new XSSFRichTextString(columnName[n].trim());
        //设置列头单元格的值
        cellRowName.setCellValue(text);
        //设置列头单元格样式
        cellRowName.setCellStyle(columnTopStyle);
      }

      //将查询出的数据设置到sheet对应的单元格中
      for (int i = 0; i < dataList.size(); i++) {
        //遍历每个对象
        Object[] obj = dataList.get(i);
        //创建所需的行数
        XSSFRow row = sheet.createRow(i + 3);

        for (int j = 0; j < obj.length; j++) {
          //设置单元格的数据类型
          XSSFCell cell;
          if (j == 0) {
            cell = row.createCell(j, CellType.NUMERIC);
            cell.setCellValue(obj[j].toString().trim());
          } else {
            cell = row.createCell(j, CellType.NUMERIC);
            if (!"".equals(obj[j]) && obj[j] != null) {
              //设置单元格的值
              cell.setCellValue(obj[j].toString().trim());
            }
          }
          //设置单元格样式
          cell.setCellStyle(style);
        }
      }
      //让列宽随着导出的列长自动适应
      for (int colNum = 0; colNum < columnNum; colNum++) {
        int columnWidth = sheet.getColumnWidth(colNum) / 256;
        for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
          XSSFRow currentRow;
          //当前行未被使用过
          if (sheet.getRow(rowNum) == null) {
            currentRow = sheet.createRow(rowNum);
          } else {
            currentRow = sheet.getRow(rowNum);
          }
          if (currentRow.getCell(colNum) != null) {
            XSSFCell currentCell = currentRow.getCell(colNum);
            if (currentCell.getCellTypeEnum().equals(CellType.STRING)) {
              int length = currentCell.getStringCellValue().getBytes().length;
              if (columnWidth < length) {
                columnWidth = length;
              }
            }
          }
        }
        if (colNum == 0) {
          // 序号列宽度
          sheet.setColumnWidth(colNum, 8 * 256);
        } else {
          sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*
   * 列头单元格样式
   */
  private static XSSFCellStyle getColumnTopStyle(XSSFWorkbook workbook) {

    // 设置字体
    XSSFFont font = workbook.createFont();
    //设置字体大小
    font.setFontHeightInPoints((short) 11);
    //字体加粗
    font.setBold(true);
    //设置字体名字
    font.setFontName("Courier New");
    //设置样式;
    XSSFCellStyle style = workbook.createCellStyle();
    //设置底边框;
    style.setBorderBottom(BorderStyle.THIN);
    // 黑色
    XSSFColor black = new XSSFColor(Color.BLACK);
    //设置底边框颜色;
    style.setBottomBorderColor(black);
    //设置左边框;
    style.setBorderLeft(BorderStyle.THIN);
    //设置左边框颜色;
    style.setLeftBorderColor(black);
    //设置右边框;
    style.setBorderRight(BorderStyle.THIN);
    //设置右边框颜色;
    style.setRightBorderColor(black);
    //设置顶边框;
    style.setBorderTop(BorderStyle.THIN);
    //设置顶边框颜色;
    style.setTopBorderColor(black);
    //在样式用应用设置的字体;
    style.setFont(font);
    //设置自动换行;
    style.setWrapText(false);
    //设置水平对齐的样式为居中对齐;
    style.setAlignment(HorizontalAlignment.CENTER);
    //设置垂直对齐的样式为居中对齐;
    style.setVerticalAlignment(VerticalAlignment.CENTER);

    return style;

  }

  /*
   * 列数据信息单元格样式
   */
  private static XSSFCellStyle getStyle(XSSFWorkbook workbook) {
    // 设置字体
    XSSFFont font = workbook.createFont();
    //设置字体大小
    font.setFontHeightInPoints((short) 10);
    //设置字体名字
    font.setFontName("Courier New");
    //设置样式;
    XSSFCellStyle style = workbook.createCellStyle();
    //设置底边框;
    style.setBorderBottom(BorderStyle.THIN);
    // 黑色
    XSSFColor black = new XSSFColor(Color.BLACK);
    //设置底边框颜色;
    style.setBottomBorderColor(black);
    //设置左边框;
    style.setBorderLeft(BorderStyle.THIN);
    //设置左边框颜色;
    style.setLeftBorderColor(black);
    //设置右边框;
    style.setBorderRight(BorderStyle.THIN);
    //设置右边框颜色;
    style.setRightBorderColor(black);
    //设置顶边框;
    style.setBorderTop(BorderStyle.THIN);
    //设置顶边框颜色;
    style.setTopBorderColor(black);
    //在样式用应用设置的字体;
    style.setFont(font);
    //设置自动换行;
    style.setWrapText(false);
    //设置水平对齐的样式为居中对齐;
    style.setAlignment(HorizontalAlignment.CENTER);
    //设置垂直对齐的样式为居中对齐;
    style.setVerticalAlignment(VerticalAlignment.CENTER);

    return style;

  }

  public static void doExport(HttpServletResponse response,String userAgent,String title,XSSFWorkbook workbook){
    try {
      String finalFileName;
      if (StringUtils.contains(userAgent, "Mozilla")) {
        //google,火狐浏览器
        finalFileName = new String(title.getBytes(), "ISO8859-1");
      } else {
        //其他浏览器
        finalFileName = URLEncoder.encode(title, "UTF8");
      }
      String fileName = finalFileName + ".xlsx";
      response.setContentType("application/octet-stream;charset=utf-8");
      response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
      response.addHeader("Pargam", "no-cache");
      response.addHeader("Cache-Control", "no-cache");
      OutputStream output = response.getOutputStream();
      workbook.write(output);
      output.flush();
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
