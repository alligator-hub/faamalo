package org.example.faamalobot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.io.File;
import java.util.List;

@Service
public class MessageMaker {

    public SendMessage make(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), message);
        sendMessage.setReplyMarkup(removeBtn());
        sendMessage.setParseMode(ParseMode.HTML);
        return sendMessage;
    }

    public SendPhoto makeSendPhoto(String username, String filePath) {
        return new SendPhoto(username, new InputFile(new File(filePath)));
    }

    public SendMessage make(Long chatId, String message, InlineKeyboardMarkup board) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), message);
        sendMessage.setReplyMarkup(board);
        sendMessage.setParseMode(ParseMode.HTML);
        return sendMessage;
    }

    public SendPhoto make(Long chatId, String message, InlineKeyboardMarkup board, String pathImage) {
        SendPhoto sendPhoto = new SendPhoto(String.valueOf(chatId), new InputFile(pathImage));
        sendPhoto.setReplyMarkup(board);
        sendPhoto.setCaption(message);
        sendPhoto.setParseMode(ParseMode.HTML);
        return sendPhoto;
    }

    public DeleteMessage makeDelete(Long chatId, Integer messageId) {
        return new DeleteMessage(String.valueOf(chatId), messageId);
    }

    private ReplyKeyboardRemove removeBtn() {
        return new ReplyKeyboardRemove(true, false);
    }
}
