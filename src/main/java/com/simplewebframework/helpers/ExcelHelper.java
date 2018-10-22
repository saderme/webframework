package com.simplewebframework.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.application.dataprovider.BBOSTestAccount;
import com.simplewebframework.core.TestLogger;

public class ExcelHelper {

	private Logger log = TestLogger.getLogger(ExcelHelper.class);

	public static Object[][] getExcelData(String excelLocation, String sheetName) {

		try {
			Object dataSets[][] = null;
			FileInputStream file = new FileInputStream(new File(excelLocation));
			// Create Workbook instance
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get sheet Name from Workbook
			XSSFSheet sheet = workbook.getSheet(sheetName);

			// count number of active rows in excel sheet
			int totalRow = sheet.getLastRowNum();
            //System.out.println(totalRow);
			// count active columns in row
			int totalColumn = sheet.getRow(0).getLastCellNum();
            //System.out.println(totalColumn);
			dataSets = new Object[totalRow+1][totalColumn];

			// Iterate Through each Rows one by one.
			Iterator<Row> rowIterator = sheet.iterator();
			int i = 0;
			while (rowIterator.hasNext()) {
				i++;
				// for Every row , we need to iterator over columns
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				int j = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					dataSets[i-1][j++] = cell.getStringCellValue();
				}
			}
			return dataSets;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object[][] getExcelDataToBBOS(String excelLocation, String sheetName) {
		try {
			Object dataSets[][] = null;
			FileInputStream file = new FileInputStream(new File(excelLocation));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int totalRow = sheet.getLastRowNum();
			int totalColumn = sheet.getRow(0).getLastCellNum();

			dataSets = new Object[totalRow+1][1];

			// Iterate Through each Rows one by one.
			Iterator<Row> rowIterator = sheet.iterator();
			int i = 0;
			while (rowIterator.hasNext()) {
				i++;
				BBOSTestAccount bbta = new BBOSTestAccount();
				
				// for Every row , we need to iterator over columns
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				int j = 0;
				String login = "";
				String email= "";
				String password= "";
				String runmode= "";
				
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					
					switch (j) {
					case 0:
						login = cell.getStringCellValue();
						//bbta.setLogin(cell.getStringCellValue());
						break;
					case 1:
						email = cell.getStringCellValue();
						//bbta.setEmail(cell.getStringCellValue());
						break;
					case 2:
						password = cell.getStringCellValue();
						//bbta.setPassword(cell.getStringCellValue());
						break;
					case 3:
						runmode = cell.getStringCellValue();
						//bbta.setRunMode(cell.getStringCellValue());	
						break;
					}
					j++;
				}
				//dataSets[i-1][0] = bbta;
				dataSets[i-1][0] = new BBOSTestAccount(login,email,password,runmode);
			}
			return dataSets;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateResult(String excelLocation, String sheetName, String testCaseName, String testStatus){
		try{
			FileInputStream file = new FileInputStream(new File(excelLocation));
			// Create Workbook instance
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get sheet Name from Workbook
			XSSFSheet sheet = workbook.getSheet(sheetName);
			// count number of active rows in excel sheet
			int totalRow = sheet.getLastRowNum()+1;
			for(int i =1; i<totalRow; i++){
				XSSFRow r = sheet.getRow(i);
				String ce = r.getCell(0).getStringCellValue();
				if(ce.contains(testCaseName)){
					r.createCell(1).setCellValue(testStatus);
					file.close();
					log.info("result updated..");
					FileOutputStream out = new FileOutputStream(new File(excelLocation));
					workbook.write(out);
					out.close();
					break;
				}
			}
		}
		catch(Exception e){
			
		}
	}
	
	public static void main(String[] args) {
	 ExcelHelper excelHelper = new ExcelHelper();
	 String excelLocation = ResourceHelper.getResourcePath("src/main/resources/testdata/testdata.xlsx");
	 Object[][] data = excelHelper.getExcelData(excelLocation, "loginData");
	 System.out.println(data);
//	 excelHelper.updateResult(excelLocation, "TestScripts", "Login", "PASS");
//	 excelHelper.updateResult(excelLocation, "TestScripts", "Registration", "FAIL");
//	 excelHelper.updateResult(excelLocation, "TestScripts", "Add to Cart", "PASS");
	 
	}

}

