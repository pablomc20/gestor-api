package com.gestor.dominator.dto.tag;

import lombok.Data;

@Data
public class TagRequest {
    private String name;
    private String slug;
    private String type;
    private String group;
}