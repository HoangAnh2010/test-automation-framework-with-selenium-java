package mail;

import static constants.FrwConstants.REPORT_TITLE;

/**
 * Data for Sending email after execution
 */
public class EmailConfig {
    public static final String SERVER = "smtp.gmail.com";
    public static final String PORT = "587";

    public static final String FROM = "hoanganh04092001@gmail.com";
    public static final String PASSWORD = "";

    public static final String[] TO = {"hoanganhoriole2010@gmail.com"};
    public static final String SUBJECT = REPORT_TITLE;
}
