package io.github.jcodeforge.core.networking.schemes.responses;

import com.google.gson.annotations.SerializedName;

public class GetFileInfoResponseScheme {

    @SerializedName("id") private String mId;
    @SerializedName("name") private String mName;
    @SerializedName("extension") private String mExtension;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getExtension() {
        return mExtension;
    }
}
