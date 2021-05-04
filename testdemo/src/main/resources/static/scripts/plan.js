var dataPlan = [];
var materialData = [];
var resources = [];

var path = window.location.pathname;
var ecomid = path.split("/")[3];

$("document").ready(function () {
    requestFinance(ecomid);
    requestPlans(ecomid);
    requestMaterials(ecomid);
    requestResources();

    $("#taskBtn").on("click", function () {
        $("#finish_date").prop('disabled', false);
        $("#selectedPlan").find('option').remove().end();
        $("#selectedPlan").attr("data-type", "task");
        for (var i in dataPlan) {
            $("#selectedPlan").append("<option value='"+ i +"'>"+ dataPlan[i].description +"</option>");

        }
    });

    $("#materialBtn").on("click", function () {
        $("#finish_date").prop('disabled', true);
        $("#selectedPlan").find('option').remove().end();
        $("#selectedPlan").attr("data-type", "material");
        for (var i in materialData) {
            $("#selectedPlan").append("<option value='"+ i +"'>"+ materialData[i].description +"</option>");
        }
    });

    $("#selectedPlan").on("change", function () {
        /*
        $("#start_date").val(dataPlan[$(this).val()].start_date);
        $("#finish_date").val(dataPlan[$(this).val()].finish_date);*/
        $("#resources div").remove().end();
        if ($(this).attr("data-type") == "task") {
            for (var i in resources) {
                if (resources[i].skill == dataPlan[$(this).val()].code_lvl_2) {
                    $("#resources").append("<div className='form-check'>" +
                        "<label className='form-check-label'>" +
                        "<input type='checkbox' className='form-check-input'/>"+ resources[i].description +
                        "</label></div>");
                }
            }
        } else {
            for (var i in resources) {
                if (resources[i].skill == materialData[$(this).val()].code) {
                    $("#resources").append("<div className='form-check'>" +
                        "<label className='form-check-label'>" +
                        "<input type='checkbox' className='form-check-input'/>"+ resources[i].description +
                        "</label></div>");
                }
            }

        }

    });


});

function requestPlans(id) {
    dataPlan.splice(0, dataPlan.length);
    $.ajax({
        url: "/restapi/plan/all_task_by_ecomid/" + id,
        type: "get",
        dataType: "json",
        success: function (data) {
            for (var i in data) {
                dataPlan.push(data[i]);
            }
        },
        error: function (e) {
            return e;
        }
    });

}

function requestResources() {
    resources.splice(0, resources.length);
    $.ajax({
        url: "/restapi/resources/all",
        type: "get",
        dataType: "json",
        success: function (data) {
            for (var i in data) {
                resources.push(data[i]);
            }
        },
        error: function (e) {
            return e;
        }
    });

}

function requestMaterials(id) {
    materialData.splice(0, materialData.length);
    $.ajax({
        url: "/restapi/material/all_by_ecomid/" + id,
        type: "get",
        dataType: "json",
        success: function (data) {

            for (var i in data) {
                materialData.push(data[i]);

            };
        },
        error: function (e) {
            console.log(e);
        }
    });

}

function requestFinance(id) {

    $.ajax({
        url: "/restapi/finance/all_by_ecomid/" + id,
        type: "get",
        dataType: "json",
        success: function (data) {
            plotChart(data);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function plotChart(data) {
    var date = [];
    var cost = [];
    var facture = [];
    var state = []
    for (var i in data) {
        date.push(data[i].date),
        cost.push(data[i].cost),
        facture.push(data[i].facture),
        state.push(data[i].state)
    }

    var chartdata = {
        labels: date,
        datasets: [
            {
                label: 'Financial State',
                data: state,
                fill: false,
                borderColor: 'blue',
                lineTension: 0,
                borderDash: []//[5, 15],
            },
            {
                label: 'Cumul Cost',
                data: cost,
                fill: false,
                borderColor: 'red',
                lineTension: 0,
                borderDash: []//[5, 15],
            },
            {
                label: 'Cumul Facture',
                data: facture,
                fill: false,
                borderColor: 'black',
                lineTension: 0,
                borderDash: []//[5, 15],
            }
        ]
    }
    var c = $("#myChart")[0].getContext('2d');
    new Chart(c, {
        type: 'line',
        data: chartdata
    });
}

function savePlan(data, token) {
    $.ajax({
        type: "post",
        contentType: "application/json",
        url: "/restapi/plan/save",
        dataType: "json",
        headers: {'X-CSRF-TOKEN': token},
        data: JSON.stringify(plan),
        success: function (result) {
            console.log(result);
            return true;
        },
        error: function (e) {
            console.log(e);
            return false;
        }
    });

}
