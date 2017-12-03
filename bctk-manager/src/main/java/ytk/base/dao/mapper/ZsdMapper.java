package ytk.base.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.base.pojo.po.Zsd;
import ytk.base.pojo.po.ZsdExample;

public interface ZsdMapper {
    int countByExample(ZsdExample example);

    int deleteByExample(ZsdExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(Zsd record);

    int insertSelective(Zsd record);

    List<Zsd> selectByExample(ZsdExample example);

    Zsd selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") Zsd record, @Param("example") ZsdExample example);

    int updateByExample(@Param("record") Zsd record, @Param("example") ZsdExample example);

    int updateByPrimaryKeySelective(Zsd record);

    int updateByPrimaryKey(Zsd record);
}