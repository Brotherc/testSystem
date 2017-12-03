package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.Sjda;
import ytk.business.pojo.po.SjdaExample;

public interface SjdaMapper {
    int countByExample(SjdaExample example);

    int deleteByExample(SjdaExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(Sjda record);

    int insertSelective(Sjda record);

    List<Sjda> selectByExample(SjdaExample example);

    Sjda selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") Sjda record, @Param("example") SjdaExample example);

    int updateByExample(@Param("record") Sjda record, @Param("example") SjdaExample example);

    int updateByPrimaryKeySelective(Sjda record);

    int updateByPrimaryKey(Sjda record);
}