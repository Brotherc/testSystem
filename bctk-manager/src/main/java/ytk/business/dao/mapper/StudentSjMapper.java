package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.StudentSj;
import ytk.business.pojo.po.StudentSjExample;

public interface StudentSjMapper {
    int countByExample(StudentSjExample example);

    int deleteByExample(StudentSjExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(StudentSj record);

    int insertSelective(StudentSj record);

    List<StudentSj> selectByExample(StudentSjExample example);

    StudentSj selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") StudentSj record, @Param("example") StudentSjExample example);

    int updateByExample(@Param("record") StudentSj record, @Param("example") StudentSjExample example);

    int updateByPrimaryKeySelective(StudentSj record);

    int updateByPrimaryKey(StudentSj record);
}