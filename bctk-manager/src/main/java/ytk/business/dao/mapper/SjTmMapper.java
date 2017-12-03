package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.SjTm;
import ytk.business.pojo.po.SjTmExample;

public interface SjTmMapper {
    int countByExample(SjTmExample example);

    int deleteByExample(SjTmExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(SjTm record);

    int insertSelective(SjTm record);

    List<SjTm> selectByExample(SjTmExample example);

    SjTm selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") SjTm record, @Param("example") SjTmExample example);

    int updateByExample(@Param("record") SjTm record, @Param("example") SjTmExample example);

    int updateByPrimaryKeySelective(SjTm record);

    int updateByPrimaryKey(SjTm record);
}