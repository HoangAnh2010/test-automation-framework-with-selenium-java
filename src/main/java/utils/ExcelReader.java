package utils;

import input.DataProvider;
import model.DataOfSignIn;
import model.DataOfSignUp;
import model.SignInPage;
import model.SignUpPage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelReader {
    public static String excelPath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\data.xlsx";

    public static List<DataProvider> getTestCases(String fileName, String sheetName, String scriptId) {
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
            return testSteps.stream().filter(t -> t.getScriptID().equals(scriptId)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DataOfSignIn getDataSignIn(String fileName, String sheetName, String scriptId) {
        try {
            FileInputStream file = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheet(sheetName);

            List<DataOfSignIn> testSteps = new ArrayList<>();
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                XSSFRow row = sheet.getRow(j);
                String scriptID = row.getCell(0).getStringCellValue();
                String description = row.getCell(1).getStringCellValue();
                String email = row.getCell(2).getStringCellValue();
                String password = row.getCell(3).getStringCellValue();
                String result = row.getCell(4).getStringCellValue();
                String note = "";

                testSteps.add(new DataOfSignIn(scriptID, description, email, password, result, note));
            }
            for(DataOfSignIn d : testSteps){
                if(d.getTcId().equals(scriptId)){
                    return d;
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<DataProvider> getTestStep(String fileName, String sheetName) {
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
            return testSteps;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DataOfSignUp getDataSignUp(String fileName, String sheetName, String scriptId) {
        try {
            FileInputStream file = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheet(sheetName);

            List<DataOfSignUp> testSteps = new ArrayList<>();
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                XSSFRow row = sheet.getRow(j);
                String scriptID = row.getCell(0).getStringCellValue();
                String description = row.getCell(1).getStringCellValue();
                String name = row.getCell(2).getStringCellValue();
                String email = row.getCell(3).getStringCellValue();
                String password = row.getCell(4).getStringCellValue();
                String passConfirm = row.getCell(5).getStringCellValue();
                String result = row.getCell(6).getStringCellValue();
                String note = "";

                testSteps.add(new DataOfSignUp(scriptID,description,email,password,passConfirm,result,note,name));
            }
            for(DataOfSignUp d : testSteps){
                if(d.getTcId().equals(scriptId)){
                    return d;
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<DataProvider> getFinalDataSignIn(List<DataProvider>dataProviders,DataOfSignIn dataOfSignIn){
        dataProviders.stream().forEach((t)->{
            if(t.getTestData().equals("varEmail")){
                t.setTestData(dataOfSignIn.getEmail());
            }

            if(t.getTestData().equals("varPassword")){
                t.setTestData(dataOfSignIn.getPassword());
            }

            if(t.getTestData().equals("varResult")){
                t.setTestData(dataOfSignIn.getResult());
            }
        });
        return dataProviders;
    }
    public static List<DataProvider> getFinalDataSignUp(List<DataProvider> dataProviders,DataOfSignUp dataOfSignUp){
        dataProviders.stream().forEach((t)->{
            if(t.getTestData().equals("varEmail")){
                t.setTestData(dataOfSignUp.getEmail());
            }

            if(t.getTestData().equals("varPassword")){
                t.setTestData(dataOfSignUp.getPassword());
            }
            if(t.getTestData().equals("varName")){
                t.setTestData(dataOfSignUp.getName());
            }

            if(t.getTestData().equals("varPasswordCf")){
                t.setTestData(dataOfSignUp.getPasswordConfirm());
            }
            if(t.getTestData().equals("varResult")){
                t.setTestData(dataOfSignUp.getResult());
            }
        });

        return dataProviders;
    }


    public static void main(String[] args) {
        List<DataProvider> signIn = getFinalDataSignIn(getTestStep(excelPath,"SignInPage"),getDataSignIn(excelPath,"DataOfSignIn","TC_DN1"));
        List<DataProvider> signUp = getFinalDataSignUp(getTestStep(excelPath,"SignUpPage"),getDataSignUp(excelPath,"DataOfSignUp","TC_DK10"));
        System.out.println("sadasdasdsdada");
    }
}

//    public static List<DataProvider> getSignIn(String fileName, String sheetName) {
//        try {
//            FileInputStream file = new FileInputStream(fileName);
//            XSSFWorkbook workbook = new XSSFWorkbook(file);
//
//            XSSFSheet sheet = workbook.getSheet(sheetName);
//
//            List<DataProvider> testSteps = new ArrayList<>();
//            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
//                XSSFRow row = sheet.getRow(j);
//                String scriptID = row.getCell(0).getStringCellValue();
//                String scriptTitle = row.getCell(1).getStringCellValue();
//                String stepID = row.getCell(2).getStringCellValue();
//                String description = row.getCell(3).getStringCellValue();
//                String keyword = row.getCell(4).getStringCellValue();
//                String locatorType = row.getCell(5).getStringCellValue();
//                String locatorValue = row.getCell(6).getStringCellValue();
//                String testData = row.getCell(7).getStringCellValue();
//
//                testSteps.add(new DataProvider(scriptID, scriptTitle, stepID, description, keyword, locatorType, locatorValue, testData));
//            }
//            return testSteps;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
