package com.huasky;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogsTopic {

    private static final String EXCHANGE_NAME = "freeswitch_topic";
    private static final String QUEUE_NAME = "freeswitch_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("freeswitch_host");
        factory.setHost("120.27.249.129");
        factory.setUsername("freeswitch_client");
        factory.setPassword("lFnQwWTDPx77ryF8");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "freeswitch_event");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
