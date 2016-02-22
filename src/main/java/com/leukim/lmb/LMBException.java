package com.leukim.lmb;

/**
 * This exception class just serves as a way to distinguish
 * bot exceptions from normal code exceptions.
 *
 * Created by miquel on 21/2/16.
 */
public class LMBException extends Exception {
    public LMBException() {
    }

    public LMBException(String message) {
        super(message);
    }

    public LMBException(String message, Throwable cause) {
        super(message, cause);
    }

    public LMBException(Throwable cause) {
        super(cause);
    }
}
