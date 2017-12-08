package ytk.business.business;

import java.util.List;

import ytk.base.pojo.vo.ClassCustom;
import ytk.base.pojo.vo.SysuserCustom;
import ytk.base.pojo.vo.SysuserQueryVo;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.pojo.vo.KsglStudentCustom;
import ytk.business.pojo.vo.KsglStudentQueryVo;

public interface KsglStudentEbo {
	public List<KsglStudentCustom> findKsglStudentList(KsglStudentQueryVo ksglStudentQueryVo) throws Exception;
	
	public int findKsglStudentListCount(KsglStudentQueryVo ksglStudentQueryVo) throws Exception;
	
	public void deleteKsglStudent(String uuid,String ksgluuid) throws Exception;
	
	public void addKsglStudentCustom(String ksgluuid,SysuserCustom sysuserCustom,ClassCustom classCustom) throws Exception;
	
	public KsglStudentCustom findKsglStudentListByUuid(String uuid) throws Exception;
	
	public void updateKsglStudent(String uuid,SysuserCustom sysuserCustom,ClassCustom classCustom) throws Exception;
	
	public List<SysuserCustom> findKsglStudentAddList(SysuserQueryVo sysuserQueryVo) throws Exception;
	
	public int findKsglStudentAddListCount(SysuserQueryVo sysuserQueryVo) throws Exception;
	
	public void addKsglStudentChoose(String ksgluuid,Long sysuseruuid) throws Exception;
	
	public SubmitResultInfo importKsglStudent(String filePath) throws Exception;

	public void updateKsglStudentStatus(String ksgluuid, Long uuid,Integer status) throws Exception;
}
