package utils;

import com.opencsv.bean.CsvToBeanBuilder;
import constants.FrwConstants;
import helpers.Helpers;
import model.DataOfSignIn;
import model.SignInPage;
import org.apache.commons.io.FileUtils;
import org.monte.media.Format;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CsvUtils {
    public static List<SignInPage>  readSignInPageCSVfile(String fileName){
        try (Reader reader = new FileReader(fileName)){
            List<SignInPage> beans = new CsvToBeanBuilder(reader)
                    .withType(SignInPage.class)
                    .withSkipLines(1)
                    .build()
                    .parse();

            return beans;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<DataOfSignIn>  readDataOfSignInCSVfile(String fileName) {
        try (Reader reader = new FileReader(fileName)){
            List<DataOfSignIn> beans = new CsvToBeanBuilder(reader)
                    .withType(DataOfSignIn.class)
                    .withSkipLines(1)
                    .build()
                    .parse();

            return beans;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static void main(String[] args) {

            List<DataOfSignIn> lst  = readDataOfSignInCSVfile("G:\\Download\\GP\\dataEngine\\DataSignIn.csv");
            lst.forEach(e-> System.out.println(e.toString()));
        List<SignInPage> lst2  = readSignInPageCSVfile("G:\\Download\\GP\\dataEngine\\signIn.csv");
        lst2.forEach(e-> System.out.println(e.toString()));
    }
}
