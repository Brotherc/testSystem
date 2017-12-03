package ytk.base.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.base.pojo.po.TeacherKc;
import ytk.base.pojo.po.TeacherKcExample;

public interface TeacherKcMapper {
    int countByExample(TeacherKcExample example);

    int deleteByExample(TeacherKcExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(TeacherKc record);

    int insertSelective(TeacherKc record);

    List<TeacherKc> selectByExample(TeacherKcExample example);

    TeacherKc selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") TeacherKc record, @Param("example") TeacherKcExample example);

    int updateByExample(@Param("record") TeacherKc record, @Param("example") TeacherKcExample example);

    int updateByPrimaryKeySelective(TeacherKc record);

    int updateByPrimaryKey(TeacherKc record);
}