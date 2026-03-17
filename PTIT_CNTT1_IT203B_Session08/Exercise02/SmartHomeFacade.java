package PTIT_CNTT1_IT203B_Session08.Exercise02;

public class SmartHomeFacade {

    private Light light;
    private Fan fan;
    private AirConditioner airConditioner;
    private TemperatureSensor temperatureSensor;

    public SmartHomeFacade(TemperatureSensor temperatureSensor) {
        this.light = new Light();
        this.fan = new Fan();
        this.airConditioner = new AirConditioner();
        this.temperatureSensor = temperatureSensor;
    }

    public void leaveHome() {
        light.turnOff();
        fan.turnOff();
        airConditioner.turnOff();
    }

    public void sleepMode() {
        light.turnOff();
        airConditioner.setTemperature(28);
        fan.lowSpeed();
    }

    public double getCurrentTemperature() {
        return temperatureSensor.getTemperatureCelsius();
    }
}
