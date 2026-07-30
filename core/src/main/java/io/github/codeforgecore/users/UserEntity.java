package io.github.codeforgecore.users;

public class UserEntity {

    private String mUserId;
    private String mEmail;
    private String mUsername;
    private String mFirstName;
    private String mLastName;
    private String mPassword;

    private UserEntity(String userId, String email, String username, String firstName,
                      String lastName, String password) {
        mUserId = userId;
        mEmail = email;
        mUsername = username;
        mFirstName = firstName;
        mLastName = lastName;
        mPassword = password;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public static UserEntity create(String userId, String email, String username, String firstName,
                                    String lastName, String password) {

        return new UserEntity(userId, email, username, firstName, lastName, password);
    }
}
