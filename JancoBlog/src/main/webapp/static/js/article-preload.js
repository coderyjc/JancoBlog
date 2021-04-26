var article;
var subType;
var superType;


$(function(){

    get_article();
    get_sub_types();
    get_super_types()
    build_page();

});


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

function build_page(){
    $(".article-view-time").append("浏览量："+ article.articleViewTime);

    var superPath = "../../sortlist.jsp?type=" + superType.typeId;
    var superTypeA = $("<a></a>").attr("href", superPath).append(superType.typeName);
    var subPath = "../../sortlist.jsp?type=" + subType.typeId;
    var subTypeA = $("<a></a>").attr("href", subPath).append(subType.typeName);
    $(".article-sort")
        .append(superTypeA)
        .append(" > ")
        .append(subTypeA);
}