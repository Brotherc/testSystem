package ytk.business.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.business.pojo.po.Jdt;
import ytk.business.pojo.po.JdtExample;

public interface JdtMapper {
    int countByExample(JdtExample example);

    int deleteByExample(JdtExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(Jdt record);

    int insertSelective(Jdt record);

    List<Jdt> selectByExample(JdtExample example);

    Jdt selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") Jdt record, @Param("example") JdtExample example);

    int updateByExample(@Param("record") Jdt record, @Param("example") JdtExample example);

    int updateByPrimaryKeySelective(Jdt record);

    int updateByPrimaryKey(Jdt record);
}