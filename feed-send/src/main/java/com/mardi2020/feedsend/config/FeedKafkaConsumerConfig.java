package com.mardi2020.feedsend.config;

import com.mardi2020.feedcommon.posting.message.NewPosting;
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
public class FeedKafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, NewPosting> consumerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "feed7");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        return new DefaultKafkaConsumerFactory<>(properties,
                new StringDeserializer(),
                new JsonDeserializer<>(NewPosting.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NewPosting> feedKafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, NewPosting> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
