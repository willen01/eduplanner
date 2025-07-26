package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.SaveTopicDto;
import com.dev.willen.eduplanner.dto.TopicResponse;
import com.dev.willen.eduplanner.entities.Topic;
import com.dev.willen.eduplanner.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topics")
@Tag(name = "Topic", description = "Endpoints for managing topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/save")
    @Operation(
            summary = "Save topic",
            tags = {"Topic"},
            description = "Endpoint for save a new topic",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<Void> registerTopic(@RequestBody SaveTopicDto topicRequest,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        topicService.saveTopic(topicRequest, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/findById/{topicId}")
    @Operation(
            summary = "Get topic",
            tags = {"Topic"},
            description = "Endpoint for get one topic by id",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<TopicResponse> getById(@PathVariable(value = "topicId") int topicId) {
        Topic topic = topicService.getTopicById(topicId);
        TopicResponse response = new TopicResponse(topic.getId(), topic.getName(), topic.getSubject().getName(),
                topic.getCreatedAt());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get topics",
            tags = {"Topic"},
            description = "Endpoint all topics",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<List<TopicResponse>> getAllTopics(@AuthenticationPrincipal UserDetails userDetails) {
        List<TopicResponse> response = topicService.getAllTopics(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/remove/{topicId}")
    @Operation(
            summary = "Remove topic",
            tags = {"Topic"},
            description = "Endpoint for remove topic",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<Void> removeTopic(@PathVariable(name = "topicId") int topicId) {
        topicService.removeTopic(topicId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
