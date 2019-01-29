package com.lukegil.kafkaTesting.nonLambdaStream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStreamBuilder;

public class NonLambdaStream {
    public void createStreamThatAddsWord(final KStreamBuilder builder, final String inputStreamName, final String outputStreamName) {
        builder.stream(Serdes.String(), Serdes.String(), inputStreamName)
                .map(new CustomKVMapper())
                .to(Serdes.String(), Serdes.String(), outputStreamName);
    }
}
