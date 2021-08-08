package com.example.kurbanek.manual

import com.example.kurbanek.model.TestPayload
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.Test
import org.springframework.cloud.stream.binder.kafka.properties.KafkaBinderConfigurationProperties
import org.springframework.cloud.stream.binder.kafka.streams.function.KafkaStreamsFunctionAutoConfiguration
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.messaging.support.MessageBuilder
import java.util.*


class ManualTest {

//    @Disabled
    @Test
    fun manualTest() {

        val payload = TestPayload("test value")

        kafkaTemplate().send(
            MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                .setHeader(KafkaHeaders.TOPIC, "input")
                .build()
        ).get()

    }

    private fun producerFactory(): ProducerFactory<String, TestPayload> {
        val configProps = HashMap<String, Any>()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    private fun kafkaTemplate(): KafkaTemplate<String, TestPayload> {
        return KafkaTemplate(producerFactory())
    }

}