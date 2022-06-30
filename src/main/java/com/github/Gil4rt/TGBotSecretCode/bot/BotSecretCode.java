package com.github.Gil4rt.TGBotSecretCode.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BotSecretCode extends TelegramLongPollingBot {

    Database db = new Database();

    private boolean waitSecretAnswer;

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Override
    public void onUpdateReceived(Update update) {

        String message_text = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();

        long getUserId = update.getMessage().getChatId();

        long adminId = db.getAdminId();

        if (update.getMessage().getText().equals("/start")) {
            SendMessage message = new SendMessage();

            if (getUserId == adminId) {
                adminPanelShow(chatId, message);
            } else {
                message.setChatId(chatId);
                message.setText("\uD83D\uDEAB Проход закрыт, без секретного слова не пустим \uD83D\uDEAB");

                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                row.add("Сказать секретное слово \uD83C\uDF81:");
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(keyboardMarkup);
                waitSecretAnswer = true;
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.getMessage().getText().equals("Изменить секретное слово \uD83E\uDD2B") && getUserId == adminId) {

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.enableHtml(true);
            message.setText("Текущее секретное слово \uD83E\uDD77: \r\n \r\n " + "<b>" + db.getSecretWord() + "</b>" + "\r\n \r\n" + "Какое новое слово устанавливаем? ✒️");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            waitSecretAnswer = true;

        } else if (waitSecretAnswer && getUserId == adminId) {
            waitSecretAnswer = false;
            db.saveSecretWord(message_text);
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.enableHtml(true);
            message.setText("Новое секретное слово: \uD83D\uDC69\u200D\uD83C\uDFA8: \r\n \r\n " + "<b>" + db.getSecretWord() + "</b>" );
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            try {
                db.updateDatabase();
                db.parseDataToDb();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        else if (update.getMessage().getText().equals("Отобразить статистику \uD83D\uDCCA") && getUserId == adminId) {

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Статистика текущего секретного слова \uD83D\uDDE3: \r\n\r\n 1️⃣ Ввод неверного слова " +
                    "\uD83D\uDC69\u200D\uD83D\uDCBB: " + db.getStatsAnyWord() +
                    "\r\n 2️⃣ Ввод верного слова \uD83D\uDC81\u200D♀️: " + db.getStatsSayedSecretWord()
                    + "\r\n 3️⃣ Вступление в группу ⛹️\u200D♀️: " + db.getStatsJoinedGroup());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.getMessage().getText().equals("Сказать секретное слово \uD83C\uDF81:")) {

        }
        else if (waitSecretAnswer) {

            waitSecretAnswer = false;
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            if(message_text.equals(db.getSecretWord())) {
                message.setText("Ты лучший ❤️, спасибо что не пропустил секретное слово \uD83E\uDD1D, мы банда \uD83D\uDE0E \r\n " +
                        "Ссылочка на группу: https://t.me/testirovanie12345Blin ");
                db.saveStatsSayedSecretWord();
            }
            else{
                message.setText("Слово не верное 😭 \r\n Возврат в меню");
                db.saveStatsAnyWord();
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            try {
                db.updateDatabase();
                db.parseDataToDb();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        else {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Неизвестная команда \uD83D\uDE4B\u200D♀️");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void adminPanelShow(String chatId, SendMessage message) {
        message.setChatId(chatId);
        message.setText("Привет Администратор! \uD83D\uDE0E ");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Изменить секретное слово \uD83E\uDD2B");
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("Отобразить статистику \uD83D\uDCCA");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {

        try {
            db.createDatabaseAndParse();
            db.parseDataToDb();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return token;
    }
}