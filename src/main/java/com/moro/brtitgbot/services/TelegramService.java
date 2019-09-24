package com.moro.brtitgbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

@Service
public class TelegramService {

    @Value("${telegram.bot.token}")
    private String TELEGRAM_BOT_TOKEN;
    @Value("${telegram.chat.id}")
    private String TELEGRAM_CHAT_ID;

    private final TelegramApiClient apiClient;

    @Autowired
    public TelegramService(final TelegramApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void sendMessages(List<String> postsMessages) throws URISyntaxException {
        for (String message : postsMessages) {
            apiClient.sendMessage(message, TELEGRAM_CHAT_ID, TELEGRAM_BOT_TOKEN, "Markdown");
        }
    }
}
