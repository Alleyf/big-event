package org.fcs.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.fcs.core.utils.ThreadLocalUtil;
import org.fcs.mapper.CategoryMapper;
import org.fcs.model.entity.Category;
import org.fcs.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author alleyf
 * @Date 2023/12/29 10:05
 * @Version 1.0
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    private static String getLoginUserName() {
        // 获取用户信息
        JSONObject userJsonObj = ThreadLocalUtil.get();
        // 获取用户名
        return userJsonObj.getStr("username");
    }

    @Override
    public void add(Category category) {
        category.setCreateBy(getLoginUserName());
        log.warn("创建人：{}", category.getCreateBy());
        // 校验分类是否存在
        Category existCategory = categoryMapper.getCategoryByName(category.getCategoryName());
        if (existCategory != null) {
//            判断是否被逻辑删除
            Assert.equals(true, existCategory.getIsDel(), "分类已存在");
            Integer recover = categoryMapper.recover(category.getCategoryName(), getLoginUserName());
            Assert.equals(1, recover, "恢复分类失败");
        } else {
            Integer code = categoryMapper.add(category);
            Assert.equals(1, code, "添加分类失败");
        }
    }

    @Override
    public List<Category> list() {
        return categoryMapper.list(getLoginUserName());
    }

    @Override
    public Category detail(Integer cid) {
        Category category = categoryMapper.getById(cid);
        Assert.notNull(category, "分类不存在");
        return category;
    }

    @Override
    public void edit(Category category) {
        // 校验分类是否存在
        detail(category.getId());
        category.setUpdateBy(getLoginUserName());
        Integer code = categoryMapper.update(category);
        Assert.equals(1, code, "修改分类失败");
    }

    @Override
    public void delete(Integer id) {
        // 校验分类是否存在
        detail(id);
        Integer code = categoryMapper.delete(id, getLoginUserName());
        Assert.equals(1, code, "删除分类失败");
    }
}
