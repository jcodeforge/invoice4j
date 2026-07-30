package io.github.codeforgecore.common;

import io.github.codeforgecore.exception.ObjectDeserializationException;
import io.github.codeforgecore.exception.ObjectSerializationException;
import io.github.codeforgecore.exception.ObjectTypeNotExpectedException;
import org.apache.commons.io.output.ByteArrayOutputStream;
import java.io.*;

/**
 * This is a helper class for writing any object and reading simple objects (no
 * arrays, collections, or generic top-level objects) to and from byte arrays.
 */
public final class ObjectSerializer
{
    /**
     * Deserializes an object of the specified type from the provided byte stream.
     */
    public <T extends Serializable> T readObject(final Class<T> expectedType, final byte[] byteStream)
        throws ObjectDeserializationException {

        final ByteArrayInputStream bytes = new ByteArrayInputStream(byteStream);
        try(final ObjectInputStream stream = new ObjectInputStream(bytes)) {
            final Object allegedObject = stream.readObject();
            if(!expectedType.isInstance(allegedObject)) {
                throw new ObjectTypeNotExpectedException(expectedType.getName(),
                        allegedObject.getClass().getName()
                );
            }

            return expectedType.cast(allegedObject);

        } catch(final IOException e) {
            throw new ObjectDeserializationException(
                "An I/O error occurred while reading the object from the byte array.",
                e
            );

        } catch(final ClassNotFoundException | NoClassDefFoundError e) {
            throw new ObjectTypeNotExpectedException(
                expectedType.getName(),
                e.getMessage(),
                e
            );
        }
    }

    /**
     * Serializes the {@link Serializable} object passed and returns it as a byte array.
     *
     * @param object The object to serialize
     *
     * @return the byte stream with the object serialized in it.
     *
     * @throws ObjectSerializationException if an I/O exception occurs while serializing the object.
     */
    public byte[] writeObject(final Serializable object) throws ObjectSerializationException {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        try(final ObjectOutputStream stream = new ObjectOutputStream(bytes)) {
            stream.writeObject(object);
        } catch(final IOException e) {
            throw new ObjectSerializationException(e);
        }

        return bytes.toByteArray();
    }
}
