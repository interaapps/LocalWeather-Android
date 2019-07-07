package de.interaapps.localweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jetbrains.annotations.Nullable;

import de.interaapps.localweather.utils.LocationFailedEnum;

public class MainActivity extends AppCompatActivity {

    private LocalWeather localWeather;
    private Weather weather;
    private Button button;
    private AppCompatTextView textView;
    private boolean weatherReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localWeather = new LocalWeather(this,
                "5f7182ae4a0cca0320a98f96e87f1f11",
                true,
                true,
                new LocalWeather.Callbacks() {
                    @Override
                    public void onLocationFailure(LocationFailedEnum locationFailedEnum) {
                        Log.e("LOCATION-ERROR", locationFailedEnum.toString());
                    }

                    @Override
                    public void onWeatherFailure(Throwable throwable) {
                        Log.e("WEATHER-ERROR", throwable.getMessage());
                    }

                    @Override
                    public void onLocationSuccess() {
                        localWeather.listenWeather();
                        Log.d("SUCCESS", "LOCATION");
                    }

                    @Override
                    public void onWeatherSuccess() {
                        weather = localWeather.getWeather();
                        weatherReady = true;
                        textView.setText("Weather is ready");
                        Log.d("SUCCESS", "WEATHER");
                    }
                });

        localWeather.setLocation();

        initialize();
        initializeLogic();
    }

    private void initialize() {
        button = findViewById(R.id.button);
        textView = findViewById(R.id.text);
    }

    private void initializeLogic() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weatherReady)
                    textView.setText(weather.getTemp() + "");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        localWeather.getLocationAccess().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        localWeather.getLocationAccess().onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
