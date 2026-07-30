package io.github.jcodeforge.licensing.schemes;

import com.google.gson.annotations.SerializedName;
import io.github.jcodeforge.core.utils.DateConverter;
import io.github.jcodeforge.core.utils.JsonConverter;
import io.github.jcodeforge.licensing.encryption.KeyFileUtils;
import org.apache.commons.collections.ListUtils;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class License  {

    @SerializedName("product_key")
    private final String mProductKey;

    @SerializedName("holder_id")
    private final String mHolderId;

    @SerializedName("holder_name")
    private final String mHolderName;

    @SerializedName("holder_name2")
    private final String mHolderName2;

    @SerializedName("issuer")
    private final String mIssuer;

    @SerializedName("subject")
    private final String mSubject;

    @SerializedName("client_id")
    private final String mClientId;

    @SerializedName("client_name")
    private final String mClientName;

    @SerializedName("issue_date")
    private final String mIssueDate;

    @SerializedName("valid_after_date")
    private final String mValidAfterDate;

    @SerializedName("expiring_date")
    private final String mExpiringDate;

    @SerializedName("number_of_licenses")
    private final int mNumberOfLicenses;

    @SerializedName("number_of_vehicles")
    private final int mNumberOfVehicles;

    @SerializedName("features")
    private final List<Feature> mFeatures;

    /**
     * An internal constructor for creating a license from a builder.
     */
    private License(License.Builder builder) {
        mProductKey = builder.mProductKey;
        mHolderId = builder.mHolderId;
        mHolderName = builder.mHolderName;
        mHolderName2 = builder.mHolderName2;
        mIssuer = builder.mIssuer;
        mSubject = builder.mSubject;
        mClientId = builder.mClientId;
        mClientName = builder.mClientName;
        mIssueDate = DateConverter.toString(builder.mIssueDate);
        mValidAfterDate = DateConverter.toString(builder.mValidAfterDate);
        mExpiringDate = DateConverter.toString(builder.mExpiringDate);
        mNumberOfLicenses = builder.mNumberOfLicenses;
        mNumberOfVehicles = builder.mNumberOfVehicles;
        mFeatures = new ArrayList<>(builder.mFeatures);
        ListUtils.unmodifiableList(mFeatures);
    }

    /**
     * Returns the product key for this license. The productKey, {@link #getIssuer() issuer},
     * {@link #getHolderId() holder}, and {@link #getSubject() subject} are symbolically named; they are interchangeable
     * and can be used to hold any number of pieces of information. For example, one might use the holder to store a
     * hardware ID, or the subject to store a product name and version combination.
     * We use it, to store the encrypted subject-client combination with the expiration dates.
     * (e.g. subject + "\n" + client + "\n" + licenseValidAfterDate + "\n" + licenseExpiringDate)
     *
     * @see KeyFileUtils#encrypt(String)
     *
     * @return the product key.
     */
    public String getProductKey() {
        return mProductKey;
    }

    public String getHolderId() {
        return mHolderId;
    }

    public String getHolderName() {
        return mHolderName;
    }

    public String getHolderName2() {
        return mHolderName2;
    }

    public String getIssuer() {
        return mIssuer;
    }

    /**
     * Returns the subject for this license. The {@link #getProductKey() productKey},
     * {@link #getIssuer() issuer}, {@link #getHolderId() holder}, and subject are symbolically named;
     * they are interchangeable and can be used to hold any number of pieces of information.
     * For example, one might use the holder to store a hardware ID, or the subject to store a
     * product name and version combination.
     * In our case the product type / client type is stored.
     */
    public String getSubject() {
        return mSubject;
    }

    public String getClientId() {
        return mClientId;
    }

    /**
     * Returns the client name (mandant) for this license.
     */
    public String getClientName() {
        return mClientId;
    }

    public String getIssueDate() {
        return mIssueDate;
    }

    public String getValidAfterDate() {
        return mValidAfterDate;
    }

    public String getExpiringDate() {
        return mExpiringDate;
    }

    public int getNumberOfLicenses() {
        return mNumberOfLicenses;
    }

    public int getNumberOfVehicles() {
        return mNumberOfVehicles;
    }

    /**
     * Returns an immutable (unchangeable) list of all the features contained within this license.
     */
    public List<License.Feature> getFeatures() {
        return mFeatures;
    }

    /**
     * Serialized this license into a byte array that can be shipped electronically.
     */
    public byte[] serialize() {
        return JsonConverter.toJson(this).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Deserializes a serialized license into an actual License object.
     */
    public static License deserialize(final byte[] data) {
        return JsonConverter.fromJson(new String(data, StandardCharsets.UTF_8), License.class);
    }

    /**
     * Checks if the feature specified is licensed. If the feature is licensed and has an
     * expiration, ensures that the feature is not expired based on the provided date
     * before returning {@code true}.
     *
     * @param featureName The feature to check
     * @param checkDate The date to check the feature against
     *
     * @return {@code true} if this feature is licensed and valid, {@code false} otherwise.
     */
    private boolean hasFeature(String featureName, Date checkDate) {
        License.Feature feature = new License.Feature(featureName);
        if (mFeatures.contains(feature)) {
            License.Feature contained = mFeatures.get(mFeatures.indexOf(feature));
            Date expiringDate = DateConverter.toDate(contained.getExpiringDate());
            return expiringDate.after(checkDate);
        }

        return false;
    }

    /**
     * Checks if all the features specified are licensed in this license. If any one feature is licensed, this
     * method returns {@code true}. Any features that contain expiration dates are checked against the current date.
     * Features are only valid if they have no expiration date, or they are not expired.
     *
     * @param checkDate The date to check features against
     * @param features The features to check
     *
     * @return {@code true} if any one feature listed is licensed and valid, {@code false} otherwise.
     */
    public boolean hasAnyFeatures(Date checkDate, String... features) {
        for (String feature : features) {
            if(!hasFeature(feature, checkDate)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasAnyFeatures(String... features) {
        return hasAnyFeatures(new Date(), features);
    }

    public boolean hasAnyFeatures(Date checkDate, Feature... features) {
        for (Feature feature : features) {
            if (!hasFeature(feature.getFeatureId(), checkDate)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasAnyFeatures(Feature... features) {
        return hasAnyFeatures(new Date(), features);
    }

    /**
     * Checks whether the provided license object is identical to this one in content, features
     * and expiration dates, deeply.
     */
    @Override
    public boolean equals(Object object) {
        if(object == null || !object.getClass().equals(License.class)) {
            return false;
        }

        License license = (License) object;

        boolean equals =
                license.getProductKey().equals(mProductKey)
                        && license.getHolderId().equals(mHolderId)
                        && license.getHolderName().equals(mHolderName)
                        && license.getHolderName2().equals(mHolderName2)
                        && license.getIssuer().equals(mIssuer)
                        && license.getSubject().equals(mSubject)
                        && license.getClientId().equals(mClientId)
                        && license.getClientName().equals(mClientName)
                        && license.getIssueDate().equals(mIssueDate)
                        && license.getValidAfterDate().equals(mValidAfterDate)
                        && license.getExpiringDate().equals(mExpiringDate)
                        && license.getNumberOfLicenses() == mNumberOfLicenses
                        && license.getNumberOfVehicles() == mNumberOfVehicles;
        if(!equals) {
            return false;
        }

        for(License.Feature feature : mFeatures) {
            final License.Feature contained = license.getFeatures().get(mFeatures.indexOf(feature));
            if(contained == null || !contained.getExpiringDate().equals(feature.getExpiringDate())
                    || !contained.getFeatureParam().equals(feature.getFeatureParam())
                    || contained.getActivationLimit() != feature.getActivationLimit()) {
                return false;
            }
        }

        return true;
    }

    /**
     * A class that represents a licensed feature. Products may have more than one "section" or
     * "module" that is licensed, and the list of features in the {@link License} object allows the
     * user to specify any number of features and, optionally, separate expiration dates for each.
     */
    public static final class Feature implements LicenseFeature {

        @SerializedName("id")
        private final String mFeatureId;

        @SerializedName("expiring_date")
        private final String mExpiringDate;

        @SerializedName("feature_param")
        private final String mFeatureParam;

        @SerializedName("activation_limit")
        private final int mActivationLimit;

        private Feature(String featureId, Date expiringDate, String featureParam, int activationLimit) {
            mFeatureId = featureId;
            mExpiringDate = DateConverter.toString(expiringDate);
            mFeatureParam = featureParam;
            mActivationLimit = activationLimit;
        }

        private Feature(String featureId) {
            this(featureId, null, "", 0);
        }

        @Override
        public String getFeatureId() {
            return mFeatureId;
        }

        public String getExpiringDate() {
            return mExpiringDate;
        }

        public String getFeatureParam() {
            return mFeatureParam;
        }

        public int getActivationLimit() {
            return mActivationLimit;
        }

        /**
         * Indicates whether these features are the same feature.
         */
        @Override
        public boolean equals(final Object object) {
            if(object == null || object.getClass() != License.Feature.class) {
                return false;
            }

            final License.Feature feature = (License.Feature) object;
            return feature.getFeatureId().equals(mFeatureId);
        }

        /**
         * Generates a hash code for this feature.
         *
         * @return a hash code for this feature.
         */
        @Override
        public int hashCode() {
            return mFeatureId.hashCode();
        }
    }

    /**
     * This class is responsible for all license creation.
     * Each method in this class returns the builder instance to make chaining possible.
     * To create a license, simply create an instance of a builder, set any values you wish
     * to be set on the license (or choose not to set values, as appropriate), then call {@link #build()}.
     */
    public static final class Builder {

        private String mProductKey = "";
        private String mHolderId = "";
        private String mHolderName = "";
        private String mHolderName2 = "";
        private String mIssuer = "";
        private String mSubject = "";
        private String mClientId = "";
        private String mClientName = "";
        private Date mIssueDate = new Date();
        private Date mValidAfterDate = null;
        private Date mExpiringDate = null;
        private int mNumberOfLicenses = 0;
        private int mNumberOfVehicles = 0;
        private final Set<Feature> mFeatures = new LinkedHashSet<>();

        /**
         * Creates a new builder for generating a new license.
         */
        public Builder() {
        }

        public Builder withProductKey(String productKey) {
            mProductKey = productKey;
            return this;
        }

        public Builder withIssuer(String issuer) {
            mIssuer = issuer;
            return this;
        }

        public Builder withHolderId(String id) {
            mHolderId = id;
            return this;
        }

        public Builder withHolderName(String name) {
            mHolderName = name;
            return this;
        }

        public Builder withHolderName2(String name2) {
            mHolderName2 = name2;
            return this;
        }

        public Builder withSubject(String subject) {
            mSubject = subject;
            return this;
        }

        public Builder withClientId(String clientId) {
            mClientId = clientId;
            return this;
        }

        public Builder withClientName(String clientName) {
            mClientName = clientName;
            return this;
        }

        public Builder withIssueDate(Date issueDate) {
            mIssueDate = issueDate;
            return this;
        }

        public Builder withValidAfterDate(Date validAfterDate) {
            mValidAfterDate = validAfterDate;
            return this;
        }

        public Builder withExpiringDate(Date expiringDate) {
            mExpiringDate = expiringDate;
            return this;
        }

        public Builder withNumberOfLicenses(int numberOfLicenses) {
            mNumberOfLicenses = numberOfLicenses;
            return this;
        }

        public Builder withNumberOfVehicles(int numberOfVehicles) {
            mNumberOfVehicles = numberOfVehicles;
            return this;
        }

        /**
         * Adds a feature to this license with the specified expiration date and additional parameters.
         */
        public Builder addFeature(String featureId, Date expirationDate, String featureParam,
                                  int activationLimit) {
            mFeatures.add(new License.Feature(featureId, expirationDate, featureParam, activationLimit));
            return this;
        }

        /**
         * Builds the license based on the criteria in this builder, then returns it.
         */
        public License build() {
            return new License(this);
        }
    }
}
