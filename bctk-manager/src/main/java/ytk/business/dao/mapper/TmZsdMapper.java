package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.TmZsd;
import ytk.business.pojo.po.TmZsdExample;

public interface TmZsdMapper {
    int countByExample(TmZsdExample example);

    int deleteByExample(TmZsdExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(TmZsd record);

    int insertSelective(TmZsd record);

    List<TmZsd> selectByExample(TmZsdExample example);

    TmZsd selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") TmZsd record, @Param("example") TmZsdExample example);

    int updateByExample(@Param("record") TmZsd record, @Param("example") TmZsdExample example);

    int updateByPrimaryKeySelective(TmZsd record);

    int updateByPrimaryKey(TmZsd record);
}