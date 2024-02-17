package com.example.sosikmemberservice.config.kafka;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaJsonConsumerConfig {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> getJsonKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();

        containerFactory.setConsumerFactory(jsonConsumerFactory());
        return containerFactory;
    }

    private ConsumerFactory<String, String> jsonConsumerFactory() {

        ConsumerFactory<String, String> consumerFactory =
                new DefaultKafkaConsumerFactory<>(
                        properties(),
                        new StringDeserializer(),
                        new StringDeserializer()
                );

        return consumerFactory;
    }

    private Map<String, Object> properties() {
        Map<String, Object> props = new HashMap<>();

        String serverIp = "192.168.0.15:9092";
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverIp);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.sosikmemberservice.dto.kafka, com.example.sosikcommunityservice.dto.kafka");

        return props;

    }


}
