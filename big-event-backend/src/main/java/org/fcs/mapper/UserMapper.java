package org.fcs.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.fcs.domain.vo.req.UserPwd;
import org.fcs.model.entity.User;

/**
 * @Author alleyf
 * @Date 2023/12/24 23:09
 * @Version 1.0
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return User
     */
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return User
     */
    @Select("select * from user where id = #{userId}")
    User findByUserId(Integer userId);

    /**
     * 添加新用户
     *
     * @param username 用户名
     * @param password 密码
     * @return Integer
     */
    @Insert("insert into user(username,password,create_time,update_time) values(#{username},#{password},now(),now())")
    Integer addUser(String username, String password);

    /**
     * 更新用户信息
     *
     * @param user 用户对象
     * @return User
     */
    @Update("update user set username=#{username}, nickname=#{nickname}, password=#{password}, email=#{email}, update_time=#{updateTime}, update_by=#{updateBy} where id=#{id}")
    Integer update(User user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return Integer
     */
    @Update("update user set is_del=1 where id=#{id}")
    Integer delete(Integer id);

    /**
     * 更新用户头像
     *
     * @param uid      用户ID
     * @param avatar   头像地址
     * @param updateBy 操作人
     * @return Integer
     */
    @Update("update user set user_pic=#{avatar}, update_by=#{updateBy}, update_time=now() where id=#{uid}")
    Integer updateAvatar(Integer uid, String avatar, String updateBy);

    /**
     * 更新用户密码
     *
     * @param userPwd 用户密码对象
     * @return Integer
     */
    @Update("update user set password=#{userPwd.newPwd},update_time=now(),update_by=#{updateBy} where id=#{userPwd.userId}")
    Integer updatePassword(UserPwd userPwd, String updateBy);


}
