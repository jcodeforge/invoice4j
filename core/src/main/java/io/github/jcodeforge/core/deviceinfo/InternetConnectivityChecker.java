package io.github.jcodeforge.core.deviceinfo;

import io.github.jcodeforge.core.common.BackgroundThreadPoster;
import io.github.jcodeforge.core.common.UiThreadPoster;
import java.net.URI;
import javax.net.ssl.HttpsURLConnection;

/**
 * This class checks for availability of a internet connection on a device
 */
public class InternetConnectivityChecker {

    public interface InternetConnectivityCallback {
        void onInternetConnectivityChecked(boolean isConnected);
    }

    private final BackgroundThreadPoster mBackgroundThreadPoster;
    private final UiThreadPoster mUiThreadPoster;

    public InternetConnectivityChecker(BackgroundThreadPoster backgroundThreadPoster,
                                       UiThreadPoster uiThreadPoster) {
        mBackgroundThreadPoster = backgroundThreadPoster;
        mUiThreadPoster = uiThreadPoster;
    }

    public void checkInternetConnection(InternetConnectivityCallback callback) {
        mBackgroundThreadPoster.post(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpsURLConnection urlc = (HttpsURLConnection)
                            new URI("https://clients3.google.com/generate_204").toURL()
                                    .openConnection();
                    urlc.setRequestProperty("User-Agent", "java-client");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(3000);
                    urlc.connect();

                    boolean isConnected = urlc.getResponseCode() == 204 && urlc.getContentLength() == 0;

                    notifyOnInternetConnectivityChecked(callback, isConnected);


                } catch (Exception e) {
                    notifyOnInternetConnectivityChecked(callback, false);
                }
            }
        });
    }

    private void notifyOnInternetConnectivityChecked(InternetConnectivityCallback callBack,
                                                     boolean isConnected) {
        mUiThreadPoster.post(new Runnable() {
            @Override
            public void run() {
                callBack.onInternetConnectivityChecked(isConnected);
            }
        });
    }
}
