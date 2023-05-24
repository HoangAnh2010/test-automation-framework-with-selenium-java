package utils;

import constants.FrwConstants;
import mail.EmailAttachmentsSender;

import javax.mail.MessagingException;

import static mail.EmailConfig.*;

public class EmailSendUtils {

    private EmailSendUtils() {
        super();
    }

    public static void sendEmail(int count_totalTCs, int count_passedTCs, int count_failedTCs, int count_skippedTCs) {

        if (FrwConstants.SEND_EMAIL_TO_USERS.trim().equalsIgnoreCase(FrwConstants.YES)) {
            System.out.println("****************************************");
            System.out.println("Send Email - START");
            System.out.println("****************************************");

            System.out.println("File name: " + FrwConstants.getExtentReportFilePath());

            String messageBody = getTestCasesCountInFormat(count_totalTCs, count_passedTCs, count_failedTCs,
                    count_skippedTCs);

            String attachmentFile_ExtentReport = FrwConstants.getExtentReportFilePath();

            try {
                EmailAttachmentsSender.sendEmailWithAttachments(SERVER, PORT, FROM, PASSWORD, TO, SUBJECT, messageBody,
                        attachmentFile_ExtentReport);

                System.out.println("****************************************");
                System.out.println("Email sent successfully.");
                System.out.println("Send Email - END");
                System.out.println("****************************************");
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }

    }

    private static String getTestCasesCountInFormat(int count_totalTCs, int count_passedTCs, int count_failedTCs,
                                                    int count_skippedTCs) {
        System.out.println("count_totalTCs: " + count_totalTCs);
        System.out.println("count_passedTCs: " + count_passedTCs);
        System.out.println("count_failedTCs: " + count_failedTCs);
        System.out.println("count_skippedTCs: " + count_skippedTCs);

        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<table class='container' align='left' style='padding-top:20px'>\n" +
                "<tr>\n" +
                "<td colspan='4'>\n" +
                "<table align='left' style='padding-top:13px'>\n" +
                "<tr>\n" +
                "<td>Dear Mr./Ms.,</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>\n" +
                "I send you automation test results on " + DateUtils.getCurrentDateTime() + "\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr align='center'>\n" +
                "<td colspan='4'>\n" +
                "<h2>\n" + FrwConstants.REPORT_TITLE + "\n" +
                "</h2>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table style='background:#67c2ef;width:120px'>\n" +
                "<tr><td align='center'>Total</td></tr>\n" +
                "<tr>\n" +
                "<td style='font-size: 36px' class='value' align='center'>\n" +
                count_totalTCs+"\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "<td>\n" +
                "<table style='background:#79c447;width:120px'>\n" +
                "<tr><td align='center'>Passed</td></tr>\n" +
                "<tr>\n<td style='font-size: 36px' class='value' align='center'>\n" +
                count_passedTCs+"\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "<td>\n" +
                "<table style='background:#ff5454;width:120px'>\n" +
                "<tr><td align='center'>Failed</td></tr>\n" +
                "<tr>\n" +
                "<td style='font-size: 36px' class='value' align='center'>\n" +
                count_failedTCs+"\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "<td>\n" +
                "<table style='background:#fabb3d;width:120px'>\n" +
                "<tr><td align='center'>Skipped</td></tr>\n" +
                "<tr>\n" +
                "<td style='font-size: 36px' class='value' align='center'>\n" +
                count_skippedTCs+"\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td colspan='4'>\n" +
                "<table class='container' align='left' style='padding-top:13px'>\n" +
                "<tr>\n" +
                "<td>Have a nice day and stay safe!</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>\n" +
                "Best regards,\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>\n" +
                FrwConstants.AUTHOR +"\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";

    }

}