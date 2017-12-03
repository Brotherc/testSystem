package ytk.base.business;

import java.util.List;

import ytk.base.pojo.po.Nj;
import ytk.base.pojo.vo.NjQueryVo;


public interface NjEbo {
	
	//通过状态查询年级信息
	public List<Nj> findNjListByStatus(int status) throws Exception;

	//通过条件查询年级信息数量
	public int findNjListCount(NjQueryVo njQueryVo) throws Exception;

	//通过条件查询年级信息
	public List<Nj> findNjList(NjQueryVo njQueryVo) throws Exception;

	//添加年级信息
	public void addNj(Nj nj) throws Exception;

	//根据年级uuid更新年级状态
	public void updateNjStatus(Long uuid,Integer status) throws Exception;
}
