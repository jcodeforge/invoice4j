package io.github.jcodeforge.core.networking.authentication;

import java.io.IOException;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class AuthHeadersInterceptor implements Interceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String PREFIX_API_KEY = "ApiKey";

    private static final String BASIC_AUTHENTICATION_USER = "winfuhr";

    private final AuthHeadersCredentials mCredentials;

    public AuthHeadersInterceptor(AuthHeadersCredentials credentials) {
        mCredentials = credentials;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request;
        if (chain.request().url().toString().contains("/files")) {
            request = buildNewRequestWithBasicHeaders(chain.request());

        } else {
            request = buildNewRequestWithStandardHeaders(chain.request());
        }

        return chain.proceed(request);
    }

    private Request buildNewRequestWithStandardHeaders(Request request) {
        return request
                .newBuilder()
                .addHeader(AUTHORIZATION_HEADER, PREFIX_API_KEY + " " + mCredentials.getApikey())
                .build();
    }

    private Request buildNewRequestWithBasicHeaders(Request request) {
        return request
                .newBuilder()
                .addHeader(AUTHORIZATION_HEADER, Credentials.basic(BASIC_AUTHENTICATION_USER,
                        mCredentials.getBasicAuthToken()))
                .build();
    }

    public static class AuthHeadersCredentials {

        private final String mApikey;
        private final String mBasicAuthToken;

        public AuthHeadersCredentials(String apikey, String basicAuthToken) {
            mApikey = apikey;
            mBasicAuthToken = basicAuthToken;
        }

        public String getApikey() {
            return mApikey;
        }

        public String getBasicAuthToken() {
            return mBasicAuthToken;
        }
    }
}