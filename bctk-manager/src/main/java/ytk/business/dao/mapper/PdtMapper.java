package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.Pdt;
import ytk.business.pojo.po.PdtExample;

public interface PdtMapper {
    int countByExample(PdtExample example);

    int deleteByExample(PdtExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(Pdt record);

    int insertSelective(Pdt record);

    List<Pdt> selectByExample(PdtExample example);

    Pdt selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") Pdt record, @Param("example") PdtExample example);

    int updateByExample(@Param("record") Pdt record, @Param("example") PdtExample example);

    int updateByPrimaryKeySelective(Pdt record);

    int updateByPrimaryKey(Pdt record);
}