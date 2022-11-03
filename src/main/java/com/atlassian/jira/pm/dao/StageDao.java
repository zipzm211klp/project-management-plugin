package com.atlassian.jira.pm.dao;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.pm.entity.ProjectConfiguration;
import com.atlassian.jira.pm.entity.Stage;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StageDao {
    @ComponentImport
    @Autowired
    private ActiveObjects ao;

    public Stage add(ProjectConfiguration projectConfiguration, String stageName) {
        Stage stage = ao.create(Stage.class);
        stage.setProjectConfiguration(projectConfiguration);
        stage.setStageName(stageName);
        stage.save();
        return stage;
    }

    public void delete(Stage stage) {
        ao.delete(stage);
    }
}
