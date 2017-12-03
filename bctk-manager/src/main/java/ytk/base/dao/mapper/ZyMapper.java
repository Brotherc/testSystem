package ytk.base.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.base.pojo.po.Zy;
import ytk.base.pojo.po.ZyExample;

public interface ZyMapper {
    int countByExample(ZyExample example);

    int deleteByExample(ZyExample example);

    int deleteByPrimaryKey(Long uuid);

    int insert(Zy record);

    int insertSelective(Zy record);

    List<Zy> selectByExample(ZyExample example);

    Zy selectByPrimaryKey(Long uuid);

    int updateByExampleSelective(@Param("record") Zy record, @Param("example") ZyExample example);

    int updateByExample(@Param("record") Zy record, @Param("example") ZyExample example);

    int updateByPrimaryKeySelective(Zy record);

    int updateByPrimaryKey(Zy record);
}