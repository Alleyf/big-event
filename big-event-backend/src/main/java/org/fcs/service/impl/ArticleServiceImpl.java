package org.fcs.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.fcs.core.utils.ThreadLocalUtil;
import org.fcs.mapper.ArticleMapper;
import org.fcs.mapper.CategoryMapper;
import org.fcs.model.entity.Article;
import org.fcs.model.pojo.PageBean;
import org.fcs.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author alleyf
 * @Date 2023/12/29 19:35
 * @Version 1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CategoryMapper categoryMapper;

    private static String getLoginUserName() {
        // 获取用户信息
        JSONObject userJsonObj = ThreadLocalUtil.get();
        // 获取用户名
        return userJsonObj.getStr("username");
    }

    @Override
    public void add(Article article) {
        article.setCreateBy(getLoginUserName());
        Integer code = articleMapper.add(article);
        Assert.equals(code, 1, "添加失败");
    }

    @Override
    public List<Article> list() {
        return articleMapper.getAll(getLoginUserName());
    }

    @Override
    public PageBean<Article> page(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
//    1.创建pageBean对象
        PageBean<Article> pageBean = new PageBean<>();
//    2.开启分页查询 pageHelper
        try (Page<Article> pageArticle = PageHelper.startPage(pageNum, pageSize)) {
//    3.调用mapper
            List<Article> articleList = articleMapper.page(getLoginUserName(), categoryId, state);
            Page<Article> page = (Page<Article>) articleList;
//    4.设置pageBean,填充数据
            pageBean.setItems(page.getResult());
            pageBean.setTotal(page.getTotal());
        }
        return pageBean;
    }
}
