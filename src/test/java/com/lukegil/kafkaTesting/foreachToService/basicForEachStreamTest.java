package com.lukegil.kafkaTesting.foreachToService;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.test.ProcessorTopologyTestDriver;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Properties;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class basicForEachStreamTest {

    @Test
    public void checkServiceCall() {
        /* Create a Mock service */
        final MessageService messageServiceMock = Mockito.mock(MessageService.class);
        doNothing().when(messageServiceMock).process("value word");

        final String inputStreamName = "testInput";

        /* Build Stream */
        final KStreamBuilder builder = new KStreamBuilder();
        final StreamToService streamToService = new StreamToService(messageServiceMock);
        streamToService.createStreamWithForEach(builder, inputStreamName);

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

        /* Assert the service is called */
        verify(messageServiceMock).process("value word");
    }
}
