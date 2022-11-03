package com.atlassian.jira.pm.rest;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.pm.api.ProjectConfigurationService;
import com.atlassian.jira.pm.dto.ProjectConfigurationDto;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/stageinfo")
public class StageRestController {

    private final ProjectConfigurationService service;
    private final ProjectManager projectManager;

    @Autowired
    public StageRestController(ProjectConfigurationService service,
                               @ComponentImport ProjectManager projectManager) {
        this.service = service;
        this.projectManager = projectManager;
    }

    @GET
    @Path("/{project-key}")
    @Produces({MediaType.APPLICATION_JSON})
    public ProjectConfigurationDto getByProjectKey(@PathParam("project-key") String projectKey) {
        Project project = projectManager.getProjectObjByKey(projectKey);
        return service.get(project.getId());
    }

    @GET
    @Path("/{project-key}/{issue-key}")
    @Produces({MediaType.APPLICATION_JSON})
    public ProjectConfigurationDto getByIssueKey(@PathParam("project-key") String projectKey, @PathParam("issue-key") String issueKey) {
        CustomField stagesCF = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("Stages");
        MutableIssue issueObject = ComponentAccessor.getIssueManager().getIssueObject(issueKey);
        if (issueObject != null && issueObject.getCustomFieldValue(stagesCF) != null) {
            return new ProjectConfigurationDto().setProjectId(0L).setStage(String.valueOf(issueObject.getCustomFieldValue(stagesCF)));
        }
        Project project = projectManager.getProjectObjByKey(projectKey);
        return service.get(project.getId());
    }
}
