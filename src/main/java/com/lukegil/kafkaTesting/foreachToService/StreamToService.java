package com.lukegil.kafkaTesting.foreachToService;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStreamBuilder;

public class StreamToService {
    private  MessageService messageService;

    public StreamToService(final MessageService messageService) {
        this.messageService = messageService;
    }


    public void createStreamWithForEach(final KStreamBuilder builder, final String inputStreamName) {
        builder.stream(Serdes.String(), Serdes.String(), inputStreamName)
                .map((key, value) ->
                    new KeyValue<>(key, String.format("%s word", value))
                )
                .foreach((key, value) -> this.messageService.process(value));
    }
}
