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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VkService {

    @Autowired
    private VkApiClient vkRestClient;

    private Integer lastPostsCount;

    public List<String> getVkPostsMessages() throws IOException, URISyntaxException {
        VkGetPostsResponse response = null;
        List<String> messages = new ArrayList<>();

        if (Objects.nonNull(lastPostsCount)) {
            int postsCount = vkRestClient.getPosts(-72869598, 0, 0).getCount();

            if (!lastPostsCount.equals(postsCount)) {
                response = vkRestClient.getPosts(-72869598, postsCount - lastPostsCount, 0);
                lastPostsCount = response.getCount();
            }
        } else {
            response = vkRestClient.getPosts(-72869598, 10, 0);
            lastPostsCount = response.getCount();
        }

        Optional.ofNullable(response)
                .map(VkGetPostsResponse::getItems)
                .orElse(Collections.emptyList())
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

    private int getIndexForPostId(List<VkPost> posts, int lastPostId) {
        int index = 0;
        for (VkPost vkPost : posts) {
            if (vkPost.getId().equals(lastPostId)) {
                index = posts.indexOf(vkPost);
            }
        }

        return index;
    }

    private boolean isPostsIsUnique(List<VkPost> posts, int lastPostId) {
        return posts.stream()
                .map(VkPost::getId)
                .noneMatch(id -> lastPostId == id);
    }


}
