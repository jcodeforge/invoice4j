package io.github.jcodeforge.core.utils;

public abstract class BuildConfig {

    public static final String VERSION_NAME =
            loadProperty("git.properties", "git.commit.id.abbrev", "");

    public static final int VERSION_NUMBER = Integer.parseInt(
            loadProperty("git.properties", "git.total.commit.count", "")
    );

    public static final boolean DEBUG = Boolean.parseBoolean(
            loadProperty("env.properties", "app.env.debug", "")
    );

    public static final String ENV =
            loadProperty("env.properties", "app.env", "");

    public static final String ROOT_URL =
            loadProperty("env.properties", "app.root.url", "");

    public static final String SERVER_APIKEY =
            loadProperty("env.properties", "app.env.apikey", "");

    public static final String PACKAGE_URL =
            loadProperty("env.properties", "app.package.url", "");

    public static final String EMAIL_SERVER_USERNAME =
            loadProperty("env.properties", "email.server.username", "");

    public static final String EMAIL_SERVER_PASSWORD =
            loadProperty("env.properties", "email.server.password", "");

    private static String loadProperty(String res, String key, String defaultValue) {
        return UtilMethods.loadProperties(res).getProperty(key, defaultValue);
    }

}
