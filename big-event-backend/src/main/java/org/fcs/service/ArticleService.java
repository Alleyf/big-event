package org.fcs.service;

import org.fcs.model.entity.Article;
import org.fcs.model.pojo.PageBean;

import java.util.List;

/**
 * @Author alleyf
 * @Date 2023/12/29 19:42
 * @Version 1.0
 */
public interface ArticleService {
    void add(Article article);

    List<Article> list();

    PageBean<Article> page(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
