/**
 * 초기화
 */
function resetForm(isUnSelected) {
    var form = $('#addCodeForm');

    $(form).find('#code').remove();

    $('#codeName').val('').focus();
    $('#codeAlias').val('');
    $("#description").val('');
    $('#useYNN').prop('checked',true);
    $('#parentCode').val('');
    $('#sortOrder').val('');
    $('#parentCodeName').val('');
    $('#addNewSubCode').addClass('disabled');
    $('#saveCodeButton').text('등록');
    $("input[name='_method']").remove();

    if (isUnSelected) {
        $('#codeTree').find('li').removeAttr('style').removeClass('node-selected');
    }
}

/**
 * 코드 상세 데이터 출력
 * @param node 선택된 노드
 */
function getCodeData(node) {
    var form = $('#addCodeForm');

    $(form).find('#code').remove();

    $('#parentCodeName').prop('disabled', true).val('');
    $('#codeName').val(node.text);
    $("#description").val(node.description);
    $("#codeAlias").val(node.alias);
    $("#sortOrder").val(node.sortOrder);
    $(form).append("<input type='hidden' name='code' id='code' value='" + node.code + "' />");

    if (node.useYN === 'Y') {
        $('#useYNY').prop('checked',true);
    } else {
        $('#useYNN').prop('checked',true);
    }

    if (node.parentCode !== '') {
        $('#parentCode').val(node.parentCode);
        $('#parentCodeName').val(node.parentCodeName);
        $('#sortOrder').prop('disabled', false);
    }

    // 하위등록 활성화
    $('#addNewSubCode').removeClass('disabled');

    // 등록버튼 명칭 변경
    $('#saveCodeButton').text('수정');

    $(form).append('<input type="hidden" name="_method" value="put" />');
}

/**
 * 하위 코드 검사 및 처리
 *
 * @param parentData 가공된 부모 데이터
 * @param data 원본 데이터
 */
function makeSubNodeData(parentData, data) {
    if (data !== undefined && data.childList !== undefined && data.childList !== null && data.childList.length > 0) {
        parentData.icon = '-';

        if (parentData.nodes === null || parentData.nodes === undefined) {
            parentData.nodes = [];
        }

        var childList = data.childList;
        var length = childList.length;

        for (var i = 0; i < length; i++) {
            var childData = childList[i];

            var nodeData = {
                text: childData.name,
                code: childData.code,
                alias: childData.alias,
                sortOrder: childData.sortOrder,
                parentCode: childData.parentCode,
                parentCodeName: childData.parentCodeName,
                useYN: childData.useYN,
                description: childData.description
            };

            makeSubNodeData(nodeData, childData);

            parentData.nodes.push(nodeData);
        }
    } else {
        parentData.icon = 'glyphicon glyphicon-ok';
    }
}

/**
 * 코드 트리 데이터 생성
 *
 * @param treeData 트리 데이터가 저장될 배열
 * @param data 원본 데이터
 */
function makeNodeData(treeData, data) {
    var nodeData = {
        text: data.name,
        code: data.code,
        alias: data.alias,
        sortOrder: data.sortOrder,
        parentCode: (data.parentCode !== null && data.parentCode !== undefined)? data.parentCode: '',
        parentCodeName: (data.parentCodeName !== null && data.parentCodeName !== undefined)? data.parentCodeName: '',
        useYN: data.useYN,
        description: data.description
    };

    makeSubNodeData(nodeData, data);

    treeData.push(nodeData);
}

/**
 * 코드 목록 조회
 */
function getCodeList() {
    $.ajax(
        {
            type: 'get',
            url: ctx + '/code/list?ajax=true',
            dataType: 'json',
            success: function (d) {
                if (d.result === 'y') {
                    var list = d.list;
                    var length = list.length;
                    var treeData = [];

                    for (var i = 0; i < length; i++) {
                        var data = list[i];

                        makeNodeData(treeData, data);
                    }

                    var treeOption = {
                        data: treeData,
                        expandIcon: 'glyphicon glyphicon-folder-close',
                        collapseIcon: 'glyphicon glyphicon-folder-open',
                        levels: 1
                    };

                    $('#codeTree').treeview(treeOption).on('nodeSelected', function (event, node) {
                        getCodeData(node);
                    });

                    $('#codeTree').find('li').each(function () {
                        $(this).html("<div>" + $(this).html() + "</div>");
                    });
                } else {
                    if (d.message !== undefined && d.message !== null) {
                        $('#ajaxModal').modal();
                    }
                }
            }
        }
    );
}

/**
 * 저장
 */
function saveCode() {
    var data = $('#addCodeForm').serialize();
    var url = ctx + '/code/form';

    if ($("input[name='_method']").length > 0) {
        url = ctx + '/code/' + $('#code').val() + '/form';
    }

    $.ajax(
        {
            type: 'post',
            url: url,
            data: data,
            dataType: 'json',
            success: function (d) {
                if (d.result === "y") {
                    $('#codeTree').empty();

                    resetForm(true);
                    getCodeList();

                    alert('처리되었습니다.');
                } else {
                    var message = (d.message !== null && d.message !== undefined)? d.message: "서버에서 오류가 발생하였습니다.";

                    if (Object.prototype.toString.call(message) === '[object Array]') {
                        displayErrorMessage(message, '#addCodeForm');
                    } else {
                        console.log(message);

                        $('.modal-body').html('<p>' + message + '</p>');
                        $('#ajaxModal').modal();
                    }
                }
            }
        }
    );
}

function setAddSubCode() {
    var code = $('#code').val();
    var name = $('.node-selected').text();

    resetForm(false);

    $('#parentCode').val(code);
    $('#parentCodeName').val(name);
    $('#codeName').focus();
}

$(document).ready(function () {
    $('.modal-footer').find('button').on('click', function () {
        $('#ajaxModal').modal('hide');
    });

    $('#codeName').focus();

    $('#saveCodeButton').on('click', function (e) {
        saveCode();

        e.preventDefault();
    });

    $('#addNewCode').on('click', function (e) {
        resetForm(true);

        $('#sortOrder').prop('disabled', true);

        e.preventDefault();
    });

    $('#addNewSubCode').on('click', function (e) {
        setAddSubCode();

        $('#sortOrder').prop('disabled', false);

        e.preventDefault();
    });

    $('#cancelCodeButton').on('click', function (e) {
        resetForm(true);

        e.preventDefault();
    });

    getCodeList();
});