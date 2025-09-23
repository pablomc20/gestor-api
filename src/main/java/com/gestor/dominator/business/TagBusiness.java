package com.gestor.dominator.business;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gestor.dominator.components.ObjectIdConverter;
import com.gestor.dominator.dto.tag.TagResponse;
import com.gestor.dominator.model.Tag;
import com.gestor.dominator.repository.TagRepository;
import com.gestor.dominator.service.TagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagBusiness implements TagService {
    
    private final TagRepository tagRepository;

    private final ObjectIdConverter objectIdConverter;

    @Override
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TagResponse> getTagById(String id) {
        return tagRepository.findById(objectIdConverter.stringToObjectId(id))
                .map(this::convertToResponse);
    }

    @Override
    public Optional<TagResponse> getTagBySlug(String slug) {
        return tagRepository.findBySlug(slug)
                .map(this::convertToResponse);
    }

    @Override
    public List<TagResponse> searchTagsByName(String name) {
        return tagRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TagResponse> getTagsByType(String type) {
        return tagRepository.findByType(type).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TagResponse> getTagsByGroup(String groupId) {
        return tagRepository.findByGroup(groupId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private TagResponse convertToResponse(Tag tag) {
        return new TagResponse(
                tag.getId(),
                tag.getName(),
                tag.getSlug(),
                tag.getType(),
                tag.getGroup()
        );
    }
}
