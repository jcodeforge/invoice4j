package io.github.licensing.immutable;

import java.util.ListIterator;

/**
 * Wraps a list iterator such that it cannot be modified.
 *
 * @param <E> Any object
 */
public final class ImmutableListIterator<E> implements Immutable, ListIterator<E> {

    private final ListIterator<E> internal;
    private final ValidObject validObject;

    ImmutableListIterator(final ListIterator<E> iterator, final ValidObject validObject)
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
    public boolean hasPrevious()
    {
        synchronized(this.validObject)
        {
            this.validObject.checkValidity();
            return this.internal.hasPrevious();
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
    public int nextIndex()
    {
        synchronized(this.validObject)
        {
            this.validObject.checkValidity();
            return this.internal.nextIndex();
        }
    }

    @Override
    public E previous()
    {
        synchronized(this.validObject)
        {
            this.validObject.checkValidity();
            return this.internal.previous();
        }
    }

    @Override
    public int previousIndex()
    {
        synchronized(this.validObject)
        {
            this.validObject.checkValidity();
            return this.internal.previousIndex();
        }
    }

    @Override
    public void add(final E e)
    {
        throw new UnsupportedOperationException(ImmutableAbstractCollection.modificationProhibited);
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException(ImmutableAbstractCollection.modificationProhibited);
    }

    @Override
    public void set(final E e)
    {
        throw new UnsupportedOperationException(ImmutableAbstractCollection.modificationProhibited);
    }
}
