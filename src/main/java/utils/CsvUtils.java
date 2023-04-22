package utils;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import model.DataOfSignIn;
import model.SignInPage;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
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
