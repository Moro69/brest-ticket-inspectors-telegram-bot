package com.moro.brtitgbot.services;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;

@Component
public class TelegramApiClient {

    @Value("${telegram.bot.token}")
    private String TELEGRAM_BOT_TOKEN;

    private static final String URL_TEMPLATE= "https://api.telegram.org/bot%s/sendMessage";
    private static final String CHAT_ID_URL_PARAM_NAME = "chat_id";
    private static final String PARSE_MODE_URL_PARAM_NAME = "parse_mode";
    private static final String TEXT_URL_PARAM_NAME = "text";

    private final RestTemplate restTemplate;

    @Autowired
    public TelegramApiClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendMessages(List<String> postsMessages) throws URISyntaxException {

        for (String message : postsMessages) {
            URIBuilder uriBuilder = new URIBuilder(String.format(URL_TEMPLATE, TELEGRAM_BOT_TOKEN));
            uriBuilder.addParameter(CHAT_ID_URL_PARAM_NAME, "@brest_ticket_inspectors");
            uriBuilder.addParameter(PARSE_MODE_URL_PARAM_NAME, "Markdown");
            uriBuilder.addParameter(TEXT_URL_PARAM_NAME, message);

            restTemplate.getForObject(uriBuilder.build(), String.class);
        }
    }
}
