package com.atlassian.jira.pm.eventListeners;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventDispatchOption;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.index.IndexException;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.extern.slf4j.Slf4j;
import org.ofbiz.core.entity.GenericEntityException;
import org.ofbiz.core.entity.GenericValue;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@ExportAsService
@Component
@Slf4j
public class StageListener implements InitializingBean, DisposableBean {
    @ComponentImport
    private final EventPublisher eventPublisher;

    @Autowired
    public StageListener(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void destroy() throws Exception {
        eventPublisher.unregister(this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        eventPublisher.register(this);
    }

    @SuppressWarnings("unused")
    @EventListener
    public void onIssueEvent(IssueEvent event) throws GenericEntityException, IndexException {
        List<GenericValue> stagesChanges = getStagesChanges(event);
        CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();
        CustomField projectPhaseGuidelineCF = customFieldManager.getCustomFieldObjectByName("Project phase guideline");
        Issue issue = event.getIssue();
        MutableIssue mutableIssue = ComponentAccessor.getIssueManager().getIssueObject(issue.getKey());
        ApplicationUser loggedInUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
        if (stagesChanges != null && stagesChanges.size() > 0) {
            for (GenericValue change : stagesChanges) {
                if (change.getString("newstring").contains("Initiation")) {
                    mutableIssue.setCustomFieldValue(projectPhaseGuidelineCF, "https://jirafordiploma.atlassian.net/l/cp/u0f1xUZF");
                    ComponentAccessor.getIssueManager().updateIssue(loggedInUser, mutableIssue, EventDispatchOption.ISSUE_UPDATED, false);
                } else if (change.getString("newstring").contains("Planning")) {
                    mutableIssue.setCustomFieldValue(projectPhaseGuidelineCF, "https://jirafordiploma.atlassian.net/l/cp/rmbPa4y2");
                    ComponentAccessor.getIssueManager().updateIssue(loggedInUser, mutableIssue, EventDispatchOption.ISSUE_UPDATED, false);
                } else if (change.getString("newstring").contains("Executing")) {
                    mutableIssue.setCustomFieldValue(projectPhaseGuidelineCF, "https://jirafordiploma.atlassian.net/l/cp/gpcZnUpz");
                    ComponentAccessor.getIssueManager().updateIssue(loggedInUser, mutableIssue, EventDispatchOption.ISSUE_UPDATED, false);
                } else if (change.getString("newstring").contains("Monitoring and Control")) {
                    mutableIssue.setCustomFieldValue(projectPhaseGuidelineCF, "https://jirafordiploma.atlassian.net/l/cp/Kzn8GS1E");
                    ComponentAccessor.getIssueManager().updateIssue(loggedInUser, mutableIssue, EventDispatchOption.ISSUE_UPDATED, false);
                } else if (change.getString("newstring").contains("Closure")) {
                    mutableIssue.setCustomFieldValue(projectPhaseGuidelineCF, "https://jirafordiploma.atlassian.net/l/cp/2gLBHqVv");
                    ComponentAccessor.getIssueManager().updateIssue(loggedInUser, mutableIssue, EventDispatchOption.ISSUE_UPDATED, false);
                }
            }
        }
    }

    private List<GenericValue> getStagesChanges(IssueEvent event) throws GenericEntityException {
        List<GenericValue> result = null;
        if (event.getChangeLog() != null) {
            List<GenericValue> changesAll = event.getChangeLog().getRelated("ChildChangeItem");
            result = changesAll.stream().filter(genericValue -> genericValue.get("field").toString().equals("Stages")).collect(Collectors.toList());
        }
        return result;
    }
}
