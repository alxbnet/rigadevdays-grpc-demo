package lv.rigadevdays.grpcdemo.streaming;

import lv.rigadevdays.grpcdemo.Coordinates;
import lv.rigadevdays.grpcdemo.Humidity;
import lv.rigadevdays.grpcdemo.providers.RandomHumidityProvider;
import lv.rigadevdays.grpcdemo.streaming.HumidityStreamingServiceGrpc.HumidityStreamingServiceImplBase;
import io.grpc.stub.StreamObserver;

/**
 * Periodically streams random {@link Humidity} values.
 */
public class HumidityStreamingService extends HumidityStreamingServiceImplBase {

    private final RandomValuesStreamer<Humidity> valuesStreamer =
            new RandomValuesStreamer<>(new RandomHumidityProvider());

    @Override
    public StreamObserver<Coordinates> observe(StreamObserver<Humidity> responseObserver) {
        return valuesStreamer.stream(responseObserver);
    }
}
