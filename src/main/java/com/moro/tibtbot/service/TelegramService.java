package com.moro.tibtbot.service;

import com.moro.tibtbot.model.telegram.TelegramSendMessageResponse;
import com.moro.tibtbot.web.TelegramApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

@Service
public class TelegramService {

    @Autowired
    private TelegramApiClient apiClient;

    public void sendMessages(List<String> messages) throws URISyntaxException {
        for (String message : messages) {
            TelegramSendMessageResponse response =
                    apiClient.sendMessage("@brest_ticket_inspectors", "Markdown", message);
        }
    }
}
