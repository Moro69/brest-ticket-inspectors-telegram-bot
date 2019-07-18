package com.moro.tibtbot.web;

import com.moro.tibtbot.model.telegram.TelegramSendMessageResponse;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Controller
public class TelegramApiClient {

    private static final String CHAT_ID_URL_PARAM_NAME = "chat_id";
    private static final String PARSE_MODE_URL_PARAM_NAME = "parse_mode";
    private static final String TEXT_URL_PARAM_NAME = "text";

    @Value("${telegram.bot.token}")
    private String botToken;

    @Autowired
    private RestTemplate restTemplate;

    public TelegramSendMessageResponse sendMessage(String chatId, String parseMode, String text) throws URISyntaxException {
        String urlTemplate = "https://api.telegram.org/bot%s/sendMessage";
        URIBuilder uriBuilder = new URIBuilder(String.format(urlTemplate, botToken));
        uriBuilder.addParameter(CHAT_ID_URL_PARAM_NAME, chatId);
        uriBuilder.addParameter(PARSE_MODE_URL_PARAM_NAME, parseMode);
        uriBuilder.addParameter(TEXT_URL_PARAM_NAME, text);

        TelegramSendMessageResponse response = restTemplate.getForObject(uriBuilder.build(),
                TelegramSendMessageResponse.class);

        return response;
    }
}
