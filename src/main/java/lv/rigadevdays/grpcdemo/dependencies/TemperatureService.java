package lv.rigadevdays.grpcdemo.dependencies;

import lv.rigadevdays.grpcdemo.Coordinates;
import lv.rigadevdays.grpcdemo.Temperature;
import lv.rigadevdays.grpcdemo.dependencies.TemperatureServiceGrpc.TemperatureServiceImplBase;
import lv.rigadevdays.grpcdemo.providers.RandomTemperatureProvider;
import io.grpc.stub.StreamObserver;

import java.util.function.Supplier;

/**
 * Replies with randomly generated {@link Temperature}.
 */
public class TemperatureService extends TemperatureServiceImplBase {

    private final Supplier<Temperature> temperatureProvider = new RandomTemperatureProvider();

    @Override
    public void getCurrent(Coordinates request, StreamObserver<Temperature> responseObserver) {
        responseObserver.onNext(temperatureProvider.get());
        responseObserver.onCompleted();
    }

}
