package com.example.sosikmemberservice.service;

import com.example.sosikmemberservice.dto.kafka.ResponseNotification;
import com.example.sosikmemberservice.exception.ApplicationException;
import com.example.sosikmemberservice.exception.ErrorCode;
import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.model.entity.NotificationEntity;
import com.example.sosikmemberservice.repository.EmitterRepository;
import com.example.sosikmemberservice.repository.MemberRepository;
import com.example.sosikmemberservice.repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service

public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final ObjectMapper objectMapper;

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @KafkaListener(
            topics = "notification",
            groupId = "notification",
            containerFactory = "getJsonKafkaListenerContainerFactory"
    )
    public void listen(String response) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            Long memberId = jsonNode.get("memberId").asLong();
            Long postId = jsonNode.get("postId").asLong();
            String content = jsonNode.get("content").asText();
            saveNotification(memberId, postId, content);

            sendToClient(memberId, content);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public SseEmitter subscribe(Long memberId) {
        SseEmitter emitter = createEmitter(memberId);
        List<ResponseNotification> collect = notificationRepository.findAll()
                .stream()
                .map(ResponseNotification::create)
                .toList();


        String message = null;
        try {
            message = objectMapper.writeValueAsString(collect);
        } catch (JsonProcessingException e) {
            throw new ApplicationException(ErrorCode.JSON_PARSING_ERROR);
        }
        sendToClient(memberId,  collect);

        return emitter;
    }

    public void deleteNotification(Long notificationId){
        emitterRepository.deleteById(notificationId);
    }

    public void notify(Long memberId, Object event) {
        sendToClient(memberId, event);
    }

    private void sendToClient(Long id, Object data) {
        SseEmitter emitter = emitterRepository.get(id);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(id))
                        .name("sse")
                        .data(data));
            } catch (IOException exception) {
                emitterRepository.deleteById(id);
                emitter.completeWithError(exception);
            }
        }
    }

    private void saveNotification(Long id, Long postId, Object data) {
        MemberEntity member = memberRepository.findById(id).orElseThrow(() -> {
            return new ApplicationException(ErrorCode.USER_NOT_FOUND);
        });

        NotificationEntity notificationEntity = NotificationEntity.builder()
                .isRead(false)
                .message(data.toString())
                .receiverId(id)

                .nickname(member.getNickname())
                .redirectingPostId(postId)

                .build();
        notificationRepository.save(notificationEntity);
    }
    private SseEmitter createEmitter(Long id) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(id, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        return emitter;
    }

}
