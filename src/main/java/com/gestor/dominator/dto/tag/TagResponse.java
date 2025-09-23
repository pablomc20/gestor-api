package com.gestor.dominator.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {
    private String id;
    private String name;
    private String slug;
    private String type;
    private String group;
}