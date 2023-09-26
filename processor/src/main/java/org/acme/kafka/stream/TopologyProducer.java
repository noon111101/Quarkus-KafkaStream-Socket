package org.acme.kafka.stream;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
@ApplicationScoped
public class TopologyProducer {

    static final String CHAT_STATIONS_STORE = "chat-stations-store";
    static final String REQUEST_TOPIC = "request";
    static final String RESULT_TOPIC = "result";



    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        KeyValueBytesStoreSupplier storeSupplier = Stores.persistentKeyValueStore(CHAT_STATIONS_STORE);

        builder.stream(REQUEST_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
                .groupBy((k, v) -> k, Grouped.with(Serdes.String(), Serdes.String()))
                .aggregate(
                        String::new,
                        (k, v, a) -> {
                            return v;
                        },
                        Materialized.<String, String> as(storeSupplier)
                                .withKeySerde(Serdes.String())
                                .withValueSerde(Serdes.String()))
                .toStream()
                .to(RESULT_TOPIC, Produced.with(Serdes.String(), Serdes.String()));


        return builder.build();
    }
}
