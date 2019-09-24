package com.moro.brtitgbot.services;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ScheduledService {

    private final VkWallPostProvider vkWallPostProvider;
    private final TelegramApiClient telegramApiClient;

    @Autowired
    public ScheduledService(final VkWallPostProvider vkWallPostProvider,
                            final TelegramApiClient telegramApiClient) {
        this.vkWallPostProvider = vkWallPostProvider;
        this.telegramApiClient = telegramApiClient;
    }

    @Scheduled(fixedDelay = 3000)
    public void getMessagesFromVkAndSendToTelegram() throws IOException, URISyntaxException,
            ClientException, ApiException {

        List<String> postsMessages = vkWallPostProvider.getLastWallPostsMessages();

        telegramApiClient.sendMessages(postsMessages);
    }
}
