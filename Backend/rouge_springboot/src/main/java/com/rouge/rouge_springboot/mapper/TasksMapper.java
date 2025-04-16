package com.rouge.rouge_springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rouge.rouge_springboot.model.dto.tasks.TasksDTO;
import com.rouge.rouge_springboot.model.entity.Tasks;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TasksMapper extends BaseMapper<Tasks> {

    @Select("<script>" +
            "SELECT " +
            "   tasks.* , users.name \n" +
            "FROM tasks \n" +
            "JOIN users on tasks.created_by = users.id \n" +
            "<where>" +
            "   <if test='query.url != null'> AND tasks.full_url LIKE CONCAT('%', #{query.url}, '%') </if>" +
            "   <if test='query.status != null'> AND tasks.status = #{query.status} </if>" +
            "</where>" + " ORDER BY tasks.created_at DESC " +
            "</script>")
    IPage<TasksDTO.TasksResponseDTO> getTasks(IPage<TasksDTO.TasksResponseDTO> page, @Param("query") TasksDTO.TasksQueryDTO query);
}
