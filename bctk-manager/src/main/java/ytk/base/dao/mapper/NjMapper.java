package ytk.base.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.base.pojo.po.Nj;
import ytk.base.pojo.po.NjExample;

public interface NjMapper {
    int countByExample(NjExample example);

    int deleteByExample(NjExample example);

    int deleteByPrimaryKey(Long uuid);

    int insert(Nj record);

    int insertSelective(Nj record);

    List<Nj> selectByExample(NjExample example);

    Nj selectByPrimaryKey(Long uuid);

    int updateByExampleSelective(@Param("record") Nj record, @Param("example") NjExample example);

    int updateByExample(@Param("record") Nj record, @Param("example") NjExample example);

    int updateByPrimaryKeySelective(Nj record);

    int updateByPrimaryKey(Nj record);
}