package io.github.codeforgecore.exception;

@SuppressWarnings("unused")
public class ObjectTypeNotExpectedException extends ObjectDeserializationException {

    public ObjectTypeNotExpectedException() {
        super("The type of object read did not match the type expected.");
    }

    public ObjectTypeNotExpectedException(final String message) {
        super(message);
    }

    public ObjectTypeNotExpectedException(final String expectedType, final String encounteredType) {
        super("While deserializing an object of expected type \"" + expectedType + "\", got an object of type \"" +
              encounteredType + "\" instead.");
    }

    public ObjectTypeNotExpectedException(final Throwable cause) {
        super("The type of object read did not match the type expected.", cause);
    }

    public ObjectTypeNotExpectedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ObjectTypeNotExpectedException(final String expectedType, final String encounteredType,
        final Throwable cause) {
        super("While deserializing an object of expected type \""
                + expectedType + "\", got an object of type \""
                + encounteredType + "\" instead.", cause);
    }
}
