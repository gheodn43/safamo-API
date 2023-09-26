package com.backend.restapi.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "GPSAddress")
public class GPSAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int GPSAddressID;

    private String Description;

    @Column(precision = 9, scale = 6)
    private BigDecimal Lat;

    @Column(precision = 9, scale = 6)
    private BigDecimal Lng;


	public int getGPSAddressID() {
		return GPSAddressID;
	}
	public void setGPSAddressID(int gPSAddressID) {
		GPSAddressID = gPSAddressID;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public BigDecimal getLat() {
		return Lat;
	}
	public void setLat(BigDecimal lat) {
		Lat = lat;
	}
	public BigDecimal getLng() {
		return Lng;
	}
	public void setLng(BigDecimal lng) {
		Lng = lng;
	}

}

