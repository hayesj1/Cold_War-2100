package main.java.com.jhayes.exception;

/**
 * Created by hayesj3 on 10/4/2015.
 */
public class InvalidCommandException extends Exception {
    public InvalidCommandException() { super("Command does not exist!"); }
    public InvalidCommandException(String message) { this(message, null); }
    public InvalidCommandException(String message, Throwable cause) { super(message, cause); }

}
