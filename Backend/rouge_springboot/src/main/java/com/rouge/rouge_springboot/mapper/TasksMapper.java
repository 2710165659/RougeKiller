package com.rouge.rouge_springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rouge.rouge_springboot.model.dto.tasks.TasksDTO;
import com.rouge.rouge_springboot.model.entity.Tasks;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TasksMapper extends BaseMapper<Tasks> {

    @Select("SELECT tasks.* , users.name \n" +
            "FROM tasks\n" +
            "JOIN users on tasks.created_by = users.id \n" +
            "WHERE tasks.full_url LIKE CONCAT('%', #{fullUrl}, '%')")
    List<TasksDTO> getTasksByUrl(@Param("fullUrl") String fullUrl);
}
