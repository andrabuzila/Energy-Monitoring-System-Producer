package com.example.DS2022_30641_Buzila_Andra_Assignment_2_Backend;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class MessageProducer {
    private final String QUEUE_NAME = "hello";
    public MessageProducer(){

    }

    public void produceMessage() throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        String file = "sensor.csv";
        String line;
        BufferedReader br =
                new BufferedReader(new FileReader(file));
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://hohbspor:qKGNgiD2khvKWLaNYwGsXX_4KBjFA5Rr@goose.rmq2.cloudamqp.com/hohbspor");
        Properties prop = new Properties();
        while(true){
            String sensorIdFilePath = "D:\\AN 4\\ProjectSd\\DS2022_30641_Buzila_Andra_Assignment_1_Backend\\configuration.properties";

            Properties properties = new Properties();
            properties.load(new FileInputStream((sensorIdFilePath)));
            if(properties.getProperty("device_id") != null){
                prop.load(new FileInputStream((sensorIdFilePath)));
                break;
            }
            Thread.sleep(20000);
        }
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            while((line = br.readLine()) != null){
                JSONObject message = new JSONObject();
                message.put("timestamp", LocalDateTime.now());
                message.put("device_id", prop.getProperty("device_id"));
                message.put("measurement_value", line);
                String messageToBeSent = message.toString();
                channel.basicPublish("", QUEUE_NAME, null, messageToBeSent.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
                Thread.sleep(10000);
            }
        } catch (InterruptedException | TimeoutException | JSONException e) {
            e.printStackTrace();
        }
    }
}
