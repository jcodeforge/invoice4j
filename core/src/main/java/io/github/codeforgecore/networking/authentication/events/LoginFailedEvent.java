package io.github.codeforgecore.networking.authentication.events;

public class LoginFailedEvent extends LoginStateChangedEvent {

    private final String mMessage;

    public LoginFailedEvent(String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }
}
