package org.fcs.service;

import org.fcs.model.entity.Category;

import java.util.List;

/**
 * @Author alleyf
 * @Date 2023/12/29 10:05
 * @Version 1.0
 */
public interface CategoryService {
    void add(Category category);

    List<Category> list();

    Category detail(Integer cid);

    void edit(Category category);
}
