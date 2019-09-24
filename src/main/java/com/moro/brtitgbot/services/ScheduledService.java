package com.moro.brtitgbot.services;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

@Service
@Log4j2
public class ScheduledService {

    private final VkWallPostProvider vkWallPostProvider;
    private final TelegramApiClient telegramApiClient;

    @Autowired
    public ScheduledService(final VkWallPostProvider vkWallPostProvider,
                            final TelegramApiClient telegramApiClient) {
        this.vkWallPostProvider = vkWallPostProvider;
        this.telegramApiClient = telegramApiClient;
    }

    @Scheduled(fixedDelay = 10000)
    public void getMessagesFromVkAndSendToTelegram() throws URISyntaxException, ClientException, ApiException {
        log.info("getMessagesFromVkAndSendToTelegram");
        List<String> postsMessages = vkWallPostProvider.getLastWallPostsMessages();
        log.info("messages received: {}", postsMessages);

        telegramApiClient.sendMessages(postsMessages);
    }
}
