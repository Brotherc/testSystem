package ytk.base.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.base.pojo.po.Xi;
import ytk.base.pojo.po.XiExample;

public interface XiMapper {
    int countByExample(XiExample example);

    int deleteByExample(XiExample example);

    int deleteByPrimaryKey(Long uuid);

    int insert(Xi record);

    int insertSelective(Xi record);

    List<Xi> selectByExample(XiExample example);

    Xi selectByPrimaryKey(Long uuid);

    int updateByExampleSelective(@Param("record") Xi record, @Param("example") XiExample example);

    int updateByExample(@Param("record") Xi record, @Param("example") XiExample example);

    int updateByPrimaryKeySelective(Xi record);

    int updateByPrimaryKey(Xi record);
}