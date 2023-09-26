package com.backend.restapi.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "properties")
@Data
public class PropertyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int propertyId;
    private String propertyName;
    private String address;
    @Temporal(TemporalType.DATE) 
    private Date registrationDate;
    private int unitForRent;
    private String pictureUrl;

    @ManyToOne // Một tài sản thuộc về một user
    @JoinColumn(name = "user_id")
    private UserEntity owner;
    
    @ManyToOne
    @JoinColumn(name = "status_id")
    private PropertyStatus status;
    
    @OneToOne
    @JoinColumn(name = "gps_address_id") 
    private GPSAddress gpsAddress;

	public int getPropertyId() {
		return propertyId;
	}
	@ManyToOne
    @JoinColumn(name = "property_type_id")
    private PropertyRole propertyRole;
	
	
	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public int getUnitForRent() {
		return unitForRent;
	}

	public void setUnitForRent(int unitForRent) {
		this.unitForRent = unitForRent;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public UserEntity getOwner() {
		return owner;
	}

	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}

	public PropertyStatus getStatus() {
		return status;
	}

	public void setStatus(PropertyStatus status) {
		this.status = status;
	}

	public GPSAddress getGpsAddress() {
		return gpsAddress;
	}

	public void setGpsAddress(GPSAddress gpsAddress) {
		this.gpsAddress = gpsAddress;
	}
    
    
}
