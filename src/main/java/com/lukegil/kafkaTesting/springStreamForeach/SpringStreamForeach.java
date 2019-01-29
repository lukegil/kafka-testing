package com.lukegil.kafkaTesting.springStreamForeach;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class SpringStreamForeach {

    @Autowired
    private ForeachService foreachService;

    @Bean
    public KStream<String, String> foreachStream(final KStreamBuilder streamBuilder) {
        final KStream stream = this.kStreamInner(streamBuilder);
        stream.foreach((key, value) -> this.foreachService.process((String) value));
        return stream;
    }

    public KStream<String, String> kStreamInner(final KStreamBuilder streamBuilder) {
        return streamBuilder.stream(Serdes.String(), Serdes.String(), "foreachTopicInput")
                .map((key, value) -> new KeyValue<>(key, String.format("%s word", value)));
    }
}
