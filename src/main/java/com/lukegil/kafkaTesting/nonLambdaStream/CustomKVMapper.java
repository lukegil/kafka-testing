package com.lukegil.kafkaTesting.nonLambdaStream;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;

public class CustomKVMapper implements KeyValueMapper<String, String, KeyValue<String, String>> {
    public KeyValue<String, String> apply(final String key, final String value) {
        return new KeyValue<>(key, String.format("%s word", value));
    }
}
