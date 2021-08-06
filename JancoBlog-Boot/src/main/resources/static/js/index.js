var totalcount = 0

layui.use(['element', 'layer', 'laypage'], function(){

    var laypage = layui.laypage

    // 文章
    get_article(1)

    function get_article(pn) {
        $.ajax({
            url: "article/articles",
            data : "pn=" + pn,
            type : "GET",
            async: false,
            success: function (result) {
                // 调用建立文章的函数
                build_article_list(result);
                totalcount = result.extend.pageInfo.total
            }
        });
    }

    //    分页
    laypage.render({
        elem: 'pagination'
        ,count: totalcount
        ,limit: 10
        ,groups: 6
        ,jump: function(obj, first){
            if (!first) {
                get_article(obj.curr)
            }
        }
    })

    // 分类条
    build_sort_bar()
});

// 登录之后显示头像和下拉框
if('[[${session.user}]]' !== ''){
    $("nav img").attr("src", "../images/profile.png")
}

// 传入result，建立文章列表
function build_article_list(result) {
    layui.$("#article-list").empty()
    var articleList = result.extend.pageInfo.records;
    let html = ""
    $.each(articleList, function (index, item) {

        html +=
            "<div class=\"layui-col-md\">" +
            "<li class=\"layui-card\">" +
            "<div class=\"layui-card-header\">" +
            "<a target=\"_blank\" href=\"/article.html?p=1000001628251741521\">" + item.articleTitle + "</a></div>" +
            "<div class=\"layui-card-body\">" + item.articleSummary + " </div>" +
            "<div class=\"segment-line\"></div>" +
            "<div class=\"article-info\">" +
            "<div class=\"article-info-date\">作者： " + item.articleAuthorName + "</div>" +
            "<div class=\"article-info-date\">发布日期： " + dateFormat(item.articlePostTime) + "</div>" +
            "<div class=\"article-info-view\">浏览量：" + item.articleViewCount + "</div>" +
            "<div class=\"article-info-like\">" +
            "<i class=\"layui-icon layui-icon-praise\" style=\"color: red\"></i>" +
            "<span class=\"like-count\">" + item.articleLikeCount + "</span>" +
            "</div></div></li></div>"

    });

    // 向一列中中添加元素
    $("#article-list").html(html)
}

function build_sort_bar() {
    $.ajax({
        url: "type/all",
        type : "GET",
        success: function (result) {
            // 调用建立文章的函数
            var sort_list = result.extend.pageInfo.records
            let sort_ul = $("<ul class=\"layui-row layui-col-space5\"></ul>");
            let html = "";
            $.each(sort_list, function (index, item) {
                html += "<li class=\"layui-col-md12 layui-col-xs12\" href=\"/index.html?typeId="+ item.typeId + "\">" +
                    "<a href=/index.html?typeId=" + item.typeId + " style='display: block' > " + item.typeName +
                    "<i class=\"layui-icon layui-icon-right\" style='float: right'></i></a></li>"
            })
            sort_ul.html(html).appendTo(".column")
        }
    });
}

// 点击文章的时候增加文章阅读量
function add_article_view(articleId) {
    $.ajax({
        url: "/article/view",
        type: "POST",
        data: {
            "id": articleId,
        }
    });
}

// 监听搜索按钮，搜索字段为空的时候
$("#search-btn").click(function (){
    var keyWord = $("#search-text").val();
    if(keyWord === ""){
        layer.msg("搜索字段不能为空");
    } else {
        window.location.href = "/search.html?keyword=" + keyWord;
    }
});

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
