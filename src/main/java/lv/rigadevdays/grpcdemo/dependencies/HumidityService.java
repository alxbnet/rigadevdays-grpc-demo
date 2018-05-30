package lv.rigadevdays.grpcdemo.dependencies;

import lv.rigadevdays.grpcdemo.Coordinates;
import lv.rigadevdays.grpcdemo.Humidity;
import lv.rigadevdays.grpcdemo.dependencies.HumidityServiceGrpc.HumidityServiceImplBase;
import lv.rigadevdays.grpcdemo.providers.RandomHumidityProvider;
import io.grpc.stub.StreamObserver;

import java.util.function.Supplier;

/**
 * Replies with randomly generated {@link Humidity}.
 */
public class HumidityService extends HumidityServiceImplBase {

    private final Supplier<Humidity> humidityProvider = new RandomHumidityProvider();

    @Override
    public void getCurrent(Coordinates request, StreamObserver<Humidity> responseObserver) {
        responseObserver.onNext(humidityProvider.get());
        responseObserver.onCompleted();
    }

}
