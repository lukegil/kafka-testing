package com.lukegil.kafkaTesting.basicSpringStream;

import com.lukegil.kafkaTesting.controllers.MainController;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.test.ProcessorTopologyTestDriver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { MainController.class})
public class BasicSpringStreamTest {

    @Autowired
    private BasicSpringStream basicSpringStream;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private KStreamBuilder defaultKStreamBuilder;

    @Autowired
    private KStream kStream;

    @Autowired
    private StreamsConfig defaultKafkaStreamsConfig;

    @Test
    public void kStreamsConfig() {
        final StreamsConfig streamsConfig = this.basicSpringStream.defaultKafkaStreamsConfig();
        assertThat(streamsConfig.getString("application.id"), equalTo("appID"));
        assertThat(streamsConfig.getList("bootstrap.servers"), equalTo(Arrays.asList("127.0.0.1:9092")));
        assertThat(streamsConfig.getString("client.id"), equalTo("clientID"));
    }

    @Test
    public void kStreamTest() {
        /* Build Stream */
        final KStreamBuilder builder = new KStreamBuilder();
        final KStream stream = this.basicSpringStream.kStreamInner(builder);

        /* Create fake config */
        final Properties props = new Properties();
        props.put("application.id", "appID");
        props.put("bootstrap.servers", Arrays.asList("server1:9200"));
        props.put("client.id", "clientID");
        final StreamsConfig config = new StreamsConfig(props);

        /* Stream to something, so we can read output */
        stream.to(Serdes.String(), Serdes.String(),"testOutput");


        /* Create topology */
        final ProcessorTopologyTestDriver driver =  new ProcessorTopologyTestDriver(config, builder);

        /* Process a record through the stream */
        driver.process("inputTopic", "key", "value", Serdes.String().serializer(), Serdes.String().serializer());

        /* Read the record from the stream */
        ProducerRecord<String, String> record1 = driver.readOutput("testOutput", Serdes.String().deserializer(), Serdes.String().deserializer());

        /* Assert the results */
        assertThat(record1.key(), equalTo("key"));
        assertThat(record1.value(), equalTo("value word"));
    }

    @Test
    public void kStreamTestBean() {
        /* Create topology */
        final ProcessorTopologyTestDriver driver =  new ProcessorTopologyTestDriver(this.defaultKafkaStreamsConfig, this.defaultKStreamBuilder);

        /* Process a record through the stream */
        driver.process("inputTopic", "key", "value", Serdes.String().serializer(), Serdes.String().serializer());

        /* Read the record from the stream */
        ProducerRecord<String, String> record1 = driver.readOutput("testOutput", Serdes.String().deserializer(), Serdes.String().deserializer());

        /* Assert the results */
        assertThat(record1.key(), equalTo("key"));
        assertThat(record1.value(), equalTo("value word"));
    }
}
