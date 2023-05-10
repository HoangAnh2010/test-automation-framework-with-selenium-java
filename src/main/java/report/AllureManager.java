package report;

import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.google.common.collect.ImmutableMap;
import constants.FrwConstants;
import enums.Browser;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class AllureManager {

    private AllureManager() {
    }

    public static void setAllureEnvironmentInformation() {
        AllureEnvironmentWriter.allureEnvironmentWriter(
                ImmutableMap.<String, String>builder().
                        put("Test URL", FrwConstants.URL_HRM).
                        put("Target Execution", FrwConstants.TARGET).
                        put("Global Timeout", String.valueOf(FrwConstants.WAIT_DEFAULT)).
                        put("Page Load Timeout", String.valueOf(FrwConstants.WAIT_PAGE_LOADED)).
                        put("Headless Mode", FrwConstants.HEADLESS).
                        put("Local Browser", String.valueOf(Browser.CHROME)).
                        put("Remote URL", FrwConstants.REMOTE_URL).
                        put("Remote Port", FrwConstants.REMOTE_PORT).
                        build());

        System.out.println("Allure Reports is installed.");

    }

//    @Attachment(value = "Failed test screenshot", type = "image/png")
    public static byte[] takeScreenshotToAttachOnAllureReport(String s) {
        try {
            return FileUtils.readFileToByteArray(new File(s));
        } catch (Exception ex) {
            ex.getMessage();
        }
        return new byte[0];
    }

    @Attachment(value = "Take step screenshot", type = "image/png")
    public static byte[] takeScreenshotStep(String s) {
        try {
            return FileUtils.readFileToByteArray(new File(s));
        } catch (Exception ex) {
            ex.getMessage();
        }
        return new byte[0];
    }

//    @Attachment(value = "Browser Information", type = "text/plain")
//    public static String addBrowserInformationOnAllureReport() {
//        return BrowserInfoUtils.getOSInfo();
//    }

    //Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    //HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }
}