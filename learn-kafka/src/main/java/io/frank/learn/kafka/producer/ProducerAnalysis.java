package io.frank.learn.kafka.producer;

import io.frank.learn.kafka.config.Consts;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static io.frank.learn.kafka.config.Consts.BROKER_LIST;

/**
 * @author jinjunliang
 **/
public class ProducerAnalysis {


    public static Properties initConfig() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");
        props.put(ProducerConfig.RETRIES_CONFIG, 10);
        return props;
    }

    public static void main(String[] args) {
        Properties props = initConfig();
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>(Consts.TOPIC, "Hello, Kafka!");
        CountDownLatch downLatch = new CountDownLatch(100);
        try {
            for (int i = 0; i < 100; i++) {
                producer.send(record, (meta, e) -> {
                    downLatch.countDown();
                    if (Objects.nonNull(e)) {
                        e.printStackTrace();
                    } else {
                        System.out.println(record.value() + " 发送成功");
                    }
                });
            }
            downLatch.await(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("发生完成");
    }
}
