package main.java.com.jhayes.exception;

/**
 * Created by hayesj3 on 10/4/2015.
 */
public class CommandArgsMismatchException extends IllegalArgumentException {
    public CommandArgsMismatchException() { super("Wrong Arguments for entered main.java.com.jhayes.command!"); }
    public CommandArgsMismatchException(String message) { this(message, null); }
    public CommandArgsMismatchException(String message, Throwable cause) { super(message, cause); }
}
