package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.Sjmb;
import ytk.business.pojo.po.SjmbExample;

public interface SjmbMapper {
    int countByExample(SjmbExample example);

    int deleteByExample(SjmbExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(Sjmb record);

    int insertSelective(Sjmb record);

    List<Sjmb> selectByExample(SjmbExample example);

    Sjmb selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") Sjmb record, @Param("example") SjmbExample example);

    int updateByExample(@Param("record") Sjmb record, @Param("example") SjmbExample example);

    int updateByPrimaryKeySelective(Sjmb record);

    int updateByPrimaryKey(Sjmb record);
}