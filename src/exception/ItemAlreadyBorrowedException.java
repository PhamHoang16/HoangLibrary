package exception;

public class ItemAlreadyBorrowedException extends Exception {
    public ItemAlreadyBorrowedException(String message) {
        super(message);
    }
}
