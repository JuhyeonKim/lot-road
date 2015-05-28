$(document).ready(function () {
	$("ul.nav").find('li').each(function () {
	});

	$("#btnLogout").click(function (event) {
		document.location.href = "${ctx}/logout";
		event.preventDefault();
	});

	$(document).ajaxSend(function (e, xhr, options) {
		xhr.setRequestHeader($("meta[name='_csrf_header']").prop('content'),
		                     $("meta[name='_csrf']").prop('content'));
	});
});

function addErrorContainer(jqForm) {
	var html = '<div id="errorContainer" class="alert alert-danger alert-dismissable fade in">';

	html += '<i class="fa fa-ban"></i>';
	html += '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">x</button>';
	html += '<span></span></div>';

	$(html).insertBefore(jqForm.children().eq(0));

	return $('#errorContainer');
}

/**
 * 에러 메세지 출력
 *
 * 인자 순서는 [0]에러 메세지 배열(필수), [1]form id(필수), [2]errorContainer id(옵션) 이다.
 * @returns {boolean}
 */
function displayErrorMessage() {
    var errorContainer = (arguments.length === 3)? $(arguments[2]): $('#errorContainer');

    if (errorContainer === undefined || errorContainer === null || errorContainer.length === 0) {
    	if (arguments.length < 2) {
            throw "form id가 필요합니다.";
    	}

        errorContainer = addErrorContainer($(arguments[1]));
    }

    if (arguments[0].length > 0) {
        var errors = arguments[0];

        errorContainer.removeClass('hide');
        errorContainer.find('span').empty();

        var html = '';
        var length = errors.length;

        for (var i = 0; i < length; i++) {
            html += errors[i] + '<br />';
        }

        errorContainer.find('span').append(html);

        return false;
    }

    return true;
}
