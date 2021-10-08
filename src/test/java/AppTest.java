import api.ForecastServiceImpl;
import model.Forecast;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppTest {

    LocalDate tomorrow = LocalDate.now().plusDays(1);
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.dummy.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @InjectMocks
    Forecast forecast;

    @Mock
    ForecastServiceImpl service = new ForecastServiceImpl(retrofit);

    ForecastServiceImplMock serviceMock = new ForecastServiceImplMock();

    private static Forecast createForecastItem(long id, Double temperature, Integer humidity, String weatherState,
                                               Double windSpeed) {
        Forecast forecastTemp = new Forecast();
        forecastTemp.setId(id);
        forecastTemp.setTemperature(temperature);
        forecastTemp.setHumidity(humidity);
        forecastTemp.setWeatherState(weatherState);
        forecastTemp.setWindSpeed(windSpeed);
        return forecastTemp;
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAppForecast() throws IOException {

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String response = serviceMock.getMockForecast("Dubai", tomorrow);
        System.out.println("Forecast:\n" + response);
        Assertions.assertTrue(response.contains("Temp"));
        Assertions.assertTrue(response.contains("Wind"));
        Assertions.assertTrue(response.contains("Humidity"));
        Assertions.assertTrue(response.contains("°C"));
        Assertions.assertTrue(response.contains("mph"));
        Assertions.assertTrue(response.contains("%"));
    }

    @Test
    void testEmptyCityName() throws IOException {
        forecast = createForecastItem(0, 0.0, 0, "undefined", 0.0);
        when(service.getForecast("", tomorrow)).thenReturn(forecast.getWeatherState());
        String response = service.getForecast("", tomorrow);
        Assertions.assertEquals(response, forecast.getWeatherState());
    }

}