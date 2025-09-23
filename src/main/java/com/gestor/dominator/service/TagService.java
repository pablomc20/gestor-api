package com.gestor.dominator.service;

import java.util.List;
import java.util.Optional;

import com.gestor.dominator.dto.tag.TagResponse;

public interface TagService {

    public List<TagResponse> getAllTags();

    public Optional<TagResponse> getTagById(String id);

    public Optional<TagResponse> getTagBySlug(String slug);

    public List<TagResponse> searchTagsByName(String name);

    public List<TagResponse> getTagsByType(String type);

    public List<TagResponse> getTagsByGroup(String groupId);
}