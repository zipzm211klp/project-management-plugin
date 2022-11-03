package com.atlassian.jira.pm.entity;

import net.java.ao.Entity;
import net.java.ao.schema.Table;

@Table("STAGE")
public interface Stage extends Entity {
    String getStageName();

    void setStageName(String stage);

    void setProjectConfiguration(ProjectConfiguration pc);
}