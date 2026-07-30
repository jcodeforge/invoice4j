package io.github.codeforgecore.screens.common.mvcviews;

/**
 * This interface corresponds to MVC views that need to notify MVC controllers of input events
 */
public interface ObservableViewMvc<LISTENER_CLASS> extends ViewMvc {

    /**
     * Register a listener that will be notified of any input events performed on this MVC view
     */
    void registerListener(LISTENER_CLASS listener);

    /**
     * Unregister a previously registered listener. Does nothing if the listener wasn't registered.
     */
    void unregisterListener(LISTENER_CLASS listener);
}
