package enums;

public enum AuthenticationMessage {
    LOGIN_SUCCESS("login success"),
    USERNAME_NOT_FOUND("username not found"),
    WRONG_PASSWORD("wrong password");

    private final String message;

    AuthenticationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
