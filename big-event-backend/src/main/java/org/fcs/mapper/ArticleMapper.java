package org.fcs.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.fcs.model.entity.Article;

import java.util.List;

/**
 * @Author alleyf
 * @Date 2023/12/29 19:34
 * @Version 1.0
 */
@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title,category_id,create_by,cover_img,state,content) values(#{title},#{categoryId},#{createBy},#{coverImg},#{state},#{content})")
    Integer add(Article article);

    @Select("select * from article where create_by=#{createBy}")
    List<Article> getAll(String createBy);

    List<Article> page(String createBy, Integer categoryId, String state);
}
