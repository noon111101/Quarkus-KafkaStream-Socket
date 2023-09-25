package org.acme.kafka.stream;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.acme.kafka.model.Message;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import java.util.Map;

@ApplicationScoped
public class InteractiveQueries {

    @Inject
    KafkaStreams streams;

    public Map<String,Message> getMessageResult() {
        Map<String,Message>  result = (Map<String, Message>) messageReadOnlyKeyValueStore().all();
        return result;
    }

    private ReadOnlyKeyValueStore<String, Message> messageReadOnlyKeyValueStore() {
        while (true) {
            try {
                String storeName = "tableMessage";
                StoreQueryParameters<ReadOnlyKeyValueStore<String, Message>> queryParameters =
                        StoreQueryParameters.fromNameAndType(TopologyProducer.CHAT_STATIONS_STORE, QueryableStoreTypes.keyValueStore());
                return streams.store(queryParameters);
            } catch (InvalidStateStoreException e) {
                // ignore, store not ready yet
            }
        }
    }
}