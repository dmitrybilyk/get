
package com.knowledge.get.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("people")
data class Person(
    @Id val id: String? = null,
    var name: String = "",
//    @field:JsonSerialize(using = MaskingSerializer::class)
    var email:  String = "",
    var resource: String = "some resource",
//    @field:JsonSerialize(using = MaskingSerializer::class)
    var age: Int = 20,

    val child: Child?,
    val children: List<Child> = listOf(Child("fffddd"), Child("bbbb"))

)

data class Child(val name: String = "ddd", val description: String = "descr")