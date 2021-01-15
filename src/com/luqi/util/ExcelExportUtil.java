package com.luqi.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import com.jfinal.plugin.activerecord.Record;

public class ExcelExportUtil {
	private static File projectPath = new File(ExcelExportUtil.class.getClassLoader().getResource("").getPath())
			.getParentFile();

	// Excel导出
	public static File createExcelFile(String filename, List<Record> recordList) {
		// 只能导出60000条数据

		// 定义文件地址
		File file = new File(projectPath + "/ExportFile/" + filename);
		file.getParentFile().mkdirs();// 不存在则创建父级目录
		// 若存在则更新
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		try (HSSFWorkbook workbook = new HSSFWorkbook();
		     FileOutputStream fileOutputStream = new FileOutputStream(file);) {
			// 获取已完成填写的excel
			createExcel(workbook, filename, recordList);
			// 将excel写入到文件中
			workbook.write(fileOutputStream);
		} catch (IOException e) {
			System.out.println("excel文件操作失败，文件创建路径为：" + file.getAbsolutePath());
			e.printStackTrace();
		}
		return file;
	}

	private static HSSFWorkbook createExcel(HSSFWorkbook workbook, String filename,
	                                        List<Record> recordList) {
		// 创建工作表
		HSSFSheet sheet = workbook.createSheet(filename);
		// 获得标题样式与内容样式
		CellStyle headStyle = ExcelStyleUtil.getStyle(workbook, ExcelStyleUtil.TITLE);
		//CellStyle contentWithColorStyle = ExcelStyleUtil.getStyle(workbook, ExcelStyleUtil.CONTEXTWITHCOLOR);
		CellStyle contentStyle = ExcelStyleUtil.getStyle(workbook, ExcelStyleUtil.CONTEXT);
		ExcelStyleUtil.changeGREY25Color(workbook);
		// 创建行
		HSSFRow row = sheet.createRow(0);
		// 创建列
		HSSFCell cell = null;
      //  sheet.setDefaultRowHeight((short)300);    // ---->有得时候你想设置统一单元格的高度，就用这个方法  
		int i = 0;
	//	Set<String> titles = titleAndValues.keySet();
		String[] titles={"标题","创建时间","拟完成时间","完成时间","发起人","主要责任人","是否完成"};
		for (String title : titles) {
			// 每有一个数据创建一列
			cell = row.createCell(i);
			// 将样式添加到表头中
			cell.setCellStyle(headStyle);
			// 设置列宽
			sheet.setColumnWidth(i, 7000);
			// 将字段名存入表头
			cell.setCellValue(title);
			i++;
		}
		// 第一列与最后四列调宽
		//sheet.setColumnWidth(--i, 6000);
		//sheet.setColumnWidth(--i, 6000);
		//sheet.setColumnWidth(1, 3200);
		//int rowcount = 1;
		int length = recordList.size() >= 60000 ? 60000 : recordList.size();
		for (int j = 0; j < length; j++) {
			// 每有一行数据创建一行
			row = sheet.createRow(j+1);
			//row.setHeight((short) 2000);
			Record model = recordList.get(j);
			String[] modelvaule={model.getStr("O_DESCRIBE"),model.getStr("O_CREATE_TIME"),model.getStr("O_FINISH_TIME")
					,model.getStr("Real_FINISH_TIME"),model.getStr("O_CUSTOMER_NAME"),model.getStr("O_EXECUTOR_NAME"),model.getStr("FINISH")};
			for (int k=0;k<7;k++) {
				// 每个数据创建一列
				cell = row.createCell(k);
				// 向列中添加样式，奇数行添加颜色
				cell.setCellStyle(contentStyle);
				
				// 向列中存值
				cell.setCellValue(modelvaule[k]);
			}
		}
		// 返回工作簿
		return workbook;
	}
}
