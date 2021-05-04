$(document).ready(function(){

   loadMyTbl();

});

function loadMyTbl() {
   $.ajax({
      url: "/restapi/ecom/all",
      type: "get",
      dataType: "json",
      success: function (data) {

         $("#myTable").DataTable().destroy();
         $("#myTable").dataTable({
            data: data,
            columns: [
               {'data': 'id',
                  'render': function (data, type, row, meta) {
                     if (type === "display") {
                        data = '<a href="/home/projects/' + data + '">' + data + '</a>';
                     }
                     return data;
                  }
               },
               {'data': 'description'},
               {'data': 'client'}

            ]
         });
      },
      error: function (e) {
         console.log("error: " + JSON.stringify(e));
      }
   });


}



