var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));

$(document).ready(function () {
    var columnDefs = [
        {
            data: "id",
            title: "Id",
            type: "readonly"
        },
        {
            data: "firstName",
            title: "FirstName"
        },
        {
            data: "lastName",
            title: "LastName"
        },

        {
            data: "middleName",
            title: "MiddleName"
        },
        {
            data: "mobilePhone",
            title: "MobilePhone"
        },
        {
            data: "homePhone",
            title: "HomePhone"
        },
        {
            data: "address",
            title: "Address"

        },
        {
            data: "email",
            title: "Email"

        }
    ];

    var myTable;

    // local URL's are not allowed
    // var url_ws_mock_get = './mock_svc_load.json';
    // var url_ws_mock_ok = './mock_svc_ok.json';
    // var url_ws_mock_get = contextPath + '/js/mock_svc_load.json';
    var url_ws_mock_get = contextPath + '/user/contacts';
    var url_ws_mock_ok = contextPath + '/js/mock_svc_ok.json';

    myTable = $('#contactstbl').DataTable({
        "sPaginationType": "full_numbers",
        "ajax": {
            url: "/contacts/user",
            type: "GET",
            dataSrc: "",
            dataType: "json"
        },
        columns: columnDefs,
        dom: 'Bfrtip',        // Needs button container
        select: 'single',
        responsive: true,
        altEditor: true,     // Enable altEditor
        buttons: [{
            text: 'Add',
            name: 'add'        // do not change name
        },
            {
                extend: 'selected', // Bind to Selected row
                text: 'Edit',
                name: 'edit'        // do not change name
            },
            {
                extend: 'selected', // Bind to Selected row
                text: 'Delete',
                name: 'delete'      // do not change name
            },
            {
                text: 'Refresh',
                name: 'refresh'      // do not change name
            }],
        onAddRow: function (datatable, rowdata, success, error) {
            $.ajax({
                url: "/contact/user",
                method: 'PUT',
                dataType: 'json',
                data: JSON.stringify(rowdata),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data, status, response) {
                    myTable.row.add( data.body).draw( false );
                    alertSuccess(data, status, response);
                },
                error: function (data) {
                    alertError(data);
                }
            });
        },
        onDeleteRow: function (datatable, rowdata, success, error) {
            $.ajax({
                url: "/contact/user",
                method: 'DELETE',
                dataType: 'json',
                data: JSON.stringify(rowdata),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data, status, response) {
                    myTable.row('.selected').remove().draw(false);
                    alertSuccess(data, status, response);
                },
                error: function (data) {
                    alertError(data);
                }
            });
        },
        onEditRow: function (datatable, rowdata, success, error) {
            $.ajax({
                url: "/contact/user",
                method: 'POST',
                dataType: 'json',
                data: JSON.stringify(rowdata),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data, status, response) {
                    myTable
                        .row('.selected')
                        .data(data.body)
                        .draw();
                    alertSuccess(data, status, response);
                },
                error: function (data, status, er) {
                    alertError(data);
                }
            });
        }
    });

    function alertError(data) {
        let message = "<div align=\"center\"><b>details: </b></div>" + JSON.parse(data.responseText).details;
        let title = "<b>status: </b>" + data.status + "</br> <b>message: </b>" +JSON.parse(data.responseText).message;
        errorAlert(title, message);
    }

    function alertSuccess(data, status, response) {
        let message = "<div align=\"center\"><b>details: </b></div>" + data.message;
        let title = "<b>status: </b>" + response.status + "</br> <b>message: </b>" +status;
        successAlert(title, message);
    }

});

