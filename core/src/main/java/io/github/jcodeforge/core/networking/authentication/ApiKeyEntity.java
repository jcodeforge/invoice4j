package io.github.jcodeforge.core.networking.authentication;

import io.github.jcodeforge.core.utils.DateConverter;
import java.util.Date;

public class ApiKeyEntity {

    private String mId;
    private String mToken;
    private String mDescription;
    private String mUser;
    private String mCreatedAt;
    private String mCreatedBy;
    private String mLastUpdatedAt;
    private String mLastUpdatedBy;

    private ApiKeyEntity(String id, String token, String description, String user, String createdAt,
                        String createdBy, String lastUpdatedAt, String lastUpdatedBy) {
        mId = id;
        mToken = token;
        mDescription = description;
        mUser = user;
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

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String user) {
        mUser = user;
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

    public static ApiKeyEntity create(String id, String token, String description, String user,
                                      String createdAt, String createdBy, String lastUpdatedAt,
                                      String lastUpdatedBy) {
        return new ApiKeyEntity(id, token, description, user, createdAt, createdBy, lastUpdatedAt,
                lastUpdatedBy);
    }

    public static ApiKeyEntity create(String id, String user, String createdBy) {
        return new ApiKeyEntity(id, "", "", user, DateConverter.toString(new Date()),
                createdBy, null, "");
    }
}
