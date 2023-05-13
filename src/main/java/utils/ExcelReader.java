package utils;

import input.DataProvider;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelReader {

    public static List<DataProvider>getTestCases(String fileName, String sheetName,String scriptId) {
        try {
            FileInputStream file = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheet(sheetName);

            List<DataProvider> testSteps = new ArrayList<>();
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                XSSFRow row = sheet.getRow(j);
                String scriptID = row.getCell(0).getStringCellValue();
                String scriptTitle = row.getCell(1).getStringCellValue();
                String stepID = row.getCell(2).getStringCellValue();
                String description = row.getCell(3).getStringCellValue();
                String keyword = row.getCell(4).getStringCellValue();
                String locatorType = row.getCell(5).getStringCellValue();
                String locatorValue = row.getCell(6).getStringCellValue();
                String testData = row.getCell(7).getStringCellValue();

                testSteps.add(new DataProvider(scriptID, scriptTitle, stepID, description, keyword, locatorType, locatorValue, testData));
            }
            return testSteps.stream().filter(t->t.getScriptID().equals(scriptId)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args) {
//        Map<String, List<DataProvider>> cuccut = getTestCases(System.getProperty("user.dir") + "\\src\\test\\resources\\data\\data.xlsx","SignInPage","LoginWithoutEmail");
//    }
}
