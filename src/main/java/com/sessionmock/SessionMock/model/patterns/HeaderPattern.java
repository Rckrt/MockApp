package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.Data;

@Data
public class HeaderPattern extends Pattern {

    public HeaderPattern() {
        patternType = PatternType.HEADER;
    }

    @Override
    public boolean validate(Object o) {
        return false;
    }
}
