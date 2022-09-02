package com.jancoyan.jancoblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.jancoblog.model.domain.Article;
import com.jancoyan.jancoblog.mapper.ArticleMapper;
import com.jancoyan.jancoblog.model.domain.ArticleImage;
import com.jancoyan.jancoblog.model.domain.DeletedComment;
import com.jancoyan.jancoblog.model.domain.PageArticle;
import com.jancoyan.jancoblog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jancoyan.jancoblog.utils.ArticleUtils;
import com.jancoyan.jancoblog.utils.FileUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public IPage<Article> listArticleIndex(Integer pn, Integer limit, String condition) {

        //        分页查询
        IPage<Article> iPage = new Page<>(pn, limit);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("article_rank");
        wrapper = ArticleUtils.generateManageArticleWrapperByCondition(wrapper, condition);
        wrapper.orderByDesc("article_post_time");

        return baseMapper.getIndexList(iPage, wrapper);
    }

    @Override
    public IPage<Article> listArticleManage(Integer userId,
                                        Integer pn,
                                        Integer limit,
                                        String condition) {
        //        分页查询
        IPage<Article> iPage = new Page<>(pn, limit);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        // 单一用户的文章获取
        if(null != userId) {
            wrapper.eq("user_id", userId);
        }

        wrapper = ArticleUtils.generateManageArticleWrapperByCondition(wrapper, condition);

        wrapper.orderByDesc("article_post_time");

        return baseMapper.getManageList(iPage, wrapper);
    }

    @Override
    public Article getArticleSingle(String articleId) {
        return baseMapper.getSingleArticle(articleId);
    }

    @Override
    public Article getArticleSingleDeleted(String articleId) {
        return baseMapper.getSingleArticleDeleted(articleId);
    }

    @Override
    public IPage<Article> listDeleted(Integer userId,
                                      Integer pn,
                                      Integer limit,
                                      String condition) {
        //        分页查询
        IPage<Article> iPage = new Page<>(pn, limit);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        // 单一用户的文章获取
        if(null != userId) {
            wrapper.eq("user_id", userId);
        }

        wrapper = ArticleUtils.generateManageArticleWrapperByCondition(wrapper, condition);
        wrapper.orderByDesc("article_post_time");
        return baseMapper.getDeletedList(iPage, wrapper);
    }

    @Override
    public boolean deleteCompletely(String ids) {
        QueryWrapper<ArticleImage> wrapper = new QueryWrapper<>();
        ArticleImage articleImage = new ArticleImage();
        QueryWrapper<DeletedComment> deletedCommentQueryWrapper = new QueryWrapper<>();
        DeletedComment deletedComment = new DeletedComment();

        if(!ids.contains("&")){
            wrapper.eq("article_id", ids);
            deletedCommentQueryWrapper.eq("comment_article_id", ids);
            List<ArticleImage> articleImages = articleImage.selectList(wrapper);
            for (ArticleImage image : articleImages) {
                // 依次删除图片文件
                FileUtils.deleteImageIfExists(image.getInsertDate(), image.getFilename());
            }
            // 从表中删除
            articleImage.delete(wrapper);
            deletedComment.delete(deletedCommentQueryWrapper);
            // 删除所有已删除的评论
            baseMapper.deleteCompletely(ids);
        } else {
            String[] id = ids.split("&");
            for (String item : id) {
                // 删除图片文件
                wrapper.eq("article_id", item);
                deletedCommentQueryWrapper.eq("comment_article_id", item);
                List<ArticleImage> articleImages = articleImage.selectList(wrapper);
                for (ArticleImage image : articleImages) {
                    // 依次删除图片文件
                    FileUtils.deleteImageIfExists(image.getInsertDate(), image.getFilename());
                }
                // 从表中删除
                articleImage.delete(wrapper);
                deletedComment.delete(deletedCommentQueryWrapper);
                // 从文章表中删除文章
                baseMapper.deleteCompletely(item);
            }
        }
        return true;
    }

    @Override
    public boolean batchRecoverDeletedArticle(String ids) {
        if(!ids.contains("&")){
            baseMapper.batchRecover(ids);
        } else {
            String[] id = ids.split("&");
            for (String item : id) {
                baseMapper.batchRecover(item);
            }
        }
        return true;
    }

    @Override
    public boolean batchDeleteArticle(String ids) {
        Article article = new Article();
        if(!ids.contains("&")){
            article.setArticleId(ids);
            article.deleteById();
        } else {
            String[] id = ids.split("&");
            for (String item : id) {
                article.setArticleId(item);
                article.deleteById();
            }
        }
        return true;
    }

    @Override
    public IPage<PageArticle> listArticleUserRecently(String id, Integer pn, Integer limit) {
        IPage<PageArticle> iPage = new Page<>(pn, limit);
        QueryWrapper<PageArticle> wrapper = new QueryWrapper<>();
        if(!"-1".equals(id)){
            wrapper.eq("article_author", id);
        }
        wrapper.orderByDesc("article_post_time");
        return baseMapper.getArticleByUserRecently(iPage, wrapper);
    }

    @Override
    public void addLikeCount(String id) {
        // 增加点赞量
        Article article = new Article();
        article.setArticleId(id);
        article = article.selectById();
        article.setArticleLikeCount(article.getArticleLikeCount() + 1);
        article.updateById();
    }

    @Override
    public void subLikeCount(String id) {
        Article article = new Article();
        article.setArticleId(id);
        article = article.selectById();
        article.setArticleLikeCount(article.getArticleLikeCount() - 1);
        article.updateById();
    }

    @Override
    public void addViewCount(String id) {
        Article article = new Article();
        article.setArticleId(id);
        article = article.selectById();
        article.setArticleViewCount(article.getArticleViewCount() + 1);
        article.updateById();
    }

    @Override
    public boolean updateIsComment(String id) {
        // 改变评论的状态
        Article article = new Article();
        article.setArticleId(id);
        article = article.selectById();
        article.setArticleIsComment(1 - article.getArticleIsComment());
        return article.updateById();
    }

    @Override
    public boolean updateIsTop(String id) {
        // 改变置顶的状态
        Article article = new Article();
        article.setArticleId(id);
        article = article.selectById();
        article.setArticleRank(1 - article.getArticleRank());
        return article.updateById();
    }

    @Override
    public Article getArticleEdit(String id) {
        return baseMapper.getArticleEdit(id);
    }


}
