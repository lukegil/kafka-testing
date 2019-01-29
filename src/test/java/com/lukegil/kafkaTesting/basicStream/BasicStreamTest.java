package com.lukegil.kafkaTesting.basicStream;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.test.ProcessorTopologyTestDriver;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicStreamTest {

    @Test
    public void checkOutput() {
        final String inputStreamName = "testInput";
        final String outputStreamName = "testOutput";

        /* Build Stream */
        final KStreamBuilder builder = new KStreamBuilder();
        new BasicStream().createStreamThatAddsWord(builder, inputStreamName, outputStreamName);

        /* Create fake config */
        final Properties props = new Properties();
        props.put("application.id", "appID");
        props.put("bootstrap.servers", Arrays.asList("server1:9200"));
        props.put("client.id", "clientID");
        final StreamsConfig config = new StreamsConfig(props);

        /* Create topology */
        final ProcessorTopologyTestDriver driver =  new ProcessorTopologyTestDriver(config, builder);

        /* Process a record through the stream */
        driver.process(inputStreamName, "key", "value", Serdes.String().serializer(), Serdes.String().serializer());

        /* Read the record from the stream */
        ProducerRecord<String, String> record1 = driver.readOutput(outputStreamName, Serdes.String().deserializer(), Serdes.String().deserializer());

        /* Assert the results */
        assertThat(record1.key(), equalTo("key"));
        assertThat(record1.value(), equalTo("value word"));
    }
}

