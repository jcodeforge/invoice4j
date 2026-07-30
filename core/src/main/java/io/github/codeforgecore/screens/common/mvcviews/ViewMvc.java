package io.github.codeforgecore.screens.common.mvcviews;

import java.awt.*;
import java.util.Map;

public interface ViewMvc {

    Container getRootView();

    void setRootView(Container rootView);
    /**
     * This method aggregates all the information about the state of this MVC View into Map object.
     */
    Map<String, Object> getViewState();
}
