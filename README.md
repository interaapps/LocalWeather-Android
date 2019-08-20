# LocalWeather
[![](https://jitpack.io/v/interaapps/LocalWeather-Android.svg)](https://jitpack.io/#interaapps/LocalWeather-Android) <a href="https://www.paypal.me/datlag" title="Donate to this project using Paypal"><img src="https://img.shields.io/badge/Paypal-Donate-blue.svg" alt="PayPal donate button" /></a>

An Android library that lets you get the current weather at the user's location!
+ Location is precise up to 7 decimal places (highest precision)
+ No need to add any permissions in manifest manually
+ No need to add google play services location lib in gradle manually
+ Uses Google location services API internally - so you're in safe hands
+ Tries to get location stored on phone if Google's API fails
+ Connects automatically with OpenWeather to get users local weather

## Screenshot
![alt text](https://raw.githubusercontent.com/interaapps/LocalWeather-Android/master/app/src/main/res/drawable/example.png "Example Application")

## Usage

+ Declare localWeather in your activity
+ Override `onActivityResult` and call `localWeather.getLocationAccess().onActivityResult` inside it
+ Override `onRequestPermissionsResult` and call `localWeather.getLocationAccess().onRequestPermissionsResult` inside it
+ Declare weather in your activity like this: `weather = localWeather.getWeather()`

Example:
```java
public class MainActivity extends AppCompatActivity {

    private LocalWeather localWeather;
    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        localWeather = new LocalWeather(this,
                        "OpenWeatherApiKey",
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
                                localWeather.fetchWeather();
                            }
        
                            @Override
                            public void onWeatherSuccess() {
                                weather = localWeather.getWeather();
                                updateWeatherDetails();
                            }
                        });
        
        localWeather.fetchLocation();
    }
    
    private void updateWeatherDetails() {
        String unit = (localWeather.getUnit() == Units.METRIC) ? "°C" : "°F" ;
        exampleTextview.setText(((int) weather.getTemp()) + unit); //Output: Temp with desired unit
        
        //do whatever you want
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
```

You can look at the code of the MainActivity to understand it better and to see some examples of what information you can retrieve with the Weather class

## Setup

Add this line in your root build.gradle at the end of repositories:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
        ...
    }
}
  ```
Add this line in your app build.gradle:
```gradle
dependencies {
    ...
    implementation 'com.github.interaapps:LocalWeather-Android:1.1'
    ...
}
```

## Support this project

If this library has helped you, please show your support by donating to this project:

<a href="https://www.paypal.me/datlag" title="Donate to this project using Paypal"><img src="https://img.shields.io/badge/Paypal-Donate-blue.svg" alt="PayPal donate button" /></a>

**Feel free to open a Pull Request**

Thank you :)
