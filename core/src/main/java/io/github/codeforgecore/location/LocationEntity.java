package io.github.codeforgecore.location;

import io.github.codeforgecore.utils.DateConverter;
import java.util.Date;

public class LocationEntity {
    
    private String mId;
    private String mAddress;
    private String mAddress2;
    private String mCity;
    private String mPostalCode;
    private String mCountry;
    private String mCreatedAt;
    private String mCreatedBy;
    private String mLastUpdatedAt;
    private String mLastUpdatedBy;
    
    private LocationEntity(String locationId, String address, String address2, String city,
                           String postalCode, String country, String createdAt, String createdBy,
                           String lastUpdatedAt, String lastUpdatedBy) {
        mId = locationId;
        mAddress = address;
        mAddress2 = address2;
        mCity = city;
        mPostalCode = postalCode;
        mCountry = country;
        mCreatedAt = createdAt;
        mCreatedBy = createdBy;
        mLastUpdatedAt = lastUpdatedAt;
        mLastUpdatedBy = lastUpdatedBy;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getAddress2() {
        return mAddress2;
    }

    public void setAddress2(String address2) {
        mAddress2 = address2;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getPostalCode() {
        return mPostalCode;
    }

    public void setPostalCode(String postalCode) {
        mPostalCode = postalCode;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getCreatedBy() {
        return mCreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        mCreatedBy = createdBy;
    }

    public String getLastUpdatedAt() {
        return mLastUpdatedAt;
    }

    public void setLastUpdatedAt(String lastUpdatedAt) {
        mLastUpdatedAt = lastUpdatedAt;
    }

    public String getLastUpdatedBy() {
        return mLastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        mLastUpdatedBy = lastUpdatedBy;
    }

    public static LocationEntity create(String locationId) {
        return new LocationEntity(locationId, "", "", "", "", "",
                DateConverter.toString(new Date()), "", "", "");
    }

    public static LocationEntity create(String locationId, String address, String address2,
                                        String city, String postalCode, String country,
                                        String createdAt, String createdBy, String lastUpdatedAt,
                                        String lastUpdatedBy) {

        return new LocationEntity(locationId, address, address2, city, postalCode, country,
                createdAt, createdBy, lastUpdatedAt, lastUpdatedBy);

    }
}
