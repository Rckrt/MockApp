package com.sessionmock.SessionMock.exceptions;

import java.io.File;
import java.io.IOException;

public class SerializationException extends IOException {

    public <T> SerializationException(File file, Class<T> cls, Throwable cause) {
        super("Can't serialize file: " + file.getPath() + " to " + cls.getName(), cause);
    }
}
