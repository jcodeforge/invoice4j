package io.github.jcodeforge.core.screens.common.mvcviews;

import io.github.jcodeforge.core.localcachedata.BaseObservable;
import java.awt.*;

public abstract class BaseViewMvc<ListenerType> extends BaseObservable<ListenerType>
        implements ObservableViewMvc<ListenerType> {

    private Container mRootView;

    @Override
    public Container getRootView() {
        return mRootView;
    }

    @Override
    public void setRootView(Container rootView) {
        mRootView = rootView;
    }
}
