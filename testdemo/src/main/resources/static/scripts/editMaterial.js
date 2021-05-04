$(document).ready(function () {

    var path = window.location.pathname;
    var ecomid = path.split("/")[3];

    var materials = [];

    refreshPlanTbl(ecomid);
    requestMaterials(ecomid, materials);


    $("#form").submit(function() {
        var plan = {
            'ecomid': ecomid,
            'code_lvl_1': materials[$("#description").val()].code,
            'description': materials[$("#description").val()].description,
            'quantity': $("#quantity").val(),
            'ht': $("#quantity").val()/100 * materials[$("#description").val()].ht,
            'ds': $("#quantity").val()/100 * materials[$("#description").val()].ds,
            'start_date': $("#start_date").val(),
            'finish_date': $("#start_date").val(),
            'parent': materials[$("#description").val()].id
        };

        savePlan(plan, ecomid);
    });

    $("#planBtn").attr('href', '/home/projects/' + ecomid + '/plan');
    


});

function savePlan(plan, id) {

    $.ajax({
        type: "post",
        contentType: "application/json",
        url: "/restapi/plan/save",
        dataType: "json",
        headers: {'X-CSRF-TOKEN': $("#csrf").val()},
        data: JSON.stringify(plan),
        success: function (result) {
            refreshPlanTbl(id);
        },
        error: function (e) {
            console.log(e);
        }
    });

}

function refreshPlanTbl(id) {
    $.ajax({
        url: "/restapi/plan/all_material_by_ecomid/" + id,
        type: "get",
        dataType: "json",
        success: function (data) {

            $("#planTbl").DataTable().destroy();
            $("#planTbl").dataTable({
                data: data,
                columns: [
                    {'data': 'id'},
                    {'data': 'description'},
                    {'data': 'start_date'},
                    {'data': 'ds'},
                    {'data': 'id',
                        'render': function (data, type, row, meta) {
                            if (type === "display") {
                                data = '<a class="btn btn-danger btn-sm"><i class="fas fa-minus"></i></a>';
                            }
                            return data;
                        }
                    }
                ]
            });
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function deletePlan(id) {

    $.ajax({
        type: "delete",
        contentType: "application/json",
        url: "/restapi/plan/delete/" + id,
        dataType: "json",
        headers: {'X-CSRF-TOKEN': $("#csrf").val()},
        data: JSON.stringify({'id': id}),
        success: function (result) {
            alert("Delete successful!");
            refreshPlanTbl(ecomid);
        },
        error: function (e) {
            console.log("Error: ", e);
        }
    });
}

function requestMaterials(id, materials) {
    $.ajax({
        url: "/restapi/material/all_by_ecomid/" + id,
        type: "get",
        dataType: "json",
        success: function (data) {
            populateMaterialList(data);

            for (var i in data) {
                materials.push(data[i]);

            };
        },
        error: function (e) {
            console.log(e);
        }
    });

}


function populateMaterialList(data) {
    for (var i in data) {
        $("#description").append("<option value='" + i + "'>" + data[i].description +"</option>");
    };

}