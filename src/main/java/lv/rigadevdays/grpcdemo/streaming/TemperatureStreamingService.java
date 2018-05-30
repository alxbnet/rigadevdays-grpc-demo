package lv.rigadevdays.grpcdemo.streaming;

import lv.rigadevdays.grpcdemo.Coordinates;
import lv.rigadevdays.grpcdemo.Temperature;
import lv.rigadevdays.grpcdemo.providers.RandomTemperatureProvider;
import lv.rigadevdays.grpcdemo.streaming.TemperatureStreamingServiceGrpc.TemperatureStreamingServiceImplBase;
import io.grpc.stub.StreamObserver;

/**
 * Periodically streams random {@link Temperature} values.
 */
public class TemperatureStreamingService extends TemperatureStreamingServiceImplBase {

    private final RandomValuesStreamer<Temperature> valuesStreamer =
            new RandomValuesStreamer<>(new RandomTemperatureProvider());

    @Override
    public StreamObserver<Coordinates> observe(StreamObserver<Temperature> responseObserver) {
        return valuesStreamer.stream(responseObserver);
    }
}
