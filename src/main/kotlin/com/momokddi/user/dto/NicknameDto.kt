package com.momokddi.user.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class NicknameDto(
    @JsonProperty("adjectives") val adjectives: List<String>,
    @JsonProperty("nouns") val nouns: List<String>
)