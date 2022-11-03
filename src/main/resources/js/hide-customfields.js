AJS.$(function () {
    function hideFields() {
        var stage = $("#customfield_10203 option:selected").text();
        if (stage === null || stage === "" || stage === undefined) {
            stage = $("#customfield_10203-val").text().trim();
            if (stage === "Initiation") {
                $("#rowForcustomfield_10201").hide();
                $("#rowForcustomfield_10202").hide();
            } else if (stage === "Planning") {
                $("#rowForcustomfield_10200").hide();
                $("#rowForcustomfield_10202").hide();
            } else if (stage === "Closure") {
                $("#rowForcustomfield_10200").hide();
                $("#rowForcustomfield_10201").hide();
            } else {
                $("#rowForcustomfield_10200").hide();
                $("#rowForcustomfield_10201").hide();
                $("#rowForcustomfield_10202").hide()
            }
        } else {
            if (stage === "Initiation") {
                $("#customfield_10201").remove();
                $("#customfield_10202").remove();
            } else if (stage === "Planning") {
                $("#customfield_10200").remove();
                $("#customfield_10202").remove();
            } else if (stage === "Closure") {
                $("#customfield_10200").remove();
                $("#customfield_10201").remove();
            } else {
                $("#customfield_10200").remove();
                $("#customfield_10201").remove();
                $("#customfield_10202").remove();
            }
        }
    }

    hideFields();

    JIRA.bind(JIRA.Events.NEW_CONTENT_ADDED, function (e, context, reason) {
        if (reason === "dialogReady") {
            hideFields();
        }
    });
});
