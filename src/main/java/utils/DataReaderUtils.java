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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DataReaderUtils {
    public static String excelPath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\data.xlsx";

    public static List<DataOfSignIn> getDataSignIn(String fileName, String sheetName, String scriptId) {
        try {
            FileInputStream file = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheet(sheetName);

            List<DataOfSignIn> testSteps = new ArrayList<>();
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                XSSFRow row = sheet.getRow(j);
                String tcID = row.getCell(0)==null?"":row.getCell(0).getStringCellValue();
                String description = row.getCell(1)==null?"":row.getCell(1).getStringCellValue();
                String email = row.getCell(2)==null?"":row.getCell(2).getStringCellValue();
                String password = row.getCell(3)==null?"":row.getCell(3).getStringCellValue();
                String result = row.getCell(4)==null?"":row.getCell(4).getStringCellValue();
                String note = "";

                testSteps.add(new DataOfSignIn(tcID, description, email, password, result, note));
            }
           return testSteps.stream().filter(t->t.getTcId().equals(scriptId)).collect(Collectors.toList());
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
                String scriptID = row.getCell(0)==null?"":row.getCell(0).getStringCellValue();
                String scriptTitle = row.getCell(1)==null?"":row.getCell(1).getStringCellValue();
                String stepID = row.getCell(2)==null?"":row.getCell(2).getStringCellValue();
                String description = row.getCell(3)==null?"":row.getCell(3).getStringCellValue();
                String keyword = row.getCell(4)==null?"":row.getCell(4).getStringCellValue();
                String locatorType = row.getCell(5)==null?"":row.getCell(5).getStringCellValue();
                String locatorValue = row.getCell(6)==null?"":row.getCell(6).getStringCellValue();
                String testData = row.getCell(7)==null?"":row.getCell(7).getStringCellValue();

                testSteps.add(new DataProvider(scriptID, scriptTitle, stepID, description, keyword, locatorType, locatorValue, testData));
            }
            return testSteps;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<DataOfSignUp> getDataSignUp(String fileName, String sheetName, String scriptId) {
        try {
            FileInputStream file = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheet(sheetName);

            List<DataOfSignUp> testSteps = new ArrayList<>();
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                XSSFRow row = sheet.getRow(j);
                String tcID = row.getCell(0)==null?"":row.getCell(0).getStringCellValue();
                String description = row.getCell(1)==null?"":row.getCell(1).getStringCellValue();
                String name = row.getCell(2)==null?"":row.getCell(2).getStringCellValue();
                String email = row.getCell(3)==null?"":row.getCell(3).getStringCellValue();
                String password = row.getCell(4)==null?"":row.getCell(4).getStringCellValue();
                String passConfirm = row.getCell(5)==null?"":row.getCell(5).getStringCellValue();
                String result = row.getCell(6)==null?"":row.getCell(6).getStringCellValue();
                String note = "";

                testSteps.add(new DataOfSignUp(tcID,description,email,password,passConfirm,result,note,name));
            }
            return testSteps.stream().filter(t->t.getTcId().equals(scriptId)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<DataProvider> getFinalDataSignIn(List<DataProvider>dataProviders,List<DataOfSignIn> dataOfSignIns){
        if(dataOfSignIns.size()>0){
            DataOfSignIn dataOfSignIn = dataOfSignIns.get(0);

            for(DataProvider t : dataProviders){
                if(Objects.isNull(t.getTestData())){
                    continue;
                }
                if(t.getTestData().equals("varEmail")){
                    t.setTestData(dataOfSignIn.getEmail()==null?"":dataOfSignIn.getEmail());
                }
                if(t.getTestData().equals("varPassword")){
                    t.setTestData(dataOfSignIn.getPassword()==null?"":dataOfSignIn.getPassword());
                }
                if(t.getTestData().equals("varExpected")){
                    t.setTestData(dataOfSignIn.getResult()==null?"":dataOfSignIn.getResult());
                }
            }
            return dataProviders;
        }
        return null;
    }
    public static List<DataProvider> getFinalDataSignUp(List<DataProvider> dataProviders,List<DataOfSignUp> dataOfSignUps){
        DataOfSignUp dataOfSignUp = dataOfSignUps.get(0);
        for(DataProvider t : dataProviders){
            if(Objects.isNull(t.getTestData())){
                continue;
            }
            if(t.getTestData().equals("varName")){
                t.setTestData(dataOfSignUp.getName()==null?"":dataOfSignUp.getName());
            }
            if(t.getTestData().equals("varEmail")){
                t.setTestData(dataOfSignUp.getEmail()==null?"":dataOfSignUp.getEmail());
            }
            if(t.getTestData().equals("varPassword")){
                t.setTestData(dataOfSignUp.getPassword()==null?"":dataOfSignUp.getPassword());
            }
            if(t.getTestData().equals("varPasswordCf")){
                t.setTestData(dataOfSignUp.getPasswordConfirm()==null?"":dataOfSignUp.getPasswordConfirm());
            }
            if(t.getTestData().equals("varExpected")){
                t.setTestData(dataOfSignUp.getResult()==null?"":dataOfSignUp.getResult());
            }
        }

        return dataProviders;
    }

    public static List<DataProvider>convertSignInToDataProvider(List<SignInPage>signInPages){
        List<DataProvider> dataProviders = new ArrayList<>();
        for(SignInPage s: signInPages) {
            dataProviders.add(new DataProvider(s));
        }
        return dataProviders;
    }

    public static List<DataProvider>convertSignUpToDataProvider(List<SignUpPage >signInPages){
        List<DataProvider> dataProviders = new ArrayList<>();
        for(SignUpPage s: signInPages){
            dataProviders.add(new DataProvider(s));
        }
        return dataProviders;
    }
    public static void main(String[] args) {
        List<DataProvider> signIn = getFinalDataSignIn(getTestStep(excelPath,"SignInPage"),getDataSignIn(excelPath,"DataOfSignIn","TC_DN1"));
        List<DataProvider> signUp = getFinalDataSignUp(getTestStep(excelPath,"SignUpPage"),getDataSignUp(excelPath,"DataOfSignUp","TC_DK10"));
    }
}

//
//public static List<DataProvider> getTestCases(String fileName, String sheetName, String scriptId) {
//    try {
//        FileInputStream file = new FileInputStream(fileName);
//        XSSFWorkbook workbook = new XSSFWorkbook(file);
//
//        XSSFSheet sheet = workbook.getSheet(sheetName);
//
//        List<DataProvider> testSteps = new ArrayList<>();
//        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
//            XSSFRow row = sheet.getRow(j);
//            String scriptID = row.getCell(0).getStringCellValue();
//            String scriptTitle = row.getCell(1).getStringCellValue();
//            String stepID = row.getCell(2).getStringCellValue();
//            String description = row.getCell(3).getStringCellValue();
//            String keyword = row.getCell(4).getStringCellValue();
//            String locatorType = row.getCell(5).getStringCellValue();
//            String locatorValue = row.getCell(6).getStringCellValue();
//            String testData = row.getCell(7).getStringCellValue();
//
//            testSteps.add(new DataProvider(scriptID, scriptTitle, stepID, description, keyword, locatorType, locatorValue, testData));
//        }
//        return testSteps.stream().filter(t -> t.getScriptID().equals(scriptId)).collect(Collectors.toList());
//    } catch (IOException e) {
//        e.printStackTrace();
//        return null;
//    }
//}