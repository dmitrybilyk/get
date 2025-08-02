package com.proper.classes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("humans")
public record Human(
        @Id String id,
        String name,
        int age,
        List<String> activityIds
) {}
