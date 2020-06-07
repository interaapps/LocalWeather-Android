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
+ Override `onActivityResult` and call `localWeather.onActivityResult` inside it if using current user location
+ Override `onRequestPermissionsResult` and call `localWeather.onRequestPermissionsResult` inside it if using current user location

Example:
```java
public class MainActivity extends AppCompatActivity {

    private LocalWeather localWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        localWeather = new LocalWeather(this, "OpenWeatherApiKey");
        
        localWeather.setUseCurrentLocation(true);
        localWeather.setUpdateCurrentLocation(true);
        localWeather.lang = Lang.ENGLISH;
        localWeather.unit = Units.METRIC;
        
        localWeather.setWeatherCallback(new LocalWeather.WeatherCallback(){
            @Override
            public void onSuccess(Weather weather) { 
                updateWeatherDetails(weather);
            }
            
            @Override
            public void onFailure(Throwable exception) {
                Log.e("Weather fetching", exception.getMessage());
            }
        });
        
        localWeather.fetchCurrentLocation(new LocalWeather.CurrentLocationCallback(){
            @Override
            public void onSuccess(Location location) {
                localWeather.fetchCurrentWeatherByLocation(location);
            }
            
            @Override
            public void onFailure(LocationFailedEnum failed) {
                Log.e("Location fetching", failed.toString());
            }
        });
    }
    
    private void updateWeatherDetails(Weather weather) {
        String unit = (localWeather.unit == Units.METRIC) ? "°C" : "°F" ;
        exampleTextview.setText(((int) weather.getTemperature()) + unit); //Output: Temp with desired unit
        
        //do whatever you want
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        localWeather.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        localWeather.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
    implementation 'com.github.interaapps:LocalWeather-Android:2.0'
    ...
}
```

## Support this project

If this library has helped you, please show your support by donating to this project:

<a href="https://www.paypal.me/datlag" title="Donate to this project using Paypal"><img src="https://img.shields.io/badge/Paypal-Donate-blue.svg" alt="PayPal donate button" /></a>

**Feel free to open a Pull Request**

Thank you :)
