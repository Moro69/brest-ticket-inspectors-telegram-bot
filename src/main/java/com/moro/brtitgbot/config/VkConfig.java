package com.moro.brtitgbot.config;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkConfig {

    @Value("${vk.app.id}")
    private Integer APP_ID;
    @Value("${vk.service.token}")
    private String SERVICE_TOKEN;

    @Bean
    public VkApiClient vkApiClient() {

        TransportClient transportClient = HttpTransportClient.getInstance();

        return new VkApiClient(transportClient);
    }

    @Bean
    public ServiceActor serviceActor() {
        return new ServiceActor(APP_ID, SERVICE_TOKEN);
    }
}