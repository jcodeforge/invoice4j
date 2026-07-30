package io.github.jcodeforge.licensing;

import io.github.jcodeforge.licensing.exception.ExpiredLicenseException;
import io.github.jcodeforge.licensing.exception.InvalidLicenseException;
import io.github.jcodeforge.licensing.schemes.License;
import io.github.jcodeforge.core.utils.DateConverter;
import java.util.Date;

/**
 * A default implementation of {@link LicenseValidator}, which simply checks that the license is active and not expired.
 */
public class DefaultLicenseValidator implements LicenseValidator {
    /**
     * Ensures the current date is between the license's good-after and good-before dates (the license
     * has taken effect and hasn't expired).
     *
     * @param license The license to validate
     *
     * @throws InvalidLicenseException when the license is invalid for any reason; the implementer is required to
     *     provide adequate description in this exception to indicate why the license is invalid; extending the
     *     exception is encouraged
     * @see InvalidLicenseException
     * @see ExpiredLicenseException
     */
    @Override
    public void validateLicense(License license) throws InvalidLicenseException {
        Date now = new Date();
        Date validAfterDate = DateConverter.toDate(license.getValidAfterDate());
        Date expiringDate = DateConverter.toDate(license.getExpiringDate());

        if(validAfterDate.after(now)) {
            throw new InvalidLicenseException(
                "The " + getLicenseInfo(license) + " does not take effect until " +
                        license.getValidAfterDate() + "."
            );
        }

        if(expiringDate.before(now)) {
            throw new ExpiredLicenseException(
                "The " + getLicenseInfo(license) + " expired on " +
                license.getExpiringDate() + "."
            );
        }
    }

    /**
     * Gets a description for the given license, usually for displaying in some user interface.
     *
     * @param license The license
     *
     * @return the description.
     */
    public String getLicenseInfo(License license) {
        return license.getSubject() + " license for " + license.getHolderName() + " " +
                license.getHolderName2();
    }
}
