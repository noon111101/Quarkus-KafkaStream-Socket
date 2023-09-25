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

    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        ObjectMapperSerde<Message> messageObjectMapperSerde = new ObjectMapperSerde<>(Message.class);

        KeyValueBytesStoreSupplier storeSupplier = Stores.persistentKeyValueStore(CHAT_STATIONS_STORE);

        GlobalKTable<String, Message> tableMessage = builder.globalTable(REQUEST_TOPIC, Consumed.with(Serdes.String(), messageObjectMapperSerde));

        return builder.build();
    }
}
