package com.sensor.kafkaconsumer.infrastructure.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;
import java.util.Random;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaJsonProducer implements CommandLineRunner {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaAddress;

    private static final String TOPIC = "sensor-data";
    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    public void run(String... args) {
        // Create topic if it doesn't exist
        createTopic();

        // Set producer properties
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Generate and send sample messages
        try {
            for (int i = 0; i < 5; i++) {
                String message = createSampleMessage(i%5 + 1, i);
                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, null, message);

                // Send message synchronously
                RecordMetadata metadata = producer.send(record).get();

                System.out.printf("Sent message: %s | Partition: %d | Offset: %d%n",
                        message, metadata.partition(), metadata.offset());

                Thread.sleep(100); // Small delay between messages
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    private  void createTopic() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);

        try (AdminClient adminClient = AdminClient.create(props)) {
            // Check if topic exists
            boolean topicExists = adminClient.listTopics().names().get().contains(TOPIC);

            if (!topicExists) {
                // Create new topic
                NewTopic newTopic = new NewTopic(TOPIC, 1, (short) 1); // 1 partition, 1 replica
                adminClient.createTopics(Collections.singleton(newTopic)).all().get();
                System.out.println("Created topic: " + TOPIC);
            } else {
                System.out.println("Topic already exists: " + TOPIC);
            }
        } catch (Exception e) {
            System.err.println("Error creating topic: " + e.getMessage());
        }
    }

    private static String createSampleMessage(int id, int messageCount) {
        ObjectNode message = mapper.createObjectNode();
        message.put("id", "TK" + String.format("%02d", messageCount));
        message.put("device_id", "device_" + ((id % 3) + 1)); // device_1, device_2, or device_3
        message.put("type", "CargoSensor");

        // Create specific messages
        switch(id) {
            case 1:
                ArrayNode statChanges1 = mapper.createArrayNode();
                ObjectNode statChange1 = mapper.createObjectNode();
                statChange1.put("airport", "IST");
                statChanges1.add(statChange1);
                message.set("stat_changes", statChanges1);
                break;
            case 2:
                ArrayNode statChanges2 = mapper.createArrayNode();
                ObjectNode statChange2 = mapper.createObjectNode();
                statChange2.put("location", "29.35");
                statChanges2.add(statChange2);
                message.set("stat_changes", statChanges2);
                break;
            case 3:
                ArrayNode opChanges3 = mapper.createArrayNode();
                ObjectNode opChange3 = mapper.createObjectNode();
                opChange3.put("temperature", "25");
                opChanges3.add(opChange3);
                message.set("op_changes", opChanges3);
                break;
            case 4:
                ArrayNode statChanges4 = mapper.createArrayNode();
                ObjectNode statChange4 = mapper.createObjectNode();
                statChange4.put("location", "30.42");
                statChanges4.add(statChange4);
                message.set("stat_changes", statChanges4);
                break;
            case 5:
                ArrayNode opChanges5 = mapper.createArrayNode();
                ObjectNode opChange5 = mapper.createObjectNode();
                opChange5.put("temperature", "27.5");
                opChanges5.add(opChange5);
                message.set("op_changes", opChanges5);
                break;
        }

        return message.toString();
    }
}