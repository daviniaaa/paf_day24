package day24.exception;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException() {
        super();
    }
    public BankAccountNotFoundException(String message) {
        super(message);
    }
    public BankAccountNotFoundException(Throwable cause) {
        super(cause);
    }
    public BankAccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
