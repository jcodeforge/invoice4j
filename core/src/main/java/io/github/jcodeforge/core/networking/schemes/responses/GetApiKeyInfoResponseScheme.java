package io.github.jcodeforge.core.networking.schemes.responses;

import com.google.gson.annotations.SerializedName;
import io.github.jcodeforge.core.Constants;

public class GetApiKeyInfoResponseScheme {

    @SerializedName(Constants.FIELD_NAME_ID) private String mId;
    @SerializedName(Constants.FIELD_NAME_TOKEN) private String mToken;
    @SerializedName(Constants.FIELD_NAME_DESCRIPTION) private String mDescription;
    @SerializedName(Constants.FIELD_NAME_CREATED_AT) private String mCreatedAt;
    @SerializedName(Constants.FIELD_NAME_UPDATED_AT) private String mUpdatedAt;

    public String getId() {
        return mId;
    }

    public String getToken() {
        return mToken;
    }

    public String getDescription() {
        return mDescription;
    }
}
