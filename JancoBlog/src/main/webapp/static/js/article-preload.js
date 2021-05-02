var article;
var subType;
var superType;


$(function(){

    get_article();
    get_sub_types();
    get_super_types()
    build_page();
    // ��ȡ��������
    get_comments();

    // ��ӱʼǵİ�ť�¼�
    $(".add-comment-btn").click(function (){
        // ��ȡ�����Ϣ
        let nickName = $("#edit-comment-author").val();
        let email = $("#edit-comment-email").val();
        let content = $("#edit-comment-content").val();
        // ���кϷ�У��
        var nameReg = /^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$/;
        if(!nameReg.test(nickName)){
            alert("�û�����ʽ����");
            return;
        }
        var emailReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if(!emailReg.test(email)){
            alert("�û������ʽ����");
            return;
        }
        if("" === content){
            alert("���ݲ���Ϊ��");
            return;
        }

        // ����ajax����������
        $.ajax({
            url: "../../comment",
            type: "POST",
            data: {
                "id" : article.articleId,
                "nickName": nickName,
                "email": email,
                "content" : content
            },
            success: function (result) {
                let comment = result.extend.comment;
                // �������������һ������
                $(".comment-list").append(create_comment_box(comment));
                add_article_comment_count();
                // ��ձ༭��
                $("#edit-comment-author").val("");
                $("#edit-comment-email").val("");
                $("#edit-comment-content").val("");
            }
        });
    });
});


/**
 * ҳ��������֮���������
 */
function get_comments(){
    $.ajax({
        url: "../../comments",
        data: "id=" + article.articleId,
        type : "GET",
        success: function (result) {
            var comments = result.extend.comments;
            var list = $(".comment-list");
            // �������
            $.each(comments, function (index, item) {
                list.append(create_comment_box(item));
            });
        }
    })
}

/**
 * ��������box
 * @param comment ���۶���
 * @returns {*|jQuery} ��������box
 */
function create_comment_box(comment) {
    var commentBox = $("<div></div>").addClass("comment-box clearfix");

    var commentAuthorInfo = $("<div></div>").addClass("comment-author-info")
        .append($("<div class='comment-author'></div>").append("�ǳƣ�" + comment.authorNickname))
        .append($("<div class='comment-ip'></div>").append("��ַ��" + comment.authorIp));
    var commentContent = $("<div></div>").addClass("comment-conetnt").append(comment.commentContent);
    var otherInfo = $("<div class='comment-other-info'></div>")
        .append($("<div class='comment-date'></div>").append(dateFormat(comment.commentDate)));

    commentBox.append(commentAuthorInfo)
        .append("<br>")
        .append(commentContent)
        .append(otherInfo);
    return commentBox;
}

/**
 * ajax��ȡ����
 */
function get_article(){
    var currentPath = window.location.href;
    var prop = currentPath.split("/");
    var articleId = prop[prop.length - 1].split(".")[0];
    $.ajax({
        url:"../../articles",
        type: "PUT",
        async: false,
        data: "id=" + articleId,
        success: function(result){
            article = result.extend.article;
        }
    });
}

/**
 * ajax��ȡ��������
 */
function get_sub_types() {
    $.ajax({
        url: "../../types",
        type: "POST",
        async: false,
        data: "id=" + article.articleType,
        success: function(result){
            subType = result.extend.type;
        }
    })
}

/**
 * ajax��ȡ��������
 */
function get_super_types() {
    $.ajax({
        url: "../../types",
        type: "POST",
        async: false,
        data: "id=" + subType.preTypeId,
        success: function(result){
            superType = result.extend.type;
        }
    })
}

/**
 * ���ݻ�ȡ�������º����ͽ���ҳ��Ľ���
 */
function build_page(){
    $(".article-view-time").append("�������"+ article.articleViewTime);
    if(superType != null){
        var superPath = "../../sortlist.jsp?type=" + superType.typeId;
        var superTypeA = $("<a></a>").attr("href", superPath).append(superType.typeName);
        $(".article-sort").append(superTypeA);
    }
    if(subType != null){
        var subPath = "../../sortlist.jsp?type=" + subType.typeId;
        var subTypeA = $("<a></a>").attr("href", subPath).append(subType.typeName);
        $(".article-sort").append(" > ")
        $(".article-sort").append(subTypeA);
    }
}

function add_article_comment_count() {
    $.ajax({
        url:"../../article/comment",
        type:"POST",
        data: "id=" + article.articleId
    });
}

/**
 * ��ʱ���ת��Ϊ��׼ʱ��
 * @param date ʱ���
 * @returns {string} ��׼ʱ���ַ���
 */
function dateFormat(date) {
    var s = new Date(date)
    var y = s.getFullYear()
    var m = (s.getMonth() + 1) < 10 ? '0' + (s.getMonth() + 1) : (s.getMonth() + 1)
    var dd = s.getDate() < 10 ? '0' + s.getDate() : s.getDate()
    var hh = s.getHours() < 10 ? '0' + s.getHours() : s.getHours()
    var mm = s.getMinutes() < 10 ? '0' + s.getMinutes() : s.getMinutes()
    var ss = s.getSeconds() < 10 ? '0' + s.getSeconds() : s.getSeconds()
    var enddate = y + '-' + m + '-' + dd + ' ' + hh + ':' + mm + ":" + ss
    return enddate
}