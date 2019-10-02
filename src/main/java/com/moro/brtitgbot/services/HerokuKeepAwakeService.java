package com.moro.brtitgbot.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class HerokuKeepAwakeService {

    @Value("${heroku.app.url}")
    private String HEROKU_APP_URL;

    private final RestTemplate restTemplate;

    @Autowired
    public HerokuKeepAwakeService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void keepAwake() {
        String message = restTemplate.getForObject(HEROKU_APP_URL, String.class);

        log.info("{}", message);
    }
}
