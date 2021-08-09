package com.example.kurbanek.manual

import com.example.kurbanek.model.TestPayload
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.cloud.stream.binder.kafka.properties.KafkaBinderConfigurationProperties
import org.springframework.cloud.stream.binder.kafka.streams.function.KafkaStreamsFunctionAutoConfiguration
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.messaging.support.MessageBuilder
import java.util.*
import java.util.concurrent.TimeUnit


class ManualTest {

    @Disabled("manual test")
    @Test
    fun manualTest() {

        val payload = TestPayload("test value")

        kafkaTemplate().send(
            MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                .setHeader(KafkaHeaders.TOPIC, "input")
                .build()
        ).get(5, TimeUnit.SECONDS)

    }

    private fun producerFactory(): ProducerFactory<String, TestPayload> {
        return DefaultKafkaProducerFactory(mutableMapOf<String,Any>(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
        ))
    }

    private fun kafkaTemplate(): KafkaTemplate<String, TestPayload> {
        return KafkaTemplate(producerFactory())
    }

}