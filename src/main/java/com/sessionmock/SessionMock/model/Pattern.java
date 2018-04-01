package com.sessionmock.SessionMock.model;

import lombok.Data;

@Data
public class Pattern {
    private String name;
    private String dataType;
    private boolean isIdentifier;
    private boolean isMandatory;
}
