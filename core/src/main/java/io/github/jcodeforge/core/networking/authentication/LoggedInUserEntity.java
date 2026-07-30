package io.github.jcodeforge.core.networking.authentication;

public class LoggedInUserEntity {

    private final String mEmail;
    private final String mUserId;
    private final String mUserName;

    public LoggedInUserEntity(String email, String userId, String userName) {
        mEmail = email;
        mUserId = userId;
        mUserName = userName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getUserName() {
        return mUserName;
    }
}
