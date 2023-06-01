package helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public final class Helpers {

    public Helpers() {
        super();
    }

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    /**
     * @return lấy đường dẫn đến thư mục nguồn source mình có thêm dấu / ở cuối luôn
     */
    public static String getCurrentDir() {
        return System.getProperty("user.dir") + File.separator;
    }

    /**
     * Tạo folder rỗng
     *
     * @param path đường dẫn cần tạo folder
     */
    public static void createFolder(String path) {
        // File is a class inside java.io package
        File file = new File(path);

        String result = null;

        int lengthSum = path.length();
        int lengthSub = path.substring(0, path.lastIndexOf('/')).length();

        result = path.substring(lengthSub, lengthSum);

        if (!file.exists()) {
            file.mkdir(); // mkdir is used to create folder
            System.out.println("Folder " + file.getName() + " created: " + path);
        } else {
            System.out.println("Folder already created");
        }
    }

    /**
     * @param str        chuỗi string cần tách ra theo điều kiện
     * @param valueSplit ký tự cần tách chuỗi thành mảng giá trị
     * @return mảng giá trị kiểu chuỗi sau khi tách
     */
    public static ArrayList<String> splitString(String str, String valueSplit) {
        ArrayList<String> arrayListString = new ArrayList<>();
        for (String s : str.split(valueSplit, 0)) {
            arrayListString.add(s);
        }
       
        return arrayListString;
    }
}
