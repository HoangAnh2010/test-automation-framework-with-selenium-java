package helpers;

import exceptions.InvalidPathForExcelException;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.*;
import utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelHelpers {
    private static FileInputStream fis;
//    private static FileOutputStream fileOut;
    private static Workbook wb;
    private static Sheet sheet;
    private static Cell cell;
//    private static Row row;
    private static CellStyle cellStyle;

    private static String excelFilePath;
    private static Map<String, Integer> columns = new HashMap<String, Integer>();
    
  //1. WORKING WITH EXCEL FILE
    //Cau truc 1 file Excel bao gom: WorkBook, WrokSheet, Row, Column, Cell
    //Thu vien Apache POI cung cap cac class de lam viec voi cac
    //thanh phan nay cua Excel
    
    //Phuong thuc tao file Excel neu chua co
    public static void setExcelFile(String excelPath, String sheetName) throws Exception {
    	LogUtils.info("Set Excel File: " + excelPath);
        LogUtils.info("Sheet Name: " + sheetName);
    	try {
            File f = new File(excelPath);

            if (!f.exists()) {
            	try {
                    LogUtils.info("File Excel path not found.");
                    throw new InvalidPathForExcelException("File Excel path not found.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (sheetName.isEmpty()) {
                try {
                    LogUtils.info("The Sheet Name is empty.");
                    throw new InvalidPathForExcelException("The Sheet Name is empty.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            fis = new FileInputStream(excelPath);
            wb = WorkbookFactory.create(fis);
            sheet = wb.getSheet(sheetName);
            if (sheet == null) {
            	//sheet = wb.createSheet(sheetName);
            	try {
                    LogUtils.info("Sheet name not found.");
                    throw new InvalidPathForExcelException("Sheet name not found.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            excelFilePath = excelPath;

            //adding all the column header names to the map 'columns'
            sheet.getRow(0).forEach(cell ->{
                columns.put(cell.getStringCellValue(), cell.getColumnIndex());
            });

        } catch (Exception e) {
        	e.getMessage();
            LogUtils.error(e.getMessage());
        }
    }
    
	public static Sheet getSheet(String SheetName) {
		 Sheet sheet=wb.getSheet(SheetName);
		if(!isSheet(SheetName)) {
			sheet=wb.createSheet(SheetName);
		}
		return sheet;
	}

	public static void open() throws IOException{
		File file=new File(excelFilePath);
		if(file.canRead()) {
			FileInputStream fileInput=new FileInputStream(file);
			wb = WorkbookFactory.create(fileInput);
			fileInput.close();
		}
	}
	
	public static void save()throws IOException{
		FileOutputStream streamOut=new FileOutputStream(excelFilePath);
		wb.write(streamOut);
		streamOut.flush();
		streamOut.close();
	}

	public static void saveAs(String path)throws IOException{
		FileOutputStream streamOut=new FileOutputStream(path);
		wb.write(streamOut);
		streamOut.flush();
		streamOut.close();
	}
	
	public static boolean isSheet(String SheetName) {
		return wb.getSheetIndex(SheetName)>=0;
	}

	public static void addSheet(String SheetName) {
		if(!isSheet(SheetName)) {
			wb.createSheet(SheetName);
		}
	}
	
	public static void removeSheet(int SheetIndex) {
		wb.removeSheetAt(SheetIndex);
	}
	
	public static void removeSheet(String SheetName) {
		int index=wb.getSheetIndex(SheetName);
		removeSheet(index);
	}

	//2. WORKING WITH COLUMN IN EXCEL
	public static void addColumn(Sheet sheet, String ColName) {
		cellStyle=wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColorPredefined.RED.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		Row row=sheet.getRow(0);
		if(row==null)
			row=sheet.createRow(0);
		
		Cell cell;
		if(row.getLastCellNum()==-1) 
			cell=row.createCell(0);
		else
			cell=row.createCell(row.getLastCellNum());
		cell.setCellValue(ColName);
		//cell.setCellStyle(style);
	}

	public static void addColumn(String SheetName,String ColName) {
		Sheet ws=getSheet(SheetName);
		addColumn(ws, ColName);
	}
	
	public static void removeColumn(String SheetName, int colNum) {
		Sheet sheet=getSheet(SheetName);

		int rowCount=sheet.getLastRowNum()+1; 
		 
		Row row;
		for(int i=0;i<rowCount;i++) {
			row=sheet.getRow(i);
			if(row!=null) {
				Cell cell =row.getCell(colNum);
				if(cell!=null) {
					row.removeCell(cell);
				}
			}
		}
	}

	public static int convertColNameToColNum(Sheet sheet, String colName) {
		Row row=sheet.getRow(0);
		int cellRowNumber=row.getLastCellNum();
		int colNum=-1;
		
		for(int i=0;i<cellRowNumber;i++) {
			if(row.getCell(i).getStringCellValue().trim().equals(colName)) {
				colNum=i;
			}
		}
		return colNum;
	}

	//3. WORKING WITH ROW IN EXCEL
		public static Row getRow(Sheet sheet, int rowIndex) {
			Row row=sheet.getRow(rowIndex);
			if(row==null) {
				row=sheet.createRow(rowIndex);
			}
			return row;
		}
		
		// This method is to get the row count used of the excel sheet
		public static int getRowCount(String sheetName) {
			sheet = wb.getSheet(sheetName);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}

 	//4. WORKING WITH CELL IN EXCEL
	   //Chi so hang/cot tren excel duoc tinh tu 0
	public static String getCellData(int rownum, int colnum) {
		try {
			cell = sheet.getRow(rownum).getCell(colnum);
			String cellData = null;
					
				switch (cell.getCellType()) {
					case STRING:
						cellData = cell.getStringCellValue();
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
	                        cellData = String.valueOf(cell.getDateCellValue());
	                    } else {
	                        cellData = String.valueOf((long) cell.getNumericCellValue());
	                    }
						break;
					case BOOLEAN:
						cellData=Boolean.toString(cell.getBooleanCellValue());
						break;
					case BLANK:
						cellData="";
						break;
					case FORMULA:
						cellData=cell.getCellFormula();
						break;
				}
				return cellData;

		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getCellData(String sheetName, int rowIndex, int colIndex) {
		Sheet sheet = getSheet(sheetName);
		Row row=getRow(sheet, rowIndex);
		Cell cell=getCell(row, colIndex);
		return cell.getStringCellValue();
	} 
	
	public static Cell getCell(Row row, int colIndex) {
		Cell cell=row.getCell(colIndex-1);
		if(cell==null) {
			cell=row.createCell(colIndex-1);
		}
		return cell;
	}
	
	public static void setCell(Sheet sheet, int rowIndex, int colIndex, String value) {
		Row row=getRow(sheet, rowIndex);
		Cell cell=getCell(row,colIndex);
		cell.setCellValue(value);
	} 
	public static void setCell(String sheetName,int rowIndex,int colIndex, String value) {
		Sheet sheet=getSheet(sheetName);
		setCell(sheet, rowIndex, colIndex, value);
	}
	
	public static void setCell(String sheetName,String colName,int rowIndex, String value) {
		Sheet sheet=getSheet(sheetName);
		int colIndex=convertColNameToColNum(sheet,colName);
		setCell(sheet, rowIndex, colIndex, value);
	}
}
