package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.SaveTopicDto;
import com.dev.willen.eduplanner.dto.TopicResponse;
import com.dev.willen.eduplanner.entities.Topic;
import com.dev.willen.eduplanner.services.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> registerTopic(@RequestBody SaveTopicDto topicRequest,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        topicService.saveTopic(topicRequest, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/findById/{topicId}")
    public ResponseEntity<TopicResponse> getById(@PathVariable(value = "topicId") int topicId) {
        Topic topic = topicService.getTopicById(topicId);
        TopicResponse response = new TopicResponse(topic.getName(), topic.getUpdatedAt(), topic.getSubject().getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
