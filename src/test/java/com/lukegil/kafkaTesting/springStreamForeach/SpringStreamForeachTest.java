package com.lukegil.kafkaTesting.springStreamForeach;

import com.lukegil.kafkaTesting.basicSpringStream.BasicSpringStream;
import com.lukegil.kafkaTesting.controllers.MainController;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.test.ProcessorTopologyTestDriver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { MainController.class})
public class SpringStreamForeachTest {

    @Autowired
    @InjectMocks
    private SpringStreamForeach springStreamForeach;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private KStreamBuilder defaultKStreamBuilder;

    @Autowired
    private KStream foreachStream;

    @Autowired
    private StreamsConfig defaultKafkaStreamsConfig;

    @Mock
    private ForeachService foreachService;

    @Test
    public void kStreamTestBean() {
        doNothing().when(this.foreachService).process("value word");

        /* Create topology */
        final ProcessorTopologyTestDriver driver =  new ProcessorTopologyTestDriver(this.defaultKafkaStreamsConfig, this.defaultKStreamBuilder);

        /* Process a record through the stream */
        driver.process("foreachTopicInput", "key", "value", Serdes.String().serializer(), Serdes.String().serializer());

        verify(this.foreachService).process("value word");
    }
}
