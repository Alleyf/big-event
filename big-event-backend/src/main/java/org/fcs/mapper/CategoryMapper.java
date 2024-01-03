package org.fcs.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.fcs.model.entity.Category;

import java.util.List;

/**
 * 分类数据映射器
 *
 * @Author alleyf
 * @Date 2023/12/29 9:53
 * @Version 1.0
 */
@Mapper
public interface CategoryMapper {
    /**
     * 添加分类
     *
     * @param category 要添加的分类对象
     * @return 添加成功时返回1，否则返回0
     */
    @Insert("insert into category(category_name,category_alias,create_by) values(#{categoryName},#{categoryAlias},#{createBy})")
    Integer add(Category category);

    /**
     * 根据用户名获取其拥有的所有分类
     *
     * @param username 用户名
     * @return 分类列表
     */
    @Select("select * from category where create_by = #{username}")
    List<Category> list(String username);

    /**
     * 根据分类ID获取分类信息
     *
     * @param cid 分类ID
     * @return 分类对象
     */
    @Select("select * from category where id = #{cid}")
    Category getById(Integer cid);

    /**
     * 更新分类信息
     *
     * @param category 分类编辑对象
     * @return 更新成功时返回1，否则返回0
     */
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_by=#{updateBy},update_time=now() where id=#{id}")
    Integer update(Category category);
}
