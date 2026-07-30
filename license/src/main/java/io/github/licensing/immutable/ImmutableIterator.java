package io.github.licensing.immutable;

import java.util.Iterator;

/**
 * Wraps an iterator such that it cannot be modified.
 *
 * @param <E> Any object
 */
public final class ImmutableIterator<E> implements Immutable, Iterator<E> {

    private final Iterator<E> internal;
    private final ValidObject validObject;

    ImmutableIterator(final Iterator<E> iterator, final ValidObject validObject)
    {
        this.internal = iterator;
        this.validObject = validObject;
    }

    @Override
    public boolean hasNext()
    {
        synchronized(this.validObject)
        {
            this.validObject.checkValidity();
            return this.internal.hasNext();
        }
    }

    @Override
    public E next()
    {
        synchronized(this.validObject)
        {
            this.validObject.checkValidity();
            return this.internal.next();
        }
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("This iterator cannot be modified.");
    }
}
