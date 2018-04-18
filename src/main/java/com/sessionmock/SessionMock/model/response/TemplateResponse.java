package com.sessionmock.SessionMock.model.response;

import com.sessionmock.SessionMock.exceptions.InvalidScriptParameters;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import groovy.lang.GroovyShell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateResponse extends Response {
    private String template;
    private String script;
    private List<String> scriptParams;

    private String buildBody(String templateFile,  Map<String, Object> params){
        JtwigTemplate template = JtwigTemplate.fileTemplate(templatePath + File.separator + templateFile);
        JtwigModel model = JtwigModel.newModel(params);
        return template.render(model);
    }

    private Map<String, Object> executeScript(String script, List<String> params) throws IOException {
        return (Map<String, Object>) new GroovyShell()
                .parse(new File(scriptPath + File.separator + script))
                .invokeMethod("main", params.toArray());
    }

    private Map<String,Object> getResponseParamMap(RequestPattern requestPattern, HttpServletRequest request)
            throws InvalidScriptParameters, IOException {
        log.info("Start response data retrieving");
        List<String> scriptParams = requestPattern
                .getScriptParamPatterns(((TemplateResponse) requestPattern.getResponse()).getScriptParams())
                .stream()
                .map(pattern -> pattern.getPatternValue(request))
                .collect(Collectors.toList());
        return executeScript(((TemplateResponse) requestPattern.getResponse()).getScript(), scriptParams);
    }

    @Override
    public String getBody(HttpServletRequest request) {
        return null;
    }
}
