package guimodule;

import java.util.*;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.marker.*;
import processing.core.PApplet;

public class LifeExpectancy extends PApplet {
	UnfoldingMap map;
	List<Marker> countryMarkers;
	List<Feature> countries;
	Map<String, Float> lifeExpMap =
			new HashMap<String, Float>();
	
	private Map<String, Float> loadLifeExpectancyFromCSV(String fileName) {
		String[] rows = loadStrings(fileName);
		
		for (String row : rows) {
			String[] columns = row.split(",");
			if (columns.length == 6 && !columns[5].equals("..")) {
				float value = Float.parseFloat(columns[5]);
				lifeExpMap.put(columns[4], value);
			}
		}
		return lifeExpMap;
	}
	
	public void setup() {
		// step 1: set up map
		size(800, 600, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 700, 500, 
						new Microsoft.HybridProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// step 2: read data
		Map<String, Float> lifeExpByCountry;
		lifeExpByCountry = loadLifeExpectancyFromCSV
							("LifeExpectancyWorldBankModule3.csv");
		// read country info
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		shadeCountries();
		
	}
	

	private void shadeCountries() {
		for (Marker marker : countryMarkers) {
			String countryId = marker.getId();
			if (lifeExpMap.containsKey(countryId)) {
				float lifeExp = lifeExpMap.get(countryId);
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else {
				marker.setColor(color(150,150,150));
			}
		}
		
	}

	public void draw() {
		map.draw();
	}
}
