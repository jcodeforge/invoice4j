package io.github.jcodeforge.core.networking;

import okhttp3.HttpUrl;
import org.apache.commons.validator.routines.UrlValidator;

public abstract class HttpUtils {

    public static int HTTP_CODE_OK = 200;
    public static int HTTP_CODE_CREATED = 201;

    public static boolean isValidUrl(String url) {
        return new UrlValidator().isValid(url);
    }

    public static HttpUrl toUrl(String s) {
        if (!isValidUrl(s)) {
            return null;
        }
        // URL implementation should add a trailing
        // slash (https://futurestud.io/tutorials/retrofit-2-url-handling-resolution-and-parsing)
        return HttpUrl.parse(s.replaceAll("([^/])$", "$1/"));
    }
}
