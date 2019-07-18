package com.moro.tibtbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ScheduledService {

    @Autowired
    private VkService vkService;
    @Autowired
    private TelegramService telegramService;

    @Scheduled(fixedDelay = 180000)
    public void getMessagesFromVkAndSendToTelegram() throws IOException, URISyntaxException {
        List<String> messages = vkService.getVkPostsMessages();

        telegramService.sendMessages(messages);
    }
}
