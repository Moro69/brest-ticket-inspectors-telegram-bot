package com.moro.brtitgbot.services;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.enums.WallFilter;
import com.vk.api.sdk.objects.wall.Wallpost;
import com.vk.api.sdk.objects.wall.WallpostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VkWallPostProvider {

    private static final Integer DEFAULT_POSTS_COUNT = 10;

    @Value("${vk.group.id}")
    private Integer OWNER_ID;

    private final VkApiClient vkApiClient;
    private final ServiceActor serviceActor;

    private Integer lastPostsCount;

    @Autowired
    public VkWallPostProvider(final VkApiClient vkApiClient, ServiceActor serviceActor) {
        this.vkApiClient = vkApiClient;
        this.serviceActor = serviceActor;
    }

    public List<String> getLastWallPostsMessages() throws ClientException, ApiException {
        List<WallpostFull> messages = new ArrayList<>();

        int postsCount = getTotalPostsCount();

        if (Objects.nonNull(lastPostsCount)) {

            if (!lastPostsCount.equals(postsCount)) {
                messages = getWallPosts(postsCount - lastPostsCount);
                lastPostsCount = postsCount;
            }
        } else {
            messages = getWallPosts(DEFAULT_POSTS_COUNT);
            lastPostsCount = postsCount;
        }

        return messages.stream()
                .sorted(Comparator.comparing(Wallpost::getDate))
                .map(this::mapToFormattedString)
                .collect(Collectors.toList());
    }

    private String mapToFormattedString(WallpostFull wallpostFull) {
        OffsetDateTime postDate =
                OffsetDateTime.ofInstant(Instant.ofEpochSecond(wallpostFull.getDate()),
                        ZoneOffset.of("+03:00"));

        String message = wallpostFull.getText();

        String formattedMessage = "*"
                + postDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                + "*" + "\n" + message;

        return formattedMessage;
    }

    private List<WallpostFull> getWallPosts(Integer count) throws ApiException, ClientException {
        GetResponse getResponse = vkApiClient.wall()
                .get(serviceActor)
                .ownerId(OWNER_ID)
                .count(count)
                .filter(WallFilter.OTHERS)
                .execute();

        return getResponse.getItems();
    }

    private Integer getTotalPostsCount() throws ClientException, ApiException {
        return vkApiClient.wall()
                .get(serviceActor)
                .ownerId(OWNER_ID)
                .filter(WallFilter.OTHERS)
                .execute()
                .getCount();
    }
}
