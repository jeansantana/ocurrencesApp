package com.jei.occurrences.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Occurrence {
	protected String _id; //id api
	protected Integer id; //local id
	protected Integer __v;
	protected String title;
	protected OccurrenceLocation location;
	protected String date;
	protected String hour;
	protected String crimeType;
	protected String description;

	public Occurrence(String title, OccurrenceLocation location, String date,
			String hour, String crimeType, String description) {
		this.id = null;
		this.title = title;
		this.location = location;
		this.date = date;
		this.hour = hour;
		this.crimeType = crimeType;
		this.description = description;
	}
	//for api js
	/*public Occurrence(String _id, String title, String location, String date,
			String hour, String crimeType, String description) {
		this._id = _id;
		this.id = null;
		this.title = title;
		this.location = new OccurrenceLocation(location);
		this.date = date;
		this.hour = hour;
		this.crimeType = crimeType;
		this.description = description;
	} */
	
	public Occurrence() {
		// TODO Auto-generated constructor stub
	}

	public Occurrence(Integer id, String _id, String title, OccurrenceLocation location, String date,
			String hour, String crimeType, String description) {
		this.id = id;
		this._id = _id;
		this.title = title;
		this.location = location;
		this.date = date;
		this.hour = hour;
		this.crimeType = crimeType;
		this.description = description;
	}

	public Integer get__v() {
		return __v;
	}
	
	public void set__v(Integer __v) {
		this.__v = __v;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public OccurrenceLocation getLocation() {
		return location;
	}

	public void setLocation(OccurrenceLocation location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getCrimeType() {
		return crimeType;
	}

	public void setCrimeType(String crimeType) {
		this.crimeType = crimeType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return title + "\n"
				+ "Location: " + location.toString() + "\n"
				+ "date: " + date + "\n"
				+ "hour: " + hour + "\n"
				+ "crime type: " + crimeType + "\n"
				+ "description: " + description;
				
	}
}