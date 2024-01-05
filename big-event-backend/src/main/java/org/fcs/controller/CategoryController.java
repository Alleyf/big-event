package org.fcs.controller;

import jakarta.annotation.Resource;
import org.fcs.model.entity.Category;
import org.fcs.model.pojo.Result;
import org.fcs.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author alleyf
 * @Date 2023/12/29 9:52
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 添加分类
     *
     * @param category 分类实体
     * @return 成功
     */
    @PostMapping("/add")
    public Result<?> add(@Validated(Category.Add.class) @RequestBody Category category) {
        categoryService.add(category);
        return Result.success();
    }

    /**
     * 查询当前登录用户的分类列表
     *
     * @return 分类列表
     */

    @GetMapping("/list")
    public Result<List<Category>> list() {
        return Result.success(categoryService.list());
    }

    /**
     * 查询分类详情
     *
     * @param cid 分类ID
     * @return 分类详情
     */
    @GetMapping("/list/{id}")
    public Result<Category> detail(@PathVariable("id") Integer cid) {
        return Result.success(categoryService.detail(cid));
    }

    /**
     * 修改分类
     *
     * @param category 分类编辑实体
     * @return 成功
     */
    @PutMapping("/update")
    public Result<?> edit(@Validated(Category.Edit.class) @RequestBody Category category) {
        categoryService.edit(category);
        return Result.success();
    }

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return 删除结果
     */

    @DeleteMapping("/delete")
    public Result<?> delete(@RequestParam("id") Integer id) {
        categoryService.delete(id);
        return Result.success();
    }
}
