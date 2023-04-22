package utils;

import com.google.gson.Gson;
import model.Data;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JsonUtils {
    public static Data readData(String filePath){
        Gson gson = new Gson();

        try (Reader reader = new FileReader(filePath)) {

            // Convert JSON File to Java Object
            Data data = gson.fromJson(reader, Data.class);
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Data data = readData("G:\\Download\\GP\\dataEngine\\data.json");
        System.out.println(data.getSignUpPage().get(0).toString());
    }
}
