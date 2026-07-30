package io.github.jcodeforge.licensing.immutable;

/**
 * This class specifies an interface for checking the validity of an object. It
 * is specified as an abstract class instead of an interface so that the
 * implementing classes can keep the target method protected.
 */
abstract class ValidObject
{
    /**
     * Checks the validity of this object, and throws an
     * {@link ImmutableModifiedThroughReflectionError} if that check fails.
     *
     * @throws ImmutableModifiedThroughReflectionError if the validity check fails.
     */
    protected abstract void checkValidity();
}
