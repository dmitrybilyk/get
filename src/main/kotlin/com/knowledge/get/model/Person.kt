
package com.knowledge.get.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("people")
data class Person(
    @Id val id: String? = null,
    var name: String = "",
    var email:  String = "",
    var age: Int = 20
)
