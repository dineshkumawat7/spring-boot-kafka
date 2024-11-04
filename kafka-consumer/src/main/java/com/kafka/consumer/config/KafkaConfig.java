package com.kafka.consumer.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap_servers;

    @Value("${spring.kafka.consumer.group-id}")
    private String group_id;

    @Value("${spring.kafka.properties.security.protocol}")
    private String security_protocol_config;

    @Value("${spring.kafka.properties.ssl.truststore.location}")
    private String trust_store_location;

    @Value("${spring.kafka.properties.ssl.truststore.password}")
    private String trust_store_password;

    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String jaas_config;

    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String sasl_mechanism;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers);
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, security_protocol_config);
        props.put(SaslConfigs.SASL_MECHANISM, sasl_mechanism);
        props.put(SaslConfigs.SASL_JAAS_CONFIG, jaas_config);
        props.put("ssl.truststore.location", trust_store_location);
        props.put("ssl.truststore.password", trust_store_password);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group_id);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
