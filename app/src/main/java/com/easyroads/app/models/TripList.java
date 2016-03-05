package com.easyroads.app.models;

public class TripList {
	private Integer likess;
	private String tripName;
	private String placeName;
	private Integer kilometer;
	private String pointOfInterest;

	public Integer getLikess() {
		return likess;
	}

	public void setLikess(Integer likess) {
		this.likess = likess;
	}

	public String getTripName() {
		return tripName;
	}

	public void setTripName(String tripName) {
		this.tripName = tripName;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Integer getKilometer() {
		return kilometer;
	}

	public void setKilometer(Integer kilometer) {
		this.kilometer = kilometer;
	}

	public String getPointOfInterest() {
		return pointOfInterest;
	}

	public void setPointOfInterest(String pointOfInterest) {
		this.pointOfInterest = pointOfInterest;
	}
}
