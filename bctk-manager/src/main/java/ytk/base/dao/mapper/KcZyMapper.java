package ytk.base.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.base.pojo.po.KcZy;
import ytk.base.pojo.po.KcZyExample;

public interface KcZyMapper {
    int countByExample(KcZyExample example);

    int deleteByExample(KcZyExample example);

    int deleteByPrimaryKey(Long uuid);

    int insert(KcZy record);

    int insertSelective(KcZy record);

    List<KcZy> selectByExample(KcZyExample example);

    KcZy selectByPrimaryKey(Long uuid);

    int updateByExampleSelective(@Param("record") KcZy record, @Param("example") KcZyExample example);

    int updateByExample(@Param("record") KcZy record, @Param("example") KcZyExample example);

    int updateByPrimaryKeySelective(KcZy record);

    int updateByPrimaryKey(KcZy record);
}