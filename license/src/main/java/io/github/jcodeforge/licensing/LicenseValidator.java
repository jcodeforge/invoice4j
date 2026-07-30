package io.github.jcodeforge.licensing;

import io.github.jcodeforge.licensing.exception.InvalidLicenseException;
import io.github.jcodeforge.licensing.exception.ExpiredLicenseException;
import io.github.jcodeforge.licensing.schemes.License;

/**
 * Specifies an interface for validating licenses.
 * There is a default implementation, {@link DefaultLicenseValidator}, that ensures the current date is between the
 * license's good-after and good-before dates (the license has taken effect and hasn't expired).
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface LicenseValidator
{
    /**
     * Validates the license provided and throws an exception if the license is invalid for any reason
     * (expired, not who it belongs to, etc.).
     *
     * @param license The license to validate
     *
     * @throws InvalidLicenseException when the license is invalid for any reason; the implementer is required to
     *     provide adequate description in this exception to indicate why the license is invalid; extending the
     *     exception is encouraged.
     * @throws ExpiredLicenseException when the license is expired.
     */
    void validateLicense(License license) throws InvalidLicenseException;
}
