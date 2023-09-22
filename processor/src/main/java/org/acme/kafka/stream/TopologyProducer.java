package org.acme.kafka.stream;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import org.acme.kafka.model.Message; // Import đối tượng Message mới
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;

import io.quarkus.kafka.client.serialization.ObjectMapperSerde;

@ApplicationScoped
public class TopologyProducer {

    static final String CHAT_STATIONS_STORE = "chat-stations-store";

    static final String REQUEST_TOPIC = "request";
    static final String MESSAGES_RESULT_TOPIC = "messages-result";

    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        ObjectMapperSerde<Message> messageObjectMapperSerde = new ObjectMapperSerde<>(Message.class);

        KeyValueBytesStoreSupplier storeSupplier = Stores.persistentKeyValueStore(CHAT_STATIONS_STORE);

        KStream<String, Message> stream = builder.stream(REQUEST_TOPIC, Consumed.with(Serdes.String(), messageObjectMapperSerde));
        //add stream to storeSupplier
        stream.toTable(Materialized.<String, Message> as(storeSupplier)
                .withKeySerde(Serdes.String())
                .withValueSerde(messageObjectMapperSerde));
        stream.to(MESSAGES_RESULT_TOPIC, Produced.with(Serdes.String(), messageObjectMapperSerde));
        return builder.build();
    }
}
