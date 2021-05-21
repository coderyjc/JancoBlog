var article;
var subType = null;
var superType = null;

$(function(){

    get_article();
    get_sub_types();
    get_super_types()
    build_page();
    // 获取文章评论
    get_comments();

    // 添加笔记的按钮事件
    $(".add-comment-btn").click(function (){
        // 获取相关信息
        let nickName = $("#edit-comment-author").val();
        let email = $("#edit-comment-email").val();
        let content = $("#edit-comment-content").val();
        // 进行合法校验
        var nameReg = /^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$/;
        if(!nameReg.test(nickName)){
            alert("用户名格式不对");
            return;
        }
        var emailReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if(!emailReg.test(email)){
            alert("用户邮箱格式不对");
            return;
        }
        if("" === content){
            alert("内容不能为空");
            return;
        }

        // 发送ajax请求进行添加
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
                // 在评论区添加这一条评论
                $(".comment-list").append(create_comment_box(comment));
                add_article_comment_count();
                // 清空编辑区
                $("#edit-comment-author").val("");
                $("#edit-comment-email").val("");
                $("#edit-comment-content").val("");
            }
        });
    });
});


/**
 * 页面加载完毕之后加载评论
 */
function get_comments(){
    $.ajax({
        url: "../../comments",
        data: "id=" + article.articleId,
        type : "GET",
        success: function (result) {
            var comments = result.extend.comments;
            var list = $(".comment-list");
            // 遍历添加
            $.each(comments, function (index, item) {
                list.append(create_comment_box(item));
            });
        }
    })
}

/**
 * 创造评论box
 * @param comment 评论对象
 * @returns {*|jQuery} 返回评论box
 */
function create_comment_box(comment) {
    var commentBox = $("<div></div>").addClass("comment-box clearfix");

    var commentAuthorInfo = $("<div></div>").addClass("comment-author-info")
        .append($("<div class='comment-author'></div>").append("昵称：" + comment.authorNickname))
        .append($("<div class='comment-ip'></div>").append("地址：" + comment.authorIp));
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
 * ajax获取文章
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
 * ajax获取二级类型
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
 * ajax获取父级类型
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
 * 根据获取到的文章和类型进行页面的建立
 */
function build_page(){
    $(".article-view-time").append("浏览量："+ article.articleViewTime);
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

//评论之后增加数据库中文章的评论数量
function add_article_comment_count() {
    $.ajax({
        url:"../../article/comment",
        type:"POST",
        data: "id=" + article.articleId
    });
}

/**
 * 将时间戳转化为标准时间
 * @param date 时间戳
 * @returns {string} 标准时间字符串
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