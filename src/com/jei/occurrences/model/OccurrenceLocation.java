package com.jei.occurrences.model;

public class OccurrenceLocation {

	private String _id;
	private Integer id;
	private String street;//rua/av.
	private String country;//país
	private String city;// cidade
	private String neighborhood;// bairro
	private String state;// estado
	private double latitude;
	private double longitude;

	public OccurrenceLocation() {}
	
	public OccurrenceLocation(String location, double lat, double longt){
		this.id = null;
		try {
			String loc[] = location.split(",");
			
			
			this.street = loc[0];
			this.neighborhood = loc[1];
			this.city = loc[2];
			this.state = loc[3];
			this.country = loc[4];
			this.latitude = lat;
			this.longitude = longt;
		} catch (Exception e) {
			this.street = location;
			this.neighborhood = location;
			this.city = location;
			this.state = location;
			this.country = location;
			this.latitude = lat;
			this.longitude = longt;
		}
	}
	
	public OccurrenceLocation(String country, String city, String neighborhood,
			String state, String street, double latitude, double longitude) {
		this.street = street;
		this.country = country;
		this.city = city;
		this.neighborhood = neighborhood;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public OccurrenceLocation(Integer id, String _id, String country, String city, String neighborhood,
			String state, String street, double latitude, double longitude) {
		this.id = id;
		this._id = _id;
		this.street = street;
		this.country = country;
		this.city = city;
		this.neighborhood = neighborhood;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String get_id() {
		return _id;
	}
	
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return street + ", " + neighborhood + ", " + city + ", " + state + ", " + country;
	}
}
