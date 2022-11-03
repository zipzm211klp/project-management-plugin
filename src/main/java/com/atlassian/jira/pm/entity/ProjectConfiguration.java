package com.atlassian.jira.pm.entity;

import net.java.ao.OneToOne;
import net.java.ao.RawEntity;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.PrimaryKey;
import net.java.ao.schema.Table;

@Table("PC")
public interface ProjectConfiguration extends RawEntity<Long> {

    @OneToOne
    Stage getStage();

    @NotNull
    @PrimaryKey("PROJECT_ID")
    Long getProjectId();

    void setProjectId(Long projectId);
}