package com.example.ptms.mapper;


import com.example.ptms.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {

    @Select("select * from t_user where id = #{id}")
    User findById(@Param("id") Long id);

    @Select("select * from t_user where username = #{username} ")
    User findByUsername(@Param("username") String username);

    @Insert("insert into t_user(username,password,nickname) values(#{username},#{password},#{nickname})")
    int insert(User user);
}
