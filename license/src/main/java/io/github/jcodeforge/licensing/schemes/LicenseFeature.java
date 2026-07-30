package io.github.jcodeforge.licensing.schemes;

/**
 * An interface that other objects can implement to indicate that they represent features.
 * This can be especially useful for decorating enums, for example, to represent valid features.
 */
public interface LicenseFeature {
    /**
     * Returns the id of this feature.
     *
     * @return The id
     */
    String getFeatureId();
}
