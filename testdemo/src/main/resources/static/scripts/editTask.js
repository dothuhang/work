
var taskData = [];
var planData = [];

$(document).ready(function () {

    var path = window.location.pathname;
    console.log(path);
    console.log($("meta[name='ctx']").attr("content"));
    var ecomid = path.split("/")[3];


    requestAllTasks(ecomid);
    requestAllPlans(ecomid);
/*
    console.log(taskData);
    console.log(planData);*/

    $("#parent").on("change", function () {
        $("#description").text($(this).text());
    })

    $("#quantity").on("keyup", function () {
        $("#quantity").attr("max", taskData[$("#parent").val()].quantity);
        $("#avai_nb_hrs").text("Available nb. hours: " + parseFloat(taskData[$("#parent").val()].quantity).toFixed(2));
    })

    $("#okPreBtn").on("click", function () {
        if ($("#predecessors").val() == "") {
            $("#predecessors:text").val($("#pre").val());
            $("#time_lag:text").val($("#timeLag").val());
        } else {
            $("#predecessors:text").val($("#predecessors").val() +"," + $("#pre").val());
            $("#time_lag:text").val($("#time_lag").val() +"," + $("#timeLag").val());
        }

    });

    $("#resetPredBtn").on("click", function () {
        $("#predecessors:text").val("");
        $("#time_lag:text").val("");
    })

    $("#form").submit(function() {

        let plan = {
            'ecomid': ecomid,
            'code_lvl_1': taskData[$("#parent").val()].code_lvl_1,
            'code_lvl_2': taskData[$("#parent").val()].code_lvl_2,
            'description': $("#description").val(),
            'quantity': $("#quantity").val(),
            'ht': $("#quantity").val() / taskData[$("#parent").val()].quantity * taskData[$("#parent").val()].ht,
            'ds': $("#quantity").val() / taskData[$("#parent").val()].quantity * taskData[$("#parent").val()].ds,
            'zone': $("#zone").val(),
            'parent': taskData[$("#parent").val()].id,
            'predecessors': $("#predecessors").val(),
            'time_lag': $("#time_lag").val()
        };

        if (!$("#idToEdit").val()) {
            plan.id = $("#idToEdit").val();
        }

        taskData[$("#parent").val()].quantity = taskData[$("#parent").val()].quantity - parseFloat($("#quantity").val());
        requestAllPlans(ecomid);
        $("#idToEdit").val(null);
        $("#addModal").modal('toggle');

    });

    $("#planBtn").attr('href', '/home/projects/' + ecomid + '/plan');

    $("#okDelBtn").on("click", function () {
        requestDeletePlan($("#idToDel").val(), $("#csrf").val());
        requestAllTasks(ecomid);
        $("#idToDel").val(null);
    });


});

function requestAllTasks(id) {
    taskData.splice(0, taskData.length);
    $.ajax({
        url: "/restapi/task/all_by_ecomid/" + id,
        type: "get",
        dataType: "json",
        success: function (data) {
            populateParentSelection(data);
            for (var i in data) {
                taskData.push(data[i]);
            }
        },
        error: function (e) {
            return e;
        }
    });
}

function requestSavePlan(plan, token) {
    $.ajax({
        type: "post",
        contentType: "application/json",
        url: "/restapi/plan/save",
        dataType: "json",
        headers: {'X-CSRF-TOKEN': token},
        data: JSON.stringify(plan),
        success: function (result) {
            return true;
        },
        error: function (e) {
            console.log(e);
            return e;
        }
    });
}

function requestAllPlans(id) {
    planData.splice(0, planData.length);
    $.ajax({
        url: "/restapi/plan/all_task_by_ecomid/" + id,
        type: "get",
        dataType: "json",
        success: function (data) {
            populatePlanTbl(data);
            $("#pre").find('option').remove().end();
            for (var i in data) {
                $("#pre").append("<option value='" + data[i].id + "'>" + data[i].description + "</option>");
                planData.push(data[i]);
            }
            return data;
        },
        error: function (e) {
            return e;
        }
    });
}

function requestDeletePlan(id, token) {
    $.ajax({
        type: "delete",
        contentType: "application/json",
        url: "/restapi/plan/delete/" + id,
        dataType: "json",
        headers: {'X-CSRF-TOKEN': token},
        data: JSON.stringify({'id': id}),
        success: function (result) {
            return true;
        },
        error: function (e) {
            console.log(e);
            return false;
        }
    });
}

function populateParentSelection(data) {
    for (var i in data) {
        $("#parent").append("<option value='" + i +"'>" + data[i].description + "</option>");
    }
    $("#parent").selectedIndex = 1;
}

function populatePlanTbl(data) {
    $("#planTbl").DataTable().destroy();
    $("#planTbl").dataTable({
        data: data,
        columns: [
            {'data': 'id'},
            {'data': 'description'},
            {'data': 'quantity'},
            {'data': 'zone'},
            {'data': 'parent'},
            {'data': 'predecessors'},
            {'data': 'time_lag'},
            {'data': 'id',
                'render': function (data, type, row, meta) {
                    if (type === "display") {
                        data = "<div class='btn-group'>" +
                            "<a class='btn btn-primary' onclick='setEditId(" + data + ", " + row + ")' data-toggle='modal' href='#addModal'><i class='fas fa-edit'></i></a>"+
                            "<a class='btn btn-danger' onclick='setDelId(" + data + ", " + row + ")' data-toggle='modal' href='#delModal'><i class='fas fa-minus'></i></a>"+
                            "</div>";
                    }
                    return data;
                }
            }
        ]
    });
}

function setEditId(id, idx) {
    $("#idToEdit").val(id);
    $("#description").val(planData[idx].description);
    $("#zone").val(planData[idx].zone);
    $("#parent").val(planData[idx].parent);
    $("#quantity").val(planData[idx].quantity);
    $("#quantity").attr("max", taskData[planData[idx]].quantity);
    $("#predecessors").val(planData[idx].predecessors);
    $("#time_lag").val(planData[idx].time_lag);
    $("#addModal").show();
}

function setDelId(id, idx) {
    $("#idToDel").val(id);
}
