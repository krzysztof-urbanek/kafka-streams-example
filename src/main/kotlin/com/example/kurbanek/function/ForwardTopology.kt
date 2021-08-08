package com.example.kurbanek.function

import com.example.kurbanek.model.TestPayload
import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Function

@Configuration
class ForwardTopology {

    @Bean
    fun forward() = Function<KStream<String, TestPayload>,KStream<String, TestPayload>> {
        stream -> stream
    }
}