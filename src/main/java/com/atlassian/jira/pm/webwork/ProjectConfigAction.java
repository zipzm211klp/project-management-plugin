package com.atlassian.jira.pm.webwork;

import com.atlassian.jira.bc.project.ProjectService;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.pm.dto.ProjectConfigurationDto;
import com.atlassian.jira.pm.exception.ValidationException;
import com.atlassian.jira.pm.service.ProjectConfigurationServiceImpl;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.atlassian.jira.security.Permissions.ADMINISTER;

public class ProjectConfigAction extends JiraWebActionSupport {
    private static final Logger log = LoggerFactory.getLogger(ProjectConfigAction.class);
    private final String EDIT = "edit";

    @ComponentImport
    private PermissionManager permissionManager;

    @ComponentImport
    private ProjectService projectService;

    @Autowired
    private final ProjectConfigurationServiceImpl projectConfigurationService;

    @ComponentImport
    private CustomFieldManager customFieldManager;

    public ProjectConfigAction(ProjectService projectService, PermissionManager permissionManager,
                               ProjectConfigurationServiceImpl projectConfigurationService, CustomFieldManager customFieldManager) {
        this.projectService = projectService;
        this.permissionManager = permissionManager;
        this.projectConfigurationService = projectConfigurationService;
        this.customFieldManager = customFieldManager;
    }

    private boolean isUserProjectAdmin() {
        return getGlobalPermissionManager().hasPermission(ADMINISTER, getLoggedInUser());
    }

    @Override
    public String execute() throws Exception {
        if(!isUserProjectAdmin()) {
            return ERROR;
        }
        return super.execute();
    }

    public Project getProject() {
        Long pid = Long.parseLong(getHttpRequest().getParameter("pid"));
        return projectService.getProjectById(pid).get();
    }

    private ProjectConfigurationDto convert(HttpServletRequest httpRequest) {
        Map<String, String> convertedMap = this.convertParameterMap(getHttpRequest().getParameterMap());
        ObjectMapper mapper = new ObjectMapper();
        ProjectConfigurationDto dto = mapper.convertValue(convertedMap, ProjectConfigurationDto.class);
        if (!httpRequest.getParameterMap().containsKey("stage")) {
            throw new ValidationException("Stage are mandatory!");
        }
        String stage = httpRequest.getParameter("stage");
        dto.setStage(stage);
        return dto;
    }

    @Override
    public String doDefault() {
        if(!isUserProjectAdmin()) {
            return ERROR;
        }
        HttpServletRequest httpRequest = getHttpRequest();
        if(getHttpRequest().getMethod().equals("POST")) {
            try {
                ProjectConfigurationDto dto = convert(httpRequest);
                projectConfigurationService.create(dto);
                forceRedirect(getActionName()+".jspa?pid="+getProjectId() + "&projectKey=" + getProjectKey());
            } catch (ValidationException ex) {
                setErrorMessages(Arrays.asList(ex.getMessage()));
            }
        }
        return INPUT;
    }

    public String doEdit() {
        HttpServletRequest httpRequest = getHttpRequest();
        if(getHttpRequest().getMethod().equals("POST")) {
            try {
                ProjectConfigurationDto dto = convert(httpRequest);
                projectConfigurationService.update(getProject().getId(), dto);
                forceRedirect(getActionName() + ".jspa?pid=" + getProjectId() + "&projectKey=" + getProjectKey());
            } catch (ValidationException ex) {
                setErrorMessages(Arrays.asList(ex.getMessage()));
            }
        }
        return EDIT;
    }

    public Map<String, String> convertParameterMap(Map<String, String[]> httpParams) {
        Map<String, String> convertedMap = new HashMap<>();
        for (Map.Entry<String,String[]> entry : httpParams.entrySet())
            convertedMap.put(entry.getKey(), entry.getValue()[0]);
        return convertedMap;
    }

    public String getProjectId() {
        return  getHttpRequest().getParameter("pid");
    }

    public String getProjectKey() {
        return  getHttpRequest().getParameter("projectKey");
    }

    public ProjectConfigurationServiceImpl getService() {
        return projectConfigurationService;
    }

    public CustomFieldManager getCustomFieldManager() {
        return customFieldManager;
    }
}
