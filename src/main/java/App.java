import api.ForecastServiceImpl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        try {
            Scanner myObj = new Scanner(System.in);
            boolean run = true;
            System.out.print("\n");
            System.out.println("forecast:");
            String str= myObj.nextLine();
            while (run) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://www.metaweather.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                LocalDate tomorrow = LocalDate.now().plusDays(1);
                ForecastServiceImpl service = new ForecastServiceImpl(retrofit);
                System.out.println(service.getForecast(str, tomorrow));
                run = false;
            }
            if (str.length() < 1) {
                    throw new IllegalArgumentException("Pass city name as an argument");
            } else {
                System.exit(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }
}
