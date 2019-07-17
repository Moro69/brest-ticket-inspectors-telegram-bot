package com.moro.tibtbot.service;

import com.moro.tibtbot.web.VkRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class ScheduledService {

    @Autowired
    VkRestClient vkRestClient;

    @Scheduled(fixedDelay = 5000)
    public void getVkPosts() throws IOException, URISyntaxException {
        System.out.println(vkRestClient.getPosts(-72869598, 2, 0).getItems());
    }
}
