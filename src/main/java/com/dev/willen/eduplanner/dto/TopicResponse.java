package com.dev.willen.eduplanner.dto;

import java.util.Date;

public record TopicResponse(int id, String name, String subject, Date createdAt) {
}
