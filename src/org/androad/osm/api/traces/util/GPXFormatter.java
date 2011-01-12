// Created by plusminus on 13:23:45 - 21.09.2008
package org.androad.osm.api.traces.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.osmdroid.util.GeoPoint;

import org.androad.osm.adt.GPSGeoLocation;
import org.androad.osm.api.util.constants.OSMTraceAPIConstants;
import org.androad.osm.util.constants.OSMConstants;


/**
 * Class capable of formatting a List of Points to the GPX 1.1 format.
 * @author Nicolas Gramlich
 *
 */
public class GPXFormatter implements OSMTraceAPIConstants, OSMConstants {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final SimpleDateFormat formatterCompleteDateTime = new SimpleDateFormat("yyyyMMdd'_'HHmmss");

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	/**
	 * Creates a String in the following XML format:
	 * <PRE>&lt;?xml version=&quot;1.0&quot;?&gt;
	 * &lt;gpx version=&quot;1.1&quot; creator=&quot;AndRoad - https://github.com/gkfabs/AndRoad&quot; xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot; xmlns=&quot;http://www.topografix.com/GPX/1/1&quot; xsi:schemaLocation=&quot;http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd&quot;&gt;
	 * 	&lt;time&gt;2008-09-22T00:46:20Z&lt;/time&gt;
	 * 	&lt;trk&gt;
	 * 	&lt;name&gt;plusminus--yyyyMMdd_HHmmss-yyyyMMdd_HHmmss&lt;/name&gt;
	 * 		&lt;trkseg&gt;
	 * 			&lt;trkpt lat=&quot;49.472767&quot; lon=&quot;8.654174&quot;&gt;
	 * 				&lt;time&gt;2008-09-22T00:46:20Z&lt;/time&gt;
	 * 			&lt;/trkpt&gt;
	 * 			&lt;trkpt lat=&quot;49.472797&quot; lon=&quot;8.654102&quot;&gt;
	 * 				&lt;time&gt;2008-09-22T00:46:35Z&lt;/time&gt;
	 * 			&lt;/trkpt&gt;
	 * 			&lt;trkpt lat=&quot;49.472802&quot; lon=&quot;8.654185&quot;&gt;
	 * 				&lt;time&gt;2008-09-22T00:46:50Z&lt;/time&gt;
	 * 			&lt;/trkpt&gt;
	 * 		&lt;/trkseg&gt;
	 * 	&lt;/trk&gt;
	 * &lt;/gpx&gt;</PRE>
	 * 
	 */
	public static String create(final List<GeoPoint> someRecords) throws IllegalArgumentException {
		if(someRecords == null) {
			throw new IllegalArgumentException("Records may not be null.");
		}

		if(someRecords.size() == 0) {
			throw new IllegalArgumentException("Records size == 0");
		}

		final StringBuilder sb = new StringBuilder();
		final Formatter f = new Formatter(sb, Locale.ENGLISH);
		sb.append(XML_VERSION);
		f.format(GPX_TAG, OSM_CREATOR_INFO);
		f.format(GPX_TAG_TIME, Util.convertTimestampToUTCString(System.currentTimeMillis()));
		sb.append(GPX_TAG_TRACK);

		final GeoPoint first = someRecords.get(0);
		final GeoPoint last = someRecords.get(someRecords.size() - 1);
		if(first instanceof GPSGeoLocation && last instanceof GPSGeoLocation){
			f.format(GPX_TAG_TRACK_NAME, OSM_USERNAME + "--"
					+ formatterCompleteDateTime.format(new Date(((GPSGeoLocation)first).getTimeStamp()).getTime())
					+ "-"
					+ formatterCompleteDateTime.format(new Date(((GPSGeoLocation)last).getTimeStamp()).getTime()));
		}else{
			f.format(GPX_TAG_TRACK_NAME, OSM_USERNAME + "--"
					+ formatterCompleteDateTime.format(new GregorianCalendar().getTime().getTime()));
		}

		sb.append(GPX_TAG_TRACK_SEGMENT);

		for (final GeoPoint rgp : someRecords) {
			f.format(GPX_TAG_TRACK_SEGMENT_POINT, rgp.getLatitudeE6() / 1E6, rgp.getLongitudeE6() / 1E6);
            sb.append(GPX_TAG_TRACK_SEGMENT_POINT_CLOSE);
		}

		sb.append(GPX_TAG_TRACK_SEGMENT_CLOSE)
		.append(GPX_TAG_TRACK_CLOSE)
		.append(GPX_TAG_CLOSE);

		return sb.toString();
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
