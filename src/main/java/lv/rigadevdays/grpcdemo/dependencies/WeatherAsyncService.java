package lv.rigadevdays.grpcdemo.dependencies;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.stub.StreamObserver;
import lv.rigadevdays.grpcdemo.*;
import lv.rigadevdays.grpcdemo.WeatherServiceGrpc.WeatherServiceImplBase;
import lv.rigadevdays.grpcdemo.dependencies.HumidityServiceGrpc.HumidityServiceFutureStub;
import lv.rigadevdays.grpcdemo.dependencies.TemperatureServiceGrpc.TemperatureServiceFutureStub;
import lv.rigadevdays.grpcdemo.dependencies.WindServiceGrpc.WindServiceFutureStub;

import java.util.List;

import static com.google.common.util.concurrent.MoreExecutors.directExecutor;

/**
 * Sends async requests to temperature, humidity and wind services,
 * combines their responses and sends it back to a client.
 */
public class WeatherAsyncService extends WeatherServiceImplBase {

    private final TemperatureServiceFutureStub tempService;
    private final HumidityServiceFutureStub humidityService;
    private final WindServiceFutureStub windService;

    WeatherAsyncService(TemperatureServiceFutureStub tempService,
                        HumidityServiceFutureStub humidityService,
                        WindServiceFutureStub windService) {
        this.tempService = tempService;
        this.humidityService = humidityService;
        this.windService = windService;
    }

    @Override
    public void getCurrent(WeatherRequest request, StreamObserver<WeatherResponse> responseObserver) {

        Coordinates coordinates = request.getCoordinates();

        ListenableFuture<List<WeatherResponse>> responsesFuture = Futures.allAsList(
                Futures.transform(humidityService.getCurrent(coordinates),
                        (Humidity humidity) -> WeatherResponse.newBuilder().setHumidity(humidity).build(),
                        directExecutor()),
                Futures.transform(tempService.getCurrent(coordinates),
                        (Temperature temp) -> WeatherResponse.newBuilder().setTemperature(temp).build(),
                        directExecutor()),
                Futures.transform(windService.getCurrent(coordinates),
                        (Wind wind) -> WeatherResponse.newBuilder().setWind(wind).build(),
                        directExecutor())
        );

        Futures.addCallback(responsesFuture, new FutureCallback<List<WeatherResponse>>() {
                    @Override
                    public void onSuccess(List<WeatherResponse> results) {
                        WeatherResponse.Builder response = WeatherResponse.newBuilder();
                        results.forEach(response::mergeFrom);
                        responseObserver.onNext(response.build());
                        responseObserver.onCompleted();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        responseObserver.onError(t);
                    }
                },
                directExecutor());

    }
}
