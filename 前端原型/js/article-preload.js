$(function(){
	
	get_article();
	
	build_page(article);
	
});

var article;
var superType;
var subType;

function get_article(){
	var currentPath = window.location.href;
	var prop = currentPath.split("/");
	var articleId = prop[prop.length - 1].split(".")[0];
	$.ajax({
		url:"articles",
		type: "put",
		data: "id=" + articleId,
		success: function(result){
			article = result.extend.article;
		}
	});
	
	$.ajax({
		url: "types",
		type: "POST",
		data: "id=" + article.articleType,
		success: function(result){
			subType= result.extend.type;
		}
	})
	
	$.ajax({
		url: "types",
		type: "POST",
		data: "id=" + subType.preTypeId,
		success: function(result){
			superType = result.extend.type;
		}
	})
}

function build_page(article){
	$(".article-view-time").append("浏览量："+ article.articleViewTime);
	
	var indexPage = $("<a href="../../index.jsp"></a>").append("首页");
	var superPath = "../../sortlist.jsp?type=" + article.preTypeId;
	var superType = $("<a></a>").attr("href", superPath).append(superType.typeName);
	var subPath = "../../sortlist.jsp?type=" + article.typeId;
	var subType = $("<a></a>").attr("href", subPath).append(subType.typeName);
	$(".article-sort")
	.append(indexPage)
	.append(" > ")
	.append(superType)
	.append(" > ")
	.append(subType);
}