package com.moro.tibtbot.service;

import com.moro.tibtbot.model.vk.VkPost;
import com.moro.tibtbot.model.vk.response.VkGetCommentsResponse;
import com.moro.tibtbot.model.vk.response.VkGetPostsResponse;
import com.moro.tibtbot.web.VkApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VkService {

    @Autowired
    private VkApiClient vkRestClient;

    public List<String> getVkPostsMessages() throws IOException, URISyntaxException {
        VkGetPostsResponse response = vkRestClient.getPosts(-72869598, 5, 0);

        List<String> messages = new ArrayList<>();

        Optional.of(response)
                .map(VkGetPostsResponse::getItems)
                .orElseThrow(() -> new RuntimeException("some error"))
                .stream()
                .sorted(Comparator.comparing(VkPost::getDate))
                .forEach(vkPost -> {
                    Date postDate = Date.from(Instant.ofEpochSecond(vkPost.getDate()));
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = dateFormat.format(postDate);

                    String message = vkPost.getText();

                    String endMessage = "*" + formattedDate + "*" + "\n" + message;

                    messages.add(endMessage);
                });

        return messages;
    }

}
