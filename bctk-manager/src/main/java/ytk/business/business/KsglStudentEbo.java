package ytk.business.business;

import java.util.List;

import ytk.base.pojo.vo.ClassCustom;
import ytk.base.pojo.vo.StudentCustom;
import ytk.base.pojo.vo.StudentQueryVo;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.pojo.vo.KsglStudentCustom;
import ytk.business.pojo.vo.KsglStudentQueryVo;

public interface KsglStudentEbo {
	public List<KsglStudentCustom> findKsglStudentList(KsglStudentQueryVo ksglStudentQueryVo) throws Exception;
	
	public int findKsglStudentListCount(KsglStudentQueryVo ksglStudentQueryVo) throws Exception;
	
	public void deleteKsglStudent(String uuid,String ksgluuid) throws Exception;
	
	public void addKsglStudentCustom(String ksgluuid,StudentCustom studentCustom,ClassCustom classCustom,String roleId) throws Exception;
	
	public KsglStudentCustom findKsglStudentListByUuid(String uuid) throws Exception;
	
	public void updateKsglStudent(String uuid,StudentCustom studentCustom,ClassCustom classCustom) throws Exception;
	
	public List<StudentCustom> findKsglStudentAddList(StudentQueryVo studentQueryVo) throws Exception;
	
	public int findKsglStudentAddListCount(StudentQueryVo studentQueryVo) throws Exception;
	
	public void addKsglStudentChoose(String ksgluuid,String studentUuid) throws Exception;
	
	public SubmitResultInfo importKsglStudent(String filePath) throws Exception;

	public void updateKsglStudentStatus(String ksgluuid, String studentUuid,Integer status) throws Exception;
}
