package com.lukegil.kafkaTesting.basicSpringStream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class BasicSpringStream {

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public StreamsConfig defaultKafkaStreamsConfig() {
        final Map<String, Object> props = new LinkedHashMap<>();
        props.put("application.id", "appID");
        props.put("bootstrap.servers", Arrays.asList("127.0.0.1:9092"));
        props.put("client.id", "clientID");
        return new StreamsConfig(props);
    }

    @Bean
    public KStream<String, String> kStream(final KStreamBuilder streamBuilder) {
        final KStream stream = this.kStreamInner(streamBuilder);
        stream.to(Serdes.String(), Serdes.String(), "testOutput");
        return stream;
    }

    public KStream<String, String> kStreamInner(final KStreamBuilder streamBuilder) {
        return streamBuilder.stream(Serdes.String(), Serdes.String(), "inputTopic")
                .map((key, value) -> new KeyValue<>(key, String.format("%s word", value)));
    }
}
