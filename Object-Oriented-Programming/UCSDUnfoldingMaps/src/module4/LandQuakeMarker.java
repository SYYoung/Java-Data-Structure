package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** Implements a visual marker for land earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public class LandQuakeMarker extends EarthquakeMarker {
	
	
	public LandQuakeMarker(PointFeature quake) {
		
		// calling EarthquakeMarker constructor
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = true;
	}


	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		// Draw a centered circle for land quakes
		// DO NOT set the fill color here.  That will be set in the EarthquakeMarker
		// class to indicate the depth of the earthquake.
		// Simply draw a centered circle.
		
		// HINT: Notice the radius variable in the EarthquakeMarker class
		// and how it is set in the EarthquakeMarker constructor
		
		// TODO: Implement this method
		float mag = getMagnitude();
		int small = 5, medium = small * 2, large = small * 3;
		if (mag < THRESHOLD_LIGHT)
			pg.ellipse(x, y, small, small);
		else if (mag < THRESHOLD_MODERATE)
			pg.ellipse(x, y, medium, medium);
		else
			pg.ellipse(x, y, large, large);
		// if it is past day, draw an 'X'
		/*
		String age = getAge();
		if (age.equals("Past Day"))
			pg.text("X", x, y);
		*/
	}
	

	// Get the country the earthquake is in
	public String getCountry() {
		return (String) getProperty("country");
	}



		
}