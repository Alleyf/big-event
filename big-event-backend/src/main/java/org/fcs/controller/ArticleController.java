package org.fcs.controller;

import jakarta.annotation.Resource;
import org.fcs.model.entity.Article;
import org.fcs.model.pojo.PageBean;
import org.fcs.model.pojo.Result;
import org.fcs.service.impl.ArticleServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author alleyf
 * @Date 2023/12/26 0:17
 * @Version 1.0
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Resource
    private ArticleServiceImpl articleService;

    /**
     * 文章列表
     *
     * @return 文章列表
     */
    @GetMapping("/list")
    public Result<List<Article>> list() {
        return Result.success(articleService.list());
    }

    /**
     * 新增文章
     *
     * @param article 文章
     * @return 成功
     */

    @PostMapping("/add")
    public Result<?> add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }

    /**
     * 分页查询
     *
     * @param pageNum    页码
     * @param pageSize   每页数量
     * @param categoryId 分类id
     * @param state      发布状态
     * @return 分页数据
     */

    @GetMapping("/page")
    public Result<PageBean<Article>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam(required = false) Integer categoryId,
                                          @RequestParam(required = false) String state) {
        return Result.success(articleService.page(pageNum, pageSize, categoryId, state));
    }
}
