package com.example.sosikmemberservice.dto.kafka;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class CommentInfoForKafka implements Serializable{
    private Long memberId;
    private String content;
}
