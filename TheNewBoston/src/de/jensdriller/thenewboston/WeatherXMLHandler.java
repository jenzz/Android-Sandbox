package de.jensdriller.thenewboston;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WeatherXMLHandler extends DefaultHandler {

	WeatherXMLData info = new WeatherXMLData();

	public String getInfo() {

		return info.toString();

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (localName.equals("city")) {

			String city = attributes.getValue("data");
			info.setCity(city);

		} else if (localName.equals("temp_f")) {

			String city = attributes.getValue("data");
			info.setTemp(Integer.parseInt(city));

		}

	}

}
