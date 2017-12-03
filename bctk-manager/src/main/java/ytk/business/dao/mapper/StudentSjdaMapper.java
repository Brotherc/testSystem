package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.StudentSjda;
import ytk.business.pojo.po.StudentSjdaExample;

public interface StudentSjdaMapper {
    int countByExample(StudentSjdaExample example);

    int deleteByExample(StudentSjdaExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(StudentSjda record);

    int insertSelective(StudentSjda record);

    List<StudentSjda> selectByExample(StudentSjdaExample example);

    StudentSjda selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") StudentSjda record, @Param("example") StudentSjdaExample example);

    int updateByExample(@Param("record") StudentSjda record, @Param("example") StudentSjdaExample example);

    int updateByPrimaryKeySelective(StudentSjda record);

    int updateByPrimaryKey(StudentSjda record);
}