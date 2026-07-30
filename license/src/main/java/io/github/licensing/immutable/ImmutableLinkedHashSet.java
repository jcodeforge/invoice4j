package io.github.licensing.immutable;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Wraps a set such that it cannot be modified. There is some overhead
 * associated with this due to verification of hash codes on every call to
 * prevent tampering with via reflection, but this is well worth it if your goal
 * is security and you truly need an unmodifiable set.
 *
 * @param <E> Any object
 */
public final class ImmutableLinkedHashSet<E> extends ImmutableAbstractCollection<E>
    implements Set<E>, Serializable, Cloneable {

    @Serial
    private final static long serialVersionUID = 2284350955829958161L;

    private final LinkedHashSet<E> internalSet;

    private final ArrayList<E> internalList;

    /**
     * Constructor that copies.
     *
     * @param list the set to decorate, must not be null
     *
     * @throws IllegalArgumentException if list is null
     */
    public ImmutableLinkedHashSet(final Set<E> list)
    {
        super(new LinkedHashSet<>(list));

        this.internalSet = (LinkedHashSet<E>) this.internalCollection;
        this.internalList = new ArrayList<>(list);
    }

    @Override
    @SuppressWarnings({"unchecked", "CloneDoesntCallSuperClone"})
    public final ImmutableLinkedHashSet<E> clone()
    {
        synchronized(this.internalSet)
        {
            this.checkValidity();
            return new ImmutableLinkedHashSet<>((Set<E>) this.internalSet.clone());
        }
    }

    /**
     * Retrieves the indexed element specified.
     *
     * @param index The element to retrieve.
     *
     * @return The element requested.
     */
    public E get(final int index)
    {
        return index < 0 ? null : this.internalList.get(index);
    }

    /**
     * Retrieves the matching element specified.
     *
     * @param object The element to match.
     *
     * @return The element requested.
     */
    public E get(final E object)
    {
        return this.get(this.internalList.indexOf(object));
    }
}
