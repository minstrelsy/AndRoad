// Created by plusminus on 00:51:14 - 03.12.2008
package org.androad.osm.adt;

import java.util.Formatter;
import java.util.GregorianCalendar;

import org.osmdroid.contributor.util.RecordedRouteGPXFormatter;
import org.osmdroid.views.util.constants.MapViewConstants;

import org.androad.osm.api.traces.util.Util;

public class GPSGeoLocation extends GeoLocation {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mNumSatellites = MapViewConstants.NOT_SET;

	// ===========================================================
	// Constructors
	// ===========================================================

	public GPSGeoLocation(final int latitudeE6, final int longitudeE6, final long timeStamp) {
		this(latitudeE6, longitudeE6, timeStamp, MapViewConstants.NOT_SET);
	}

	public GPSGeoLocation(final int latitudeE6, final int longitudeE6, final int aNumSatellites) {
		this(latitudeE6, longitudeE6, new GregorianCalendar().getTimeInMillis(), aNumSatellites);
	}

	public GPSGeoLocation(final int latitudeE6, final int longitudeE6, final long timestamp, final int aNumSatellites) {
		this(latitudeE6, longitudeE6, timestamp, MapViewConstants.NOT_SET, MapViewConstants.NOT_SET, MapViewConstants.NOT_SET, aNumSatellites);
	}

	public GPSGeoLocation(final int latitudeE6, final int longitudeE6, final long timeStamp, final int altitude, final int bearing, final int speed, final int aNumSatellites) {
		super(latitudeE6, longitudeE6, timeStamp, altitude, bearing, speed);
		this.mNumSatellites = aNumSatellites;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public int getNumSatellites() {
		return this.mNumSatellites;
	}

	public void setNumSatellites(final int aNumSatellites) {
		this.mNumSatellites = aNumSatellites;
	}

	public boolean hasNumSatellites(){
		return this.mNumSatellites != MapViewConstants.NOT_SET;
	}

	// ===========================================================
	// Methods from SuperClass/Interfaces
	// ===========================================================

	public void appendToGpxString(final StringBuilder sb, final Formatter f){
		f.format(RecordedRouteGPXFormatter.GPX_TAG_TRACK_SEGMENT_POINT, getLatitudeE6() / 1E6, getLongitudeE6() / 1E6);
		f.format(RecordedRouteGPXFormatter.GPX_TAG_TRACK_SEGMENT_POINT_TIME, Util.convertTimestampToUTCString(getTimeStamp()));

		if(hasNumSatellites()) {
			f.format(RecordedRouteGPXFormatter.GPX_TAG_TRACK_SEGMENT_POINT_SAT, getNumSatellites());
		}

		if(hasAltitude()) {
			f.format(RecordedRouteGPXFormatter.GPX_TAG_TRACK_SEGMENT_POINT_ELE, getAltitude());
		}

		if(hasBearing()) {
			f.format(RecordedRouteGPXFormatter.GPX_TAG_TRACK_SEGMENT_POINT_SAT, getBearing());
		}

		if(hasSpeed()) {
			f.format(RecordedRouteGPXFormatter.GPX_TAG_TRACK_SEGMENT_POINT_SAT, getSpeed());
		}

		sb.append(RecordedRouteGPXFormatter.GPX_TAG_TRACK_SEGMENT_POINT_CLOSE);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
