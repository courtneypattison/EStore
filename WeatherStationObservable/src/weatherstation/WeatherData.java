package weatherstation;

import java.util.Observable;

/**
 *
 * @author Courtney Pattison
 */
public class WeatherData extends Observable {
    private float temperature, humidity, pressure;
    
    public WeatherData() {
    }
    
    public void measurementsChanged() {
        setChanged();
        notifyObservers();
    }
    
    public void setMeasurements(float temperature, float humidity,
            float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        
        measurementsChanged();
    }

    /**
     * @return the temperature
     */
    public float getTemperature() {
        return temperature;
    }

    /**
     * @return the humidity
     */
    public float getHumidity() {
        return humidity;
    }

    /**
     * @return the pressure
     */
    public float getPressure() {
        return pressure;
    }
}
