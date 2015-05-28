function showGrowlMessage(message) {
    showGrow(message, 'success');
}

function showGrowErrorMessage(message) {
    showGrow(message, 'danger');
}

function showGrow(message, type) {
    var growlTemplate = '<div data-growl="container" class="alert" role="alert">';

    growlTemplate += '<button type="button" class="close" data-growl="dismiss">';
    growlTemplate += '<span class="sr-only">Close</span></button>';
    growlTemplate += '<span data-growl="icon"></span>';
    growlTemplate += '<span data-growl="message"></span>';
    growlTemplate += '<a href="#" data-growl="url"></a></div>';

    var icon = (type === 'success')? 'fa fa-check': 'fa fa-ban'

    $.growl(
        {
            icon: icon,
            title: '알림',
            message: message
        }, {
            element: 'body',
            type: type,
            allow_dismiss: true,
            placement: {
                from: "top",
                align: "right"
            },
            offset: 20,
            spacing: 0,
            z_index: 9999,
            delay: 500,
            timer: 2000,
            mouse_over: false,
            animate: {
                enter: 'animated fadeInDown',
                exit: 'animated fadeOutUp'
            },
            icon_type: 'class',
            template: growlTemplate
        }
    );
}