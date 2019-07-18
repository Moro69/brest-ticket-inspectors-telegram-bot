package com.moro.tibtbot.web;

import com.moro.tibtbot.model.vk.response.VkGetCommentsResponse;
import com.moro.tibtbot.model.vk.response.VkGetPostsResponse;
import com.moro.tibtbot.model.vk.response.wrapper.VkGetPostsResponseWrapper;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class VkApiClient {

    private static final String OWNER_ID_URL_PARAM_NAME = "owner_id";
    private static final String POSTS_COUNT_URL_PARAM_NAME = "count";
    private static final String ACCESS_TOKEN_URL_PARAM_NAME = "access_token";
    private static final String OFFSET_URL_PARAM_NAME = "offset";

    @Value("${vk.service.token}")
    private String serviceToken;

    @Autowired
    private RestTemplate restTemplate;

    public VkApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public VkGetPostsResponse getPosts(int ownerId, int postsCount, int offset) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder("https://api.vk.com/method/wall.get?&&extended=0&filter=others&v=5.101");
        uriBuilder.addParameter(OWNER_ID_URL_PARAM_NAME, String.valueOf(ownerId));
        uriBuilder.addParameter(POSTS_COUNT_URL_PARAM_NAME, String.valueOf(postsCount));
        uriBuilder.addParameter(OFFSET_URL_PARAM_NAME, String.valueOf(offset));
        uriBuilder.addParameter(ACCESS_TOKEN_URL_PARAM_NAME, serviceToken);

        VkGetPostsResponseWrapper responseWrapper =
                restTemplate.getForObject(uriBuilder.build(), VkGetPostsResponseWrapper.class);

        return responseWrapper.getResponse();
    }

    public VkGetCommentsResponse getComments() {
        return null;
    }
}
