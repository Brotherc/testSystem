package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.Dxt;
import ytk.business.pojo.po.DxtExample;

public interface DxtMapper {
    int countByExample(DxtExample example);

    int deleteByExample(DxtExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(Dxt record);

    int insertSelective(Dxt record);

    List<Dxt> selectByExample(DxtExample example);

    Dxt selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") Dxt record, @Param("example") DxtExample example);

    int updateByExample(@Param("record") Dxt record, @Param("example") DxtExample example);

    int updateByPrimaryKeySelective(Dxt record);

    int updateByPrimaryKey(Dxt record);
}