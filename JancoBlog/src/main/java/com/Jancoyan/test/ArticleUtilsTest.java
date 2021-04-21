package com.Jancoyan.test;

import com.Jancoyan.utils.ArticleUtils;
import org.junit.Test;

public class ArticleUtilsTest {


    @Test
    public void testArticleAbstract(){
        String testStr = "<div class=\"markdown-body editormd-preview-container\" previewcontainer=\"true\" style=\"padding: 20px;\"><h1 id=\"h1-ajax\"><a name=\"Ajax\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Ajax</h1><blockquote>\n" +
                "\t\t\t<p>实际开发中使用JQuery + Ajax较多，这部分内容作为了解即可。  </p>\n" +
                "\t\t\t<p>重点内容是 JQuery的 【## JQuery + Ajax 中的普通查询和级联查询】</p>\n" +
                "\t\t\t</blockquote>\n" +
                "\t\t\t<p>ajax:Asynchronous JavaScript and XML（异步的 JavaScript 和 XML）。</p>\n" +
                "\t\t\t<ul>\n";

        String rst = ArticleUtils.getArticleAbstract(testStr);
        System.out.println(rst);
    }
}
