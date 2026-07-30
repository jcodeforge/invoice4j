package io.github.codeforgecore.networking.authentication;

import io.github.codeforgecore.common.settings.SettingsManager;
import io.github.codeforgecore.networking.authentication.events.LoginStateChangedEvent;
import io.github.codeforgecore.utils.JsonConverter;

public class LoginStateManager {

    public interface StateChangedListener {
        void onLoginStateChanged(LoginStateChangedEvent event);
    }

    private final SettingsManager mSettingsManager;

    public LoginStateManager(SettingsManager settingsManager) {
        mSettingsManager = settingsManager;
    }

    public void setUserSession(String userId, String userName, String userEmail) {
        mSettingsManager.setUserLoggedIn(true);
        LoggedInUserEntity loggedInUser = new LoggedInUserEntity(userEmail, userId, userName);
        mSettingsManager.setLoggedInUser(JsonConverter.toJson(loggedInUser));
    }

    public boolean isUserLoggedIn() {
        return mSettingsManager.isUserLoggedIn();
    }

    public boolean canSkipLogin() {
        LoggedInUserEntity loggedInUser = getLoggedInUser();
        if (!loggedInUser.getUserId().isEmpty()) {
            return isUserLoggedIn();
        }

        return false;
    }

    public void logout() {
        clearUserSettings();
    }

    private void clearUserSettings() {
        mSettingsManager.setUserLoggedIn(false);
        mSettingsManager.setLoggedInUser("");
        mSettingsManager.setArgumentUpdateState(0);
        mSettingsManager.setLastAppUpdateAt("");
        mSettingsManager.setAutostart(false);
    }

    public LoggedInUserEntity getLoggedInUser() {
        LoggedInUserEntity loggedInUser = JsonConverter.fromJson(mSettingsManager.getLoggedInUser(),
                LoggedInUserEntity.class);

        if (loggedInUser != null && loggedInUser.getUserId() != null
                && !loggedInUser.getUserId().isEmpty()) {
            return loggedInUser;
        } else {
            return new LoggedInUserEntity("", "", "");
        }
    }
}
