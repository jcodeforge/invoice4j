package io.github.jcodeforge.core.common;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BackgroundThreadPoster {

    private static final int CORE_THREADS = 2;
    private static final long KEEP_ALIVE_SECONDS = 30L;

    private final ThreadPoolExecutor mThreadPoolExecutor;

    public BackgroundThreadPoster() {
        mThreadPoolExecutor = new ThreadPoolExecutor(
                CORE_THREADS,
                Integer.MAX_VALUE,
                KEEP_ALIVE_SECONDS,
                TimeUnit.SECONDS,
                new SynchronousQueue<>()
        );
    }

    /**
     * Execute {@link Runnable} on a random background thread.
     * @param runnable {@link Runnable} instance containing the code that should be executed
     */
    public final void post(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    /**
     * Get the underlying {@link ThreadPoolExecutor}.
     * In general, this method shouldn't be used and is provided only for the purpose of
     * integration with existing libraries and frameworks.
     */
    protected final ThreadPoolExecutor getThreadPoolExecutor() {
        return mThreadPoolExecutor;
    }

    /**
     * Get the underlying {@link ThreadFactory}.
     * In general, this method shouldn't be used and is provided only for the purpose of
     * integration with existing libraries and frameworks.
     */
    protected final ThreadFactory getThreadFactory() {
        return getThreadPoolExecutor().getThreadFactory();
    }
}
