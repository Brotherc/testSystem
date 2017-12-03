package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.Sj;
import ytk.business.pojo.po.SjExample;

public interface SjMapper {
    int countByExample(SjExample example);

    int deleteByExample(SjExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(Sj record);

    int insertSelective(Sj record);

    List<Sj> selectByExample(SjExample example);

    Sj selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") Sj record, @Param("example") SjExample example);

    int updateByExample(@Param("record") Sj record, @Param("example") SjExample example);

    int updateByPrimaryKeySelective(Sj record);

    int updateByPrimaryKey(Sj record);
}