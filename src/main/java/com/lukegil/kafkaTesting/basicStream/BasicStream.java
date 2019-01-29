package com.lukegil.kafkaTesting.basicStream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStreamBuilder;

public class BasicStream {
    public void createStreamThatAddsWord(final KStreamBuilder builder, final String inputStreamName, final String outputStreamName) {
        builder.stream(Serdes.String(), Serdes.String(), inputStreamName)
                .map((key, value) ->
                    new KeyValue<>(key, String.format("%s word", value))
                )
                .to(Serdes.String(), Serdes.String(), outputStreamName);
    }
}
