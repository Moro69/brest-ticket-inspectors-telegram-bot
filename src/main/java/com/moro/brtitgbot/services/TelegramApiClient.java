package com.moro.brtitgbot.services;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Component
public class TelegramApiClient {

    private static final String SEND_MESSAGE_URL_TEMPLATE = "https://api.telegram.org/bot%s/sendMessage";
    private static final String CHAT_ID_URL_PARAM_NAME = "chat_id";
    private static final String PARSE_MODE_URL_PARAM_NAME = "parse_mode";
    private static final String TEXT_URL_PARAM_NAME = "text";

    private final RestTemplate restTemplate;

    @Autowired
    public TelegramApiClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendMessage(String postsMessage, String chatId, String botToken, String parseMode) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(String.format(SEND_MESSAGE_URL_TEMPLATE, botToken));
        uriBuilder.addParameter(CHAT_ID_URL_PARAM_NAME, chatId);
        uriBuilder.addParameter(PARSE_MODE_URL_PARAM_NAME, parseMode);
        uriBuilder.addParameter(TEXT_URL_PARAM_NAME, postsMessage);

        restTemplate.getForObject(uriBuilder.build(), String.class);
    }
}
