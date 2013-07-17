package de.jensdriller.thenewboston;

public class WeatherXMLData {

	private int temp = 0;
	private String city = null;

	public void setCity(String city) {

		this.city = city;

	}

	public void setTemp(int temp) {

		this.temp = temp;

	}

	public String toString() {

		return "In " + city + " the current temperatur in F is " + temp + " degrees";

	}

}
