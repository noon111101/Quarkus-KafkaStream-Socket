package org.acme.kafka.stream;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.kafka.model.Message;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import java.util.HashMap;
import java.util.Map;

@Path("/ktable-data")

@ApplicationScoped
public class InteractiveQueries {

    @Inject
    KafkaStreams streams;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Message> getKTableData() {
        Map<String, Message> result = new HashMap<>();
        System.out.println("TRACE_1 " + streams);
        StoreQueryParameters<ReadOnlyKeyValueStore<String, Message>> queryParameters =
                StoreQueryParameters.fromNameAndType("my-ktable-store", QueryableStoreTypes.keyValueStore());
        System.out.println("TRACE_2 " + queryParameters);
// Sử dụng phương thức store() để lấy cửa hàng
        ReadOnlyKeyValueStore<String, Message> kTableStore = streams.store(queryParameters);
        try {
            kTableStore.all().forEachRemaining(kv -> result.put(kv.key, kv.value));
        } catch (InvalidStateStoreException ignored) {
            // store not ready yet
        }
        System.out.println("KTable data: " + result);
        return result;
    }
}