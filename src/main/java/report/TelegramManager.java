package report;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import constants.FrwConstants;
import utils.DateUtils;
import utils.LogUtils;

import java.io.File;

public class TelegramManager {
    private static String Token = FrwConstants.TELEGRAM_TOKEN;

    private static String ChatId = FrwConstants.TELEGRAM_CHATID;

    private static File input = new File(FrwConstants.EXTENT_REPORT_FILE_PATH);

    public static void sendReportPath() {
        if (FrwConstants.SEND_REPORT_TO_TELEGRAM.toLowerCase().trim().equals(FrwConstants.YES)) {
            SendMessage sendMessage = new SendMessage(FrwConstants.TELEGRAM_CHATID, "I send you automation test results on " + DateUtils.getCurrentDateTime());
            SendDocument sendDocument = new SendDocument(FrwConstants.TELEGRAM_CHATID, new File(FrwConstants.EXTENT_REPORT_FILE_PATH));
            TelegramBot bot = new TelegramBot(FrwConstants.TELEGRAM_TOKEN);
            try {
                SendResponse sendResponseMessage = bot.execute(sendMessage);
                SendResponse sendResponseDocument = bot.execute(sendDocument);
                boolean ok = sendResponseDocument.isOk();
                if (ok != true) {
                    Message message = sendResponseMessage.message();
                    Message document = sendResponseDocument.message();
                    LogUtils.warn("Message response from Telegram: " +message+" and "+ document);
                }
            } catch (Exception e) {
                LogUtils.error("Error Send Test Report to Telegram: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        sendReportPath();
    }

}