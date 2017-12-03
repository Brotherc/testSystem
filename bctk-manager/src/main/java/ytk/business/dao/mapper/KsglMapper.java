package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.Ksgl;
import ytk.business.pojo.po.KsglExample;

public interface KsglMapper {
    int countByExample(KsglExample example);

    int deleteByExample(KsglExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(Ksgl record);

    int insertSelective(Ksgl record);

    List<Ksgl> selectByExample(KsglExample example);

    Ksgl selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") Ksgl record, @Param("example") KsglExample example);

    int updateByExample(@Param("record") Ksgl record, @Param("example") KsglExample example);

    int updateByPrimaryKeySelective(Ksgl record);

    int updateByPrimaryKey(Ksgl record);
}