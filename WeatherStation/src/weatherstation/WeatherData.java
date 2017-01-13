package weatherstation;

import java.util.ArrayList;

/**
 *
 * @author Courtney Pattison
 */
public class WeatherData implements Subject {
    private ArrayList observers;
    private float temperature, humidity, pressure;
    
    public WeatherData() {
        observers = new ArrayList();
    }
    
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    
    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(o);
        }
    }
    
    @Override
    public void notifyObservers() {
        for (Object o : observers) {
            Observer observer = (Observer)o;
            observer.update(temperature, humidity, pressure);
        }
    }
    
    public void measurementsChanged() {
        notifyObservers();
    }
    
    public void setMeasurements(float temperature, float humidity,
            float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        
        measurementsChanged();
    }
}