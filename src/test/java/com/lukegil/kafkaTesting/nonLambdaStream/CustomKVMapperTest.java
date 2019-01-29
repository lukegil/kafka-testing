package com.lukegil.kafkaTesting.nonLambdaStream;

import org.apache.kafka.streams.KeyValue;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CustomKVMapperTest {

    @Test
    public void checkMap() {
        final CustomKVMapper kvMapper = new CustomKVMapper();
        final KeyValue<String, String> kv = kvMapper.apply("key", "value");
        assertThat(kv.value, equalTo("value word"));
    }
}
