package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.Tkt;
import ytk.business.pojo.po.TktExample;

public interface TktMapper {
    int countByExample(TktExample example);

    int deleteByExample(TktExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(Tkt record);

    int insertSelective(Tkt record);

    List<Tkt> selectByExample(TktExample example);

    Tkt selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") Tkt record, @Param("example") TktExample example);

    int updateByExample(@Param("record") Tkt record, @Param("example") TktExample example);

    int updateByPrimaryKeySelective(Tkt record);

    int updateByPrimaryKey(Tkt record);
}