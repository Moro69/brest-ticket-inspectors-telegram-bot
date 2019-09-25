package com.moro.brtitgbot.config;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkConfig {

    private Integer APP_ID = Integer.valueOf(System.getenv("APP_ID"));
    private String SERVICE_TOKEN = System.getenv("SERVICE_TOKEN");
    private String CLIENT_SECRET= System.getenv("CLIENT_SECRET");

    @Bean
    public VkApiClient vkApiClient() {
        TransportClient transportClient = HttpTransportClient.getInstance();

        return new VkApiClient(transportClient);
    }

    @Bean
    public ServiceActor serviceActor() {
        return new ServiceActor(APP_ID, CLIENT_SECRET, SERVICE_TOKEN);
    }
}
