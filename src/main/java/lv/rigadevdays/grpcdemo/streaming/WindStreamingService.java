package lv.rigadevdays.grpcdemo.streaming;

import lv.rigadevdays.grpcdemo.Coordinates;
import lv.rigadevdays.grpcdemo.Wind;
import lv.rigadevdays.grpcdemo.providers.RandomWindProvider;
import lv.rigadevdays.grpcdemo.streaming.WindStreamingServiceGrpc.WindStreamingServiceImplBase;
import io.grpc.stub.StreamObserver;

/**
 * Periodically streams random {@link Wind} values.
 */
public class WindStreamingService extends WindStreamingServiceImplBase {

    private final RandomValuesStreamer<Wind> valuesStreamer = new RandomValuesStreamer<>(new RandomWindProvider());

    @Override
    public StreamObserver<Coordinates> observe(StreamObserver<Wind> responseObserver) {
        return valuesStreamer.stream(responseObserver);
    }

}
