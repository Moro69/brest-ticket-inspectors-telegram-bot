package com.moro.tibtbot.model.vk.response;

import com.moro.tibtbot.model.vk.VkPost;
import com.moro.tibtbot.model.vk.VkResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VkGetPostsResponse implements Serializable, VkResponse {

    private Integer count;
    private List<VkPost> items;
}
