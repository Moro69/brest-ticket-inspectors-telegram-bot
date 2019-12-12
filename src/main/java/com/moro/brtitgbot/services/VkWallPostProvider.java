package com.moro.brtitgbot.services;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.enums.WallFilter;
import com.vk.api.sdk.objects.wall.Wallpost;
import com.vk.api.sdk.objects.wall.WallpostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log4j2
public class VkWallPostProvider {

    private static final Integer DEFAULT_POSTS_COUNT = 10;

    @Value("${vk.group.id}")
    private Integer ownerId;

    private final VkApiClient vkApiClient;
    private final ServiceActor serviceActor;

    private Integer lastPostsCount;
    private List<WallpostFull> wallposts;

    @Autowired
    public VkWallPostProvider(final VkApiClient vkApiClient,
                              final ServiceActor serviceActor) {
        this.vkApiClient = vkApiClient;
        this.serviceActor = serviceActor;
    }

    public List<String> getLastWallPostsMessages() throws ClientException, ApiException {
        log.info("getLastWallPostsMessages: ");

        int totalPostsCount = getTotalPostsCount();

        log.info("total posts count = {}", totalPostsCount);

        if (Objects.nonNull(lastPostsCount)) {
            log.info("last posts count = {}", lastPostsCount);

            if (!lastPostsCount.equals(totalPostsCount)) {
                wallposts = getWallPosts(totalPostsCount - lastPostsCount);
                lastPostsCount = totalPostsCount;
            } else {
                return Collections.emptyList();
            }
        } else {
            wallposts = getWallPosts(DEFAULT_POSTS_COUNT);
            lastPostsCount = totalPostsCount;
        }

        return wallposts.stream()
                .sorted(Comparator.comparing(Wallpost::getDate))
                .map(this::mapToFormattedString)
                .collect(Collectors.toList());
    }

    private String mapToFormattedString(WallpostFull wallpostFull) {
        OffsetDateTime postDate =
                OffsetDateTime.ofInstant(Instant.ofEpochSecond(wallpostFull.getDate()),
                        ZoneOffset.of("+03:00"));

        String message = wallpostFull.getText();

        return "*"
                + postDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                + "*" + "\n" + message;
    }

    private List<WallpostFull> getWallPosts(Integer count) throws ApiException, ClientException {
        log.info("getWallPosts: count = {}", count);

        GetResponse getResponse = vkApiClient.wall()
                .get(serviceActor)
                .ownerId(ownerId)
                .count(count)
                .filter(WallFilter.OTHERS)
                .execute();

        return getResponse.getItems();
    }

    private Integer getTotalPostsCount() throws ClientException, ApiException {
        return vkApiClient.wall()
                .get(serviceActor)
                .ownerId(ownerId)
                .count(0)
                .filter(WallFilter.OTHERS)
                .execute()
                .getCount();
    }
}
