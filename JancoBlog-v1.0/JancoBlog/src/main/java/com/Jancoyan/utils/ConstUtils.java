package com.Jancoyan.utils;

import java.io.File;

/**
 * 常量工具集
 */
public class ConstUtils {

    // 当前服务器根目录
    public static final String CURRENTPATH = System.getProperty("user.dir").replace("bin",
            "webapps") + File.separator + "JancoBlog";

    // html文件目录
    public static final String HTMLPATH = System.getProperty("user.dir").replace("bin",
            "webapps") + File.separator + "JancoBlog" + File.separator + "static" +
            File.separator + "p" + File.separator;

    // md文件目录
    public static final String MDPATH = System.getProperty("user.dir").replace("bin",
            "webapps") + File.separator + "JancoBlog" + File.separator + "static" +
            File.separator + "md" + File.separator;

    // editormd 的css文件
    public static final String LINK_EDITOR_PATH = ".." + File.separator + "js" + File.separator +
            "editor.md" + File.separator + "css" + File.separator + "editormd.min.css";

    // jq 的js文件
    public static final String SCRIPT_JQUERY_PATH = ".." + File.separator + "js" + File.separator +
            "jquery-1.12.js";

    // article preload 的 js文件
    public static final String SCRIPT_PRELOAD_PATH = ".." + File.separator + "js" + File.separator +
            "article-preload.js";

    // article 的css文件
    public static final String LINK_ARTICLE_PATH = ".." + File.separator + "css" + File.separator +
            "article.css";

}
