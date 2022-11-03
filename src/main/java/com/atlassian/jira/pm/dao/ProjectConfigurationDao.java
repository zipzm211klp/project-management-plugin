package com.atlassian.jira.pm.dao;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.pm.entity.ProjectConfiguration;
import com.atlassian.jira.pm.entity.Stage;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import net.java.ao.DBParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectConfigurationDao {
    @ComponentImport
    @Autowired
    private final ActiveObjects ao;

    @Autowired
    public ProjectConfigurationDao(ActiveObjects ao) {
        this.ao = ao;
    }

    public ProjectConfiguration add(Long projectId) {
        ProjectConfiguration projectConfiguration = ao.create(ProjectConfiguration.class,
                new DBParam("PROJECT_ID", projectId));
        projectConfiguration.save();
        return projectConfiguration;
    }

    public ProjectConfiguration update(ProjectConfiguration projectConfiguration) {
        projectConfiguration.save();
        return projectConfiguration;
    }

    public ProjectConfiguration get(Long projectConfigurationId) {
        return ao.get(ProjectConfiguration.class, projectConfigurationId);
    }

    public void delete(ProjectConfiguration projectConfiguration) {
        deleteStage(projectConfiguration);
        ao.delete(projectConfiguration);
    }

    public void deleteStage(ProjectConfiguration projectConfiguration) {
        Stage stage = projectConfiguration.getStage();
        ao.delete(stage);
    }

    public ProjectConfiguration[] all() {
        return ao.find(ProjectConfiguration.class);
    }
}
