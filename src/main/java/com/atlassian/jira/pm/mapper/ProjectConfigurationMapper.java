package com.atlassian.jira.pm.mapper;

import com.atlassian.jira.pm.dto.ProjectConfigurationDto;
import com.atlassian.jira.pm.entity.ProjectConfiguration;
import org.springframework.stereotype.Component;

@Component
public class ProjectConfigurationMapper {
    public ProjectConfigurationDto toDto(ProjectConfiguration pc) {
        ProjectConfigurationDto projectConfigurationDto = new ProjectConfigurationDto();
        projectConfigurationDto.setProjectId(pc.getProjectId());
        projectConfigurationDto.setStage(pc.getStage().getStageName());
        return projectConfigurationDto;
    }
}
