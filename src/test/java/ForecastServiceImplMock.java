import api.ForecastService;
import api.ForecastServiceImpl;
import co.infinum.retromock.Retromock;
import co.infinum.retromock.meta.Mock;
import co.infinum.retromock.meta.MockResponse;
import model.City;
import model.Forecast;
import org.junit.jupiter.api.BeforeEach;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import util.PathDate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ForecastServiceImplMock {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.dummy.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Retromock retromock = new Retromock.Builder()
            .retrofit(retrofit)
            .build();

    public String getMockForecast(String cityName, LocalDate date) throws IOException {
        PathDate pathDate = new PathDate(date);
        ForecastServiceImplMock.MockCity serviceCity = retromock.create(ForecastServiceImplMock.MockCity.class);
        ForecastServiceImplMock.MockForecast serviceForeCast = retromock.create(ForecastServiceImplMock.MockForecast.class);
        System.out.println(serviceForeCast.getForecast().execute().body().toString());
        return String.format("Weather on (%s) in %s:\n%s", pathDate, serviceCity.findCityByName().execute().body().getTitle(),serviceForeCast.getForecast().execute().body().toString());
    }

    public interface MockCity {
        @Mock
        @MockResponse(body = "{\"title\":\"Dubai\",\"woeid\":\"35784\"}")
        @GET("/")
//        Call<List<City>> findCityByName();
        Call<City> findCityByName();
    }

    public interface MockForecast {
        @Mock
        @MockResponse(body = "{\"weather_state_name\":\"Clear\",\"wind_speed\":\"78\",\"the_temp\":\"45\",\"humidity\":\"50\" }")
        @GET("/")
        Call<Forecast> getForecast();
    }
}

