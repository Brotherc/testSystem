package ytk.base.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.base.pojo.po.Kc;
import ytk.base.pojo.po.KcExample;

public interface KcMapper {
    int countByExample(KcExample example);

    int deleteByExample(KcExample example);

    int deleteByPrimaryKey(Long uuid);

    int insert(Kc record);

    int insertSelective(Kc record);

    List<Kc> selectByExample(KcExample example);

    Kc selectByPrimaryKey(Long uuid);

    int updateByExampleSelective(@Param("record") Kc record, @Param("example") KcExample example);

    int updateByExample(@Param("record") Kc record, @Param("example") KcExample example);

    int updateByPrimaryKeySelective(Kc record);

    int updateByPrimaryKey(Kc record);
}