package com.demo.util;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class ExcelStyleUtil {
	/**
	 * 标题样式
	 */
	public static final int TITLE = 1;
	/**
	 * 内容样式带颜色
	 */
	public static final int CONTEXTWITHCOLOR = 2;
	/**
	 * 内容样式不带颜色
	 */
	public static final int CONTEXT = 3;
	/**
	 * 百分比样式
	 */
	public static final int PRECENT = 4;
	// 常用字号
	public static final short FONT12 = 12;
	public static final short FONT24 = 24;
	// 常用字体
	public static final String 微软雅黑 = "微软雅黑";
	// 常用颜色
	public static final short BLUE = IndexedColors.BLUE_GREY.index;
	public static final short WHITE = IndexedColors.WHITE.index;
	public static final short GREY25 = IndexedColors.GREY_25_PERCENT.index;

	public static CellStyle getStyle(Workbook workbook, int styleType) {
		// 创建单元格样式对象
		CellStyle style = workbook.createCellStyle();
		// 创建字体样式对象
		Font font = workbook.createFont();
		switch (styleType) {
			case TITLE:
				// 设置字体样式
				font.setFontName(微软雅黑);
				// 设置字体大小
				font.setFontHeightInPoints(FONT12);
				// 设置字体颜色
				font.setColor(WHITE);
				// 粗体显示
				font.setBold(true);
				style.setFont(font);
				// 设置居中
				style.setAlignment(HorizontalAlignment.CENTER);
				style.setVerticalAlignment(VerticalAlignment.CENTER);
				// 设置填充样式
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				// 设置前景色
				style.setFillForegroundColor(BLUE);
				break;
			case CONTEXTWITHCOLOR:
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				style.setFillForegroundColor(GREY25);
				//$FALL-THROUGH$ 添加颜色并添加下面样式
			case CONTEXT:
				// 设置填充样式
				font.setFontName("楷体");
				font.setFontHeightInPoints(FONT12);
				style.setFont(font);
				style.setAlignment(HorizontalAlignment.CENTER);
				style.setVerticalAlignment(VerticalAlignment.CENTER);
				break;
			case PRECENT:
				font.setFontName("楷体");
				font.setFontHeightInPoints(FONT12);
				style.setFont(font);
				style.setAlignment(HorizontalAlignment.CENTER);
				style.setVerticalAlignment(VerticalAlignment.CENTER);
				style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
				break;
			default:
				break;
		}
		return style;
	}
	
	public static void changeGREY25Color(HSSFWorkbook workbook) {
		HSSFPalette palette = workbook.getCustomPalette();
		byte red = (byte) 248, green = (byte) 248, blue = (byte) 248;
		palette.setColorAtIndex(GREY25, red, green, blue);
	}
}
