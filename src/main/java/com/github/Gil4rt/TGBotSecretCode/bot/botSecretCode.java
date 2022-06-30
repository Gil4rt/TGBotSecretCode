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
public class botSecretCode extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String username;

    database db = new database();

    @Value("${bot.token}")
    private String token;

    @Override
    public void onUpdateReceived(Update update) {

        String message_text = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();

        long getUserId = update.getMessage().getChatId();
        long adminId = db.getAdminId();

        if (update.getMessage().getText().equals("/start"))
        {
            SendMessage message = new SendMessage();

            if(getUserId == adminId)
            {
                AdminPanelShow(chatId, message);
            }
            else {
                message.setChatId(chatId);
                message.setText("Привет");

                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                row.add("Ебанная кнопка ");
                row.add("Ебанная кнопка ");
                row.add("Ебанная кнопка ");
                keyboard.add(row);
                row = new KeyboardRow();
                row.add("Ебанная кнопка ");
                row.add("Ебанная кнопка ");
                row.add("Ебанная кнопка ");
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(keyboardMarkup);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(update.getMessage().getText().equals("Изменить секретное слово") && getUserId == adminId)
        {

        }
        else
        {
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
    public void AdminPanelShow(String chatId, SendMessage message)
    {
        message.setChatId(chatId);
        message.setText("Привет Администратор! \uD83D\uDE0E ");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Изменить секретное слово");
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("Отобразить статистику");
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
        return token;
    }
}