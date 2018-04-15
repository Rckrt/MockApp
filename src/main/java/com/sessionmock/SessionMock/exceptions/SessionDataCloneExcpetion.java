package com.sessionmock.SessionMock.exceptions;

public class SessionDataCloneExcpetion extends Exception{

    public SessionDataCloneExcpetion(String message) {
        super(message);
    }

    public SessionDataCloneExcpetion() {
        super();
    }

    public SessionDataCloneExcpetion(Throwable cause) {
        super(cause);
    }

    public SessionDataCloneExcpetion(String message, Throwable cause) {
        super(message, cause);
    }
}
