package com.proper.classes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "activities")
public record Activity(
        @Id String id,
        String name,
        double baseCost
) {}
