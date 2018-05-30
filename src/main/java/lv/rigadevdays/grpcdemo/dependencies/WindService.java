package lv.rigadevdays.grpcdemo.dependencies;

import lv.rigadevdays.grpcdemo.Coordinates;
import lv.rigadevdays.grpcdemo.Wind;
import lv.rigadevdays.grpcdemo.dependencies.WindServiceGrpc.WindServiceImplBase;
import lv.rigadevdays.grpcdemo.providers.RandomWindProvider;
import io.grpc.stub.StreamObserver;

import java.util.function.Supplier;

/**
 * Replies with randomly generated {@link Wind}.
 */
public class WindService extends WindServiceImplBase {

    private final Supplier<Wind> windProvider = new RandomWindProvider();

    @Override
    public void getCurrent(Coordinates request, StreamObserver<Wind> responseObserver) {
        responseObserver.onNext(windProvider.get());
        responseObserver.onCompleted();
    }
}