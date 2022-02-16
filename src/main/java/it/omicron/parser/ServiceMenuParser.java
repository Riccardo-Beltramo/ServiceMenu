package it.omicron.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

public class ServiceMenuParser {
	public static void main(String[] args) {




		try {
			Properties aProp = new Properties();
			aProp.load(new FileInputStream ("./config.properties"));
			FileReader reader = new FileReader(aProp.getProperty("path"));
			Gson gson = new Gson();
			MenuContent menuContent = gson.fromJson(reader, MenuContent.class);
			List<MenuNode> lista = menuContent.getNodes();
			menuContent.proccessJson(lista,0);
			Files.createDirectories(Paths.get(aProp.getProperty("outputFolderPath")));



			int indRow = 1;
			int depth = 0;


			File excelFile = new File(aProp.getProperty("filePath"));
			XSSFWorkbook workbook = new XSSFWorkbook();

			Sheet sheet = workbook.createSheet("Menu"+menuContent.getVersion());
			Row rowHeader = sheet.createRow(0);

			for(MenuNode node : menuContent.getCleanNodes()) {
				if(depth < node.getDepth()) {
					depth = node.getDepth();
				}
				Row row = sheet.createRow(indRow);
				if(node.getNodeType().equals("service")) {
					row.createCell(menuContent.getMaxDepth()+1).setCellValue(node.getNodeId());

				}
				row.createCell(menuContent.getMaxDepth()+2).setCellValue(node.getNodeName());
				row.createCell(menuContent.getMaxDepth()+3).setCellValue(node.getNodeType());
				row.createCell(menuContent.getMaxDepth()+4).setCellValue(node.getGroupType());
				row.createCell(menuContent.getMaxDepth()+5).setCellValue(node.getFlowType());
				if(node.getResource() != null) {
					row.createCell(menuContent.getMaxDepth()+6).setCellValue(node.getResource().getId());
				}

				row.createCell(node.getDepth()).setCellValue("X");
				indRow++;
				sheet.autoSizeColumn(menuContent.getMaxDepth()+7);


			}

			int l = depth;

			for (int k = 0; k <= l; k++) {
				rowHeader.createCell(k).setCellValue( k);


			}

			rowHeader.createCell(depth+1).setCellValue(aProp.getProperty("nodeId"));
			rowHeader.createCell(depth+2).setCellValue(aProp.getProperty("nodeName"));
			rowHeader.createCell(depth+3).setCellValue(aProp.getProperty("nodeType"));
			rowHeader.createCell(depth+4).setCellValue(aProp.getProperty("groupType"));
			rowHeader.createCell(depth+5).setCellValue(aProp.getProperty("flowType"));
			rowHeader.createCell(depth+6).setCellValue(aProp.getProperty("resourceId"));


			Integer columns = new Integer(rowHeader.getLastCellNum());
			for(int j = 0; j<columns;j++){
				sheet.autoSizeColumn(j);
			}



			FileOutputStream out = new FileOutputStream(excelFile);
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {

		}

	}
}