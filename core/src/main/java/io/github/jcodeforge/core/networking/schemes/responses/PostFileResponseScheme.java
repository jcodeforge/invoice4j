package io.github.jcodeforge.core.networking.schemes.responses;

import com.google.gson.annotations.SerializedName;

public class PostFileResponseScheme {

    @SerializedName("id") private String mId;
    @SerializedName("name") private String mName;
    @SerializedName("extension") private String mExtension;
    @SerializedName("description") private String mDescription;
    @SerializedName("size") private Integer mSize;
    @SerializedName("origincreated") private String mOriginCreatedAt;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getExtension() {
        return mExtension;
    }

    public String getDescription() {
        return mDescription;
    }

    public Integer getSize() {
        return mSize;
    }

    public String getOriginCreatedAt() {
        return mOriginCreatedAt;
    }
}
