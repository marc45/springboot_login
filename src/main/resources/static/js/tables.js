table = $('#user-table').DataTable({
    "ajax": {
        "type": "GET",
        "url": "/api/users",
        "dataSrc": ""
    },
    "bJQueryUI": true,

    "info": false,
    dom: "<'row'<'col-sm-3'l><'col-sm-3'f><'col-sm-6'p»" +
        "<'row'<'col-sm-12'tr»" +
        "<'row'<'col-sm-5'i><'col-sm-7'p»",
    "lengthMenu": [2,5, 10, 20, 100],
    "pageLength": 100,
    "processing": true,
    "serverSide": true,
    "scrollX": true,
    'fnCreatedRow': function (nRow, mData, iDataIndex) {
//$(nRow).attr('id', aData._id); // or whatever you choose to set as the id
        console.log(mData);
    },
    'fnCreatedRow': function (nRow, aData, iDataIndex) {
        $(nRow).attr('data-id', aData.id);

    },
    "aoColumns": [
        {"mData": "fullname"},
        {"mData": "email"},
        {"mData": "id"},
        {"mData": "password"},
        {
            "data": null,
            "fnCreatedCell": function (nTd, sData) {

                $(nTd).html("<button class='btn btn-info btn-enab'>" +sData.enabled+ "</button>");
            }
        },

        {
            "data": null,
            "fnCreatedCell": function (nTd, sData) {
                var roles = "";
                $(sData.roles).each(function () {
                    roles += this.role
                });
                $(nTd).html(roles);
            }
        },


        {
            "data": null,
            "fnCreatedCell": function (nTd) {
                $(nTd).html("<button class='btn btn-danger btn-remove'>Delete</button>");
            }}

    ]
});



$(document).on('click', '.btn-remove', function (e) {
    e.preventDefault();
//Load client information
    var id = $(this).closest("tr").attr("data-id")
// console.log(id);
    $.ajax({
        async: false,
        type: "delete",
        contentType: false,
        processData: false,
        url: "/api/users/delete/" + id,
        cache: false,
        dataType: "text",
        beforeSend: function (xhr) {
            $(".btn-remove").prop("disabled", true);

        },
        success: function (dataResponse) {
//collectLinks();
            table
                .row($(this).parents('tr'))
                .remove()
                .draw();
            $(".btn-remove").prop("disabled", false);

        },
        error: function (dataResponse) {
// showDangerToast("Something went wrong");
            $(".btn-remove").prop("disabled", false);


        }
    });
});

$(document).on('click', '.btn-enab', function (e) {
    e.preventDefault();
//Load client information
    var id = $(this).closest("tr").attr("data-id")
// console.log(id);
    $.ajax({
        async: false,
        type: "post",
        contentType: false,
        processData: false,
        url: "/api/users/update/state/" + id,
        cache: false,
        dataType: "text",
        beforeSend: function (xhr) {
            $(".btn-enab").prop("disabled", true);

        },
        success: function (dataResponse) {
//collectLinks();
            table
                .row($(this).parents('tr'))
                .remove()
                .draw();
            $(".btn-enab").prop("disabled", false);

        },
        error: function (dataResponse) {
// showDangerToast("Something went wrong");
            $(".btn-enab").prop("disabled", false);


        }
    });
});