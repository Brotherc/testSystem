package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.Dxxzt;
import ytk.business.pojo.po.DxxztExample;

public interface DxxztMapper {
    int countByExample(DxxztExample example);

    int deleteByExample(DxxztExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(Dxxzt record);

    int insertSelective(Dxxzt record);

    List<Dxxzt> selectByExample(DxxztExample example);

    Dxxzt selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") Dxxzt record, @Param("example") DxxztExample example);

    int updateByExample(@Param("record") Dxxzt record, @Param("example") DxxztExample example);

    int updateByPrimaryKeySelective(Dxxzt record);

    int updateByPrimaryKey(Dxxzt record);
}