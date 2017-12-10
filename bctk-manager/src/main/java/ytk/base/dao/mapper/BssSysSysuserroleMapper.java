package ytk.base.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ytk.base.pojo.po.BssSysSysuserrole;
import ytk.base.pojo.po.BssSysSysuserroleExample;

public interface BssSysSysuserroleMapper {
    int countByExample(BssSysSysuserroleExample example);

    int deleteByExample(BssSysSysuserroleExample example);

    int deleteByPrimaryKey(String srid);

    int insert(BssSysSysuserrole record);

    int insertSelective(BssSysSysuserrole record);

    List<BssSysSysuserrole> selectByExample(BssSysSysuserroleExample example);

    BssSysSysuserrole selectByPrimaryKey(String srid);

    int updateByExampleSelective(@Param("record") BssSysSysuserrole record, @Param("example") BssSysSysuserroleExample example);

    int updateByExample(@Param("record") BssSysSysuserrole record, @Param("example") BssSysSysuserroleExample example);

    int updateByPrimaryKeySelective(BssSysSysuserrole record);

    int updateByPrimaryKey(BssSysSysuserrole record);
}