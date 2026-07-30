package io.github.jcodeforge.core.networking.schemes.responses;

import com.google.gson.annotations.SerializedName;

public class GetVersionInfoResponseScheme {

    @SerializedName("version") private String mVersion;

    public String getVersion() {
        return mVersion;
    }
}
