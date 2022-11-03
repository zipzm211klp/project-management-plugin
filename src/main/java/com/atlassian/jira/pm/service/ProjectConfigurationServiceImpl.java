package com.atlassian.jira.pm.service;

import com.atlassian.jira.pm.api.ProjectConfigurationService;
import com.atlassian.jira.pm.dao.ProjectConfigurationDao;
import com.atlassian.jira.pm.dao.StageDao;
import com.atlassian.jira.pm.dto.ProjectConfigurationDto;
import com.atlassian.jira.pm.entity.ProjectConfiguration;
import com.atlassian.jira.pm.mapper.ProjectConfigurationMapper;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExportAsService
@Service
public class ProjectConfigurationServiceImpl implements ProjectConfigurationService {
    private ProjectConfigurationDao projectConfigurationDao;

    private StageDao stageDao;

    private ProjectConfigurationMapper mapper;

    @Autowired
    private ProjectConfigurationServiceImpl(ProjectConfigurationDao projectConfigurationDao,
                                            StageDao stageDao,
                                            ProjectConfigurationMapper mapper) {
        this.stageDao = stageDao;
        this.projectConfigurationDao = projectConfigurationDao;
        this.mapper = mapper;
    }

    public ProjectConfigurationDto create(ProjectConfigurationDto dto) {
        ProjectConfiguration pc = projectConfigurationDao.add(dto.getProjectId());
        stageDao.add(pc, dto.getStage());
        return mapper.toDto(pc);
    }

    public ProjectConfigurationDto update(Long projectId, ProjectConfigurationDto dto) {
        ProjectConfiguration pc = projectConfigurationDao.get(projectId);
        ProjectConfiguration updatedPC = projectConfigurationDao.update(pc);
        projectConfigurationDao.deleteStage(pc);
        stageDao.add(pc, dto.getStage());
        return mapper.toDto(updatedPC);
    }

    public ProjectConfigurationDto get(Long projectId) {
        if (projectConfigurationDao.get(projectId) == null) {
            return null;
        }
        return mapper.toDto(projectConfigurationDao.get(projectId));
    }

    public void delete(Long projectId) {
        ProjectConfiguration pc = projectConfigurationDao.get(projectId);
        projectConfigurationDao.delete(pc);
    }

    public List<ProjectConfigurationDto> getAll() {
        return Arrays.stream(projectConfigurationDao.all()).map(mapper::toDto).collect(Collectors.toList());
    }
}
