package io.github.codeforgecore.common.settings;

import java.util.prefs.Preferences;

// Todo Encapsulate the preference-put and get methods with a preference editor
public class SettingsManager {

    private static final String PREF_KEY_LOGGED_IN = "LOGGED_IN";
    private static final String PREF_KEY_LOGGED_IN_USER = "LOGGED_IN_USER";
    private static final String PREF_KEY_ARGUMENT_UPDATE_STATE = "ARGUMENT_UPDATE_STATE";
    private static final String PREF_KEY_LAST_APP_UPDATE_AT = "LAST_APP_UPDATE_AT";
    private static final String PREF_KEY_APP_AUTOSTART = "APP_AUTOSTART";
    private static final String PREF_KEY_USER_NAME = "USER_NAME";
    private static final String PREF_KEY_PASSWORD = "PASSWORD";
    private static final String PREF_KEY_FILES_DIR = "FILES_DIR";

    private final Preferences mPreferences;

    public SettingsManager(Preferences preferences) {
        mPreferences = preferences;
    }

    public void setUserLoggedIn(boolean value) {
        setStringPref(PREF_KEY_LOGGED_IN, String.valueOf(value));
    }

    public boolean isUserLoggedIn() {
        return Boolean.parseBoolean(getStringPref(PREF_KEY_LOGGED_IN));
    }

    public void setLoggedInUser(String value) {
        setStringPref(PREF_KEY_LOGGED_IN_USER, value);
    }

    public String getLoggedInUser() {
        return getStringPref(PREF_KEY_LOGGED_IN_USER);
    }

    public void setArgumentUpdateState(int value) {
        setStringPref(PREF_KEY_ARGUMENT_UPDATE_STATE, String.valueOf(value));
    }

    public int getArgumentUpdateState() {
        String pref = getStringPref(PREF_KEY_ARGUMENT_UPDATE_STATE);
        return !pref.isEmpty() ? Integer.parseInt(pref) : 0;
    }

    public void setLastAppUpdateAt(String value) {
        setStringPref(PREF_KEY_LAST_APP_UPDATE_AT, String.valueOf(value));
    }

    public String getLastAppUpdateAt() {
        return getStringPref(PREF_KEY_LAST_APP_UPDATE_AT);
    }

    public void setAutostart(boolean value) {
        setStringPref(PREF_KEY_APP_AUTOSTART, String.valueOf(value));
    }

    public boolean isAutostartEnabled() {
        return Boolean.parseBoolean(getStringPref(PREF_KEY_APP_AUTOSTART));
    }

    public String getUserName() {
        return getStringPref(PREF_KEY_USER_NAME);
    }

    public void setUserName(String value) {
        setStringPref(PREF_KEY_USER_NAME, value);
    }

    public String getPassword() {
        return getStringPref(PREF_KEY_PASSWORD);
    }

    public void setPassword(String value) {
        setStringPref(PREF_KEY_PASSWORD, value);
    }

    public String getFilesDir() {
        return getStringPref(PREF_KEY_FILES_DIR);
    }

    public void setFilesDir(String value) {
        setStringPref(PREF_KEY_FILES_DIR, value);
    }

    public void setStringPref(String key, String value) {
        mPreferences.put(key, value);
    }

    public String getStringPref(String key) {
        return mPreferences.get(key, "");
    }
}
