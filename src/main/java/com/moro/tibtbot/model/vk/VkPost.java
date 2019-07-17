package com.moro.tibtbot.model.vk;

import lombok.Data;

import java.io.Serializable;

@Data
public class VkPost implements Serializable {

    private Integer id;
    private Long date;
    private String text;
    private Comments comments;

    @Data
    private static class Comments {
        private Integer count;
    }
}
