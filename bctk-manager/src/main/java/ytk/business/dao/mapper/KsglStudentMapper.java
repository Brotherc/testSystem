package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.KsglStudent;
import ytk.business.pojo.po.KsglStudentExample;

public interface KsglStudentMapper {
    int countByExample(KsglStudentExample example);

    int deleteByExample(KsglStudentExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(KsglStudent record);

    int insertSelective(KsglStudent record);

    List<KsglStudent> selectByExample(KsglStudentExample example);

    KsglStudent selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") KsglStudent record, @Param("example") KsglStudentExample example);

    int updateByExample(@Param("record") KsglStudent record, @Param("example") KsglStudentExample example);

    int updateByPrimaryKeySelective(KsglStudent record);

    int updateByPrimaryKey(KsglStudent record);
}