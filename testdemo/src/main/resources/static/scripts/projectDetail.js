
$(document).ready(function () {

    var path = window.location.pathname;
    var ecomid = path.split("/")[3];

    var ecomData = {};

    $("#editBtn").on("click", function () {
        $("#form input").attr('disabled', false);
    });

    requestEcom(ecomid);
    requestMaterial(ecomid);
    requestTasks(ecomid);

    $("#editTaskBtn").prop("href", "/home/projects/"+ ecomid +"/edit_task");
    $("#editMaterialBtn").prop("href", "/home/projects/"+ ecomid +"/edit_material");

    $("#okBtn").on("click", function () {
        var data = {
            'id': id,
            'start_date_chantier': $("#start_date_chantier").val(),
            'finish_date_chantier': $("#finish_date_chantier").val(),
            'acompte_date': $("#acompte_date").val(),
            'acompte_pct': $("#acompte_pct").val(),
            'rembourse_min': $("#rembourse_min").val(),
            'rembourse_max': $("#rembourse_max").val(),
            'retenue': $("#retenue").val(),
            'penalty': $("#penalty").val(),
        };

        var mess = requestEditEcom(ecomid, $("#csrf").val());
    });

});

function requestEcom(id) {
    $.ajax({
        type: "get",
        contentType: "application/json",
        url: "/restapi/ecom/" + id,
        dataType: "json",
        success: function (data) {

            $("#ecomSum").append("<tr>")
                .append("<td>Id:</td>").append("<td>" + data.id + "</td>")
                .append("<td>Client:</td>").append("<td>" + data.client + "</td>")
                .append("</tr><tr>")
                .append("<td>Description:</td>").append("<td colspan='3'>" + data.description + "</td>")
                .append("</tr><tr>")
                .append("<td>Montant HT:</td>").append("<td>" + data.ht + "</td>")
                .append("<td>Marge:</td>").append("<td>" + parseFloat( (data.ht/data.ds-1)*100).toFixed(2) + " %</td>")
                .append("</tr><tr>");

            $("#start_date_chantier").val(data.start_date_chantier);
            $("#finish_date_chantier").val(data.finish_date_chantier);
            $("#acompte_date").val(data.acompte_date);
            $("#acompte_pct").val(data.acompte_pct);
            $("#rembourse_min").val(data.rembourse_min);
            $("#rembourse_max").val(data.rembourse_max);
            $("#retenue").val(data.retenue);
            $("#penalty").val(data.penalty);

        },
        error: function (e) {
            console.log("Error: ", e);
        }
    });

}

function requestEditEcom(data, token) {

    $.ajax({
        type: "put",
        contentType: "application/json",
        url: "/restapi/ecom/" + id + "/update",
        dataType: "json",
        headers: {'X-CSRF-TOKEN': token},
        data: JSON.stringify(data),
        success: function (result) {
            return true;
        },
        error: function (e) {
            return false;
        }
    });
}

function requestTasks(id) {
    $.ajax({
        url: "/restapi/task/all_by_ecomid/" + id,
        type: "get",
        dataType: "json",
        success: function (data) {
            populateTaskList(data)
        },
        error: function (e) {
            return e;
        }
    });
}

function requestMaterial(id) {
    $.ajax({
        url: "/restapi/material/all_by_ecomid/" + id,
        type: "get",
        dataType: "json",
        success: function (data) {
            populateMaterialList(data);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function populateMaterialList(data) {
    for (var i in data) {
        $("#materialList").append("<tr>")
            .append("<td>" + data[i].description + "</td>")
    };

    $("#materialList").append("</tr>");
}

function populateTaskList(data) {
    for (var i in data) {
        $("#taskList").append("<tr>")
            .append("<td>" + data[i].description + "</td>")
    };

    $("#taskList").append("</tr>");
}

