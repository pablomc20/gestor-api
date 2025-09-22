package com.gestor.dominator.controller;

import com.gestor.dominator.dto.tag.TagResponse;
import com.gestor.dominator.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<TagResponse> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTagById(@PathVariable String id) {
        Optional<TagResponse> tag = tagService.getTagById(id);
        return tag.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<TagResponse> getTagBySlug(@PathVariable String slug) {
        Optional<TagResponse> tag = tagService.getTagBySlug(slug);
        return tag.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TagResponse>> searchTags(@RequestParam String name) {
        List<TagResponse> tags = tagService.searchTagsByName(name);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<TagResponse>> getTagsByType(@PathVariable String type) {
        List<TagResponse> tags = tagService.getTagsByType(type);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<TagResponse>> getTagsByGroup(@PathVariable String groupId) {
        List<TagResponse> tags = tagService.getTagsByGroup(groupId);
        return ResponseEntity.ok(tags);
    }
}