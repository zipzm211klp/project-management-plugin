AJS.$(function () {

    let url = window.location.href;
    let array = url.split('/');
    if (array[3] === "projects" && array[5] === "summary") {
        let projectKey = array[4];
        $.ajax({
            type: "GET",
            url: `${AJS.contextPath()}/rest/stage-info/1.0/stageinfo/` + projectKey,
        }).done(function (response) {
            if (response !== null && response !== "") {
                showProgressTrackersProjectScreen(response);
            }
        }).fail(function (xhr, status, message) {
            console.error(xhr)
        });
    }

    function showProgressTrackersProjectScreen(response) {
        let stage = response.stage;
        let $ol = AJS.$("<ol>").addClass("aui-progress-tracker").css("width", "100%");
        let $initiation = AJS.$("<li>").addClass("aui-progress-tracker-step").css("width", "20%");
        $initiation.append(AJS.$("<span id='initiation_id'>").text("Initiation"));
        let $planning = AJS.$("<li>").addClass("aui-progress-tracker-step").css("width", "20%");
        $planning.append(AJS.$("<span id='planning_id'>").text("Planning"));
        let $executing = AJS.$("<li>").addClass("aui-progress-tracker-step").css("width", "20%");
        $executing.append(AJS.$("<span id='executing_id'>").text("Executing"));
        let $monitoring = AJS.$("<li>").addClass("aui-progress-tracker-step").css("width", "20%");
        $monitoring.append(AJS.$("<span id='monitoring_id'>").text("Monitoring and Control"));
        let $closure = AJS.$("<li>").addClass("aui-progress-tracker-step").css("width", "20%");
        $closure.append(AJS.$("<span id='closure_id'>").text("Closure"));

        if (stage === "Initiation") {
            $initiation.addClass("aui-progress-tracker-step-current");
            setTimeout(function () {
                $("#initiation_id").text("");
                $("#initiation_id").append(AJS.$("<aui-badge style='background-color: lightblue'>").text("Initiation"));
            }, 100);
        } else if (stage === "Planning") {
            $planning.addClass("aui-progress-tracker-step-current");
            setTimeout(function () {
                $("#planning_id").text("");
                $("#planning_id").append(AJS.$("<aui-badge style='background-color: lightgreen'>").text("Planning"));
            }, 100);
        } else if (stage === "Executing") {
            $executing.addClass("aui-progress-tracker-step-current");
            setTimeout(function () {
                $("#executing_id").text("");
                $("#executing_id").append(AJS.$("<aui-badge style='background-color: lightyellow'>").text("Executing"));
            }, 100);
        } else if (stage === "Monitoring and Control") {
            $monitoring.addClass("aui-progress-tracker-step-current");
            setTimeout(function () {
                $("#monitoring_id").text("");
                $("#monitoring_id").append(AJS.$("<aui-badge style='background-color: coral'>").text("Monitoring and Control"));
            }, 100);
        } else if (stage === "Closure") {
            $closure.addClass("aui-progress-tracker-step-current");
            setTimeout(function () {
                $("#closure_id").text("");
                $("#closure_id").append(AJS.$("<aui-badge style='background-color: sandybrown'>").text("Closure"));
            }, 100);
        }

        $ol.append($initiation);
        $ol.append($planning);
        $ol.append($executing);
        $ol.append($monitoring);
        $ol.append($closure);

        AJS.$("#sidebar-page-container > div > div").after($ol);
    }
});

function showProgressTrackersIssueScreen(response) {
    let stage = response.stage;
    let $ol = AJS.$("<ol>").addClass("aui-progress-tracker").css("width", "100%");
    let $initiation = AJS.$("<li>").addClass("aui-progress-tracker-step").css("width", "20%");
    $initiation.append(AJS.$("<span id='initiation_id'>").text("Initiation"));
    let $planning = AJS.$("<li>").addClass("aui-progress-tracker-step").css("width", "20%");
    $planning.append(AJS.$("<span id='planning_id'>").text("Planning"));
    let $executing = AJS.$("<li>").addClass("aui-progress-tracker-step").css("width", "20%");
    $executing.append(AJS.$("<span id='executing_id'>").text("Executing"));
    let $monitoring = AJS.$("<li>").addClass("aui-progress-tracker-step").css("width", "20%");
    $monitoring.append(AJS.$("<span id='monitoring_id'>").text("Monitoring and Control"));
    let $closure = AJS.$("<li>").addClass("aui-progress-tracker-step").css("width", "20%");
    $closure.append(AJS.$("<span id='closure_id'>").text("Closure"));

    if (stage === "Initiation") {
        $initiation.addClass("aui-progress-tracker-step-current");
        setTimeout(function () {
            $("#initiation_id").text("");
            $("#initiation_id").append(AJS.$("<aui-badge style='background-color: lightblue'>").text("Initiation"));
        }, 100);
    } else if (stage === "Planning") {
        $planning.addClass("aui-progress-tracker-step-current");
        setTimeout(function () {
            $("#planning_id").text("");
            $("#planning_id").append(AJS.$("<aui-badge style='background-color: lightgreen'>").text("Planning"));
        }, 100);
    } else if (stage === "Executing") {
        $executing.addClass("aui-progress-tracker-step-current");
        setTimeout(function () {
            $("#executing_id").text("");
            $("#executing_id").append(AJS.$("<aui-badge style='background-color: lightyellow'>").text("Executing"));
        }, 100);
    } else if (stage === "Monitoring and Control") {
        $monitoring.addClass("aui-progress-tracker-step-current");
        setTimeout(function () {
            $("#monitoring_id").text("");
            $("#monitoring_id").append(AJS.$("<aui-badge style='background-color: coral'>").text("Monitoring and Control"));
        }, 100);
    } else if (stage === "Closure") {
        $closure.addClass("aui-progress-tracker-step-current");
        setTimeout(function () {
            $("#closure_id").text("");
            $("#closure_id").append(AJS.$("<aui-badge style='background-color: sandybrown'>").text("Closure"));
        }, 100);
    }

    $ol.append($initiation);
    $ol.append($planning);
    $ol.append($executing);
    $ol.append($monitoring);
    $ol.append($closure);

    AJS.$("#issuedetails").after($ol);
}
function getStageInfo() {
    let issueKey = $('#key-val').text();
    let projectKey = $('#key-val').text().split('-')[0];
    $.ajax({
        type: "GET",
        url: `${AJS.contextPath()}/rest/stage-info/1.0/stageinfo/` + projectKey + '/' + issueKey,
    }).done(function (response) {
        if (response !== null && response !== "") {
            showProgressTrackersIssueScreen(response);
        }
    }).fail(function (xhr, status, message) {
        console.error(xhr)
    });
}
JIRA.bind(JIRA.Events.NEW_CONTENT_ADDED, function (e, context, reason) {
    if(context[0] !== undefined && context[0].id === "details-module") {
        getStageInfo();
    }
});