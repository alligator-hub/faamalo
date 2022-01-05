package org.example.faamalobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.faamalobot.model.UpdateDto;
import org.example.faamalobot.service.CallBackService;
import org.example.faamalobot.service.DefaultService;
import org.example.faamalobot.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class MyBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;

    @Autowired
    @Lazy
    CallBackService callBackService;

    @Autowired
    @Lazy
    TextService textService;

    @Autowired
    @Lazy
    DefaultService defaultService;


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                UpdateDto updateDto = getUpdateDtoFromMessage(update);
                defaultService.receiveAction(updateDto);
                textService.map(updateDto);
            }
        } else if (update.hasCallbackQuery()) {
            UpdateDto updateDto = getUpdateDtoFromCallBackQuery(update);
            defaultService.receiveAction(updateDto);
            callBackService.map(updateDto);
        }
    }


    private UpdateDto getUpdateDtoFromMessage(Update update) {
        UpdateDto updateDto = new UpdateDto();
        updateDto.setUpdate(update);

        updateDto.setChatId(update.getMessage().getChatId());
        updateDto.setMessageId(update.getMessage().getMessageId());

        updateDto.isTextMessage(true);
        updateDto.isCallBackQuery(false);

        updateDto.setText(update.getMessage().getText());
        updateDto.setFirstname(update.getMessage().getChat().getFirstName());
        updateDto.setLastname(update.getMessage().getChat().getLastName());
        updateDto.setUsername(update.getMessage().getChat().getUserName());
        return updateDto;
    }

    private UpdateDto getUpdateDtoFromCallBackQuery(Update update) {
        UpdateDto updateDto = new UpdateDto();
        updateDto.setUpdate(update);

        updateDto.setChatId(update.getCallbackQuery().getMessage().getChatId());
        updateDto.setMessageId(update.getCallbackQuery().getMessage().getMessageId());

        updateDto.isTextMessage(false);
        updateDto.isCallBackQuery(true);

        updateDto.setText(update.getCallbackQuery().getMessage().getText());
        updateDto.setFirstname(update.getCallbackQuery().getMessage().getChat().getFirstName());
        updateDto.setLastname(update.getCallbackQuery().getMessage().getChat().getLastName());
        updateDto.setUsername(update.getCallbackQuery().getMessage().getChat().getUserName());

        updateDto.setQuery(update.getCallbackQuery());
        return updateDto;
    }
}
