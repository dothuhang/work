var path = $("meta[name='ctx']").attr("content");

$(document).ready(function () {

    $("#uploadForm").submit(function () {
        $.ajax({
            type: "post",
            url: "/restapi/ecom/uploadcsv",
            headers: {'X-CSRF-TOKEN': $("#csrf").val()},
            data: new FormData($(this)[0]),
            cache: false,
            contentType: false,
            enctype: "multipart/form-data",
            processData: false,
            success: function (data) {
                console.log(data);
            },
            error: function (e) {
                console.log(e);
            }
        });
    })
});