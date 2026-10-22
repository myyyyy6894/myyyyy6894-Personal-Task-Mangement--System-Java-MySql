package com.example.ptms.mapper;

import com.example.ptms.entity.Task;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Select("select * from t_task where user_id = #{userId} order by id desc")
    List<Task> findAllByUserId(@Param("userId") Long userId);

    @Select("select * from t_task where id = #{id} and user_id = #{userId}")
    Task findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Insert("insert into t_task(user_id, title, content) values(#{userId}, #{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Task task);

    @Update("update t_task set title = #{title}, content = #{content} where id = #{id} and user_id = #{userId}")
    int update(Task task);

    @Update("update t_task set status = #{status} where id = #{id} and user_id = #{userId}")
    int updateStatus(@Param("id") Long id, @Param("userId") Long userId, @Param("status") Integer status);

    @Delete("delete from t_task where id = #{id} and user_id = #{userId}")
    int delete(@Param("id") Long id, @Param("userId") Long userId);
}
