package com.sessionmock.SessionMock.model.response;

import com.sessionmock.SessionMock.exceptions.InvalidScriptParameters;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import lombok.*;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sessionmock.SessionMock.model.constants.Constants.TEMPLATE_PATH;
import static com.sessionmock.SessionMock.services.ScriptExecutor.executeScript;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateResponse extends Response {
    private String template;
    private String script;
    private List<String> scriptParams;


    private String buildBody(Map<String, Object> params){
        JtwigTemplate template = JtwigTemplate.fileTemplate(TEMPLATE_PATH + File.separator + this.template);
        JtwigModel model = JtwigModel.newModel(params);
        return template.render(model);
    }

    private Map<String,Object> getResponseParamMap(RequestPattern requestPattern, HttpServletRequest request)
            throws InvalidScriptParameters, IOException {
        List<String> scriptParams = requestPattern
                .getScriptParamPatterns(this.scriptParams)
                .stream()
                .map(pattern -> pattern.getPatternValue(request))
                .collect(Collectors.toList());
        return executeScript(script, scriptParams);
    }

    @Override
    public String getBody(HttpServletRequest request, RequestPattern requestPattern) throws IOException, InvalidScriptParameters {
        return buildBody(getResponseParamMap(requestPattern, request));
    }
}
