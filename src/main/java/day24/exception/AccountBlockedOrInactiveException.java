package day24.exception;

public class AccountBlockedOrInactiveException extends RuntimeException {
    public AccountBlockedOrInactiveException(String message) {
        super(message);
    }
    
}
