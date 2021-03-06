package ytk.business.business.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.dao.mapper.BssSysSysuserroleMapper;
import ytk.base.dao.mapper.ClassMapper;
import ytk.base.dao.mapper.ClassMapperCustom;
import ytk.base.dao.mapper.KcZyMapper;
import ytk.base.dao.mapper.NjMapper;
import ytk.base.dao.mapper.StudentMapper;
import ytk.base.dao.mapper.StudentMapperCustom;
import ytk.base.dao.mapper.SysuserMapper;
import ytk.base.dao.mapper.SysuserMapperCustom;
import ytk.base.dao.mapper.XiMapper;
import ytk.base.dao.mapper.ZyMapper;
import ytk.base.pojo.po.BssSysSysuserrole;
import ytk.base.pojo.po.Class;
import ytk.base.pojo.po.KcZy;
import ytk.base.pojo.po.KcZyExample;
import ytk.base.pojo.po.Nj;
import ytk.base.pojo.po.NjExample;
import ytk.base.pojo.po.Student;
import ytk.base.pojo.po.StudentExample;
import ytk.base.pojo.po.Xi;
import ytk.base.pojo.po.Zy;
import ytk.base.pojo.po.ZyExample;
import ytk.base.pojo.vo.ClassCustom;
import ytk.base.pojo.vo.ClassQueryVo;
import ytk.base.pojo.vo.StudentCustom;
import ytk.base.pojo.vo.StudentQueryVo;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.business.KsglStudentEbo;
import ytk.business.dao.mapper.KsglMapper;
import ytk.business.dao.mapper.KsglStudentMapper;
import ytk.business.dao.mapper.KsglStudentMapperCustom;
import ytk.business.dao.mapper.StudentSjMapper;
import ytk.business.dao.mapper.StudentSjdaMapper;
import ytk.business.pojo.po.Ksgl;
import ytk.business.pojo.po.KsglStudent;
import ytk.business.pojo.po.KsglStudentExample;
import ytk.business.pojo.po.StudentSj;
import ytk.business.pojo.po.StudentSjExample;
import ytk.business.pojo.po.StudentSjdaExample;
import ytk.business.pojo.vo.KsglStudentCustom;
import ytk.business.pojo.vo.KsglStudentQueryVo;
import ytk.util.HxlsOptRowsInterface;
import ytk.util.HxlsRead;
import ytk.util.MyUtil;
import ytk.util.UUIDBuild;

public class KsglStudentEbi implements KsglStudentEbo{

	@Autowired
	private KsglStudentMapperCustom ksglStudentMapperCustom;
	@Autowired
	private KsglMapper ksglMapper;
	@Autowired
	private KsglStudentMapper ksglStudentMapper;
	@Autowired
	private SysuserMapper sysuserMapper;
	@Autowired
	private XiMapper xiMapper;
	@Autowired
	private ZyMapper zyMapper;
	@Autowired
	private NjMapper njMapper;
	@Autowired
	private ClassMapperCustom classMapperCustom;
	@Autowired
	private KcZyMapper kcZyMapper;
	@Autowired
	private SysuserMapperCustom sysuserMapperCustom;
	@Autowired
	private ClassMapper classMapper;
	@Autowired
	private StudentSjMapper studentSjMapper;
	@Autowired
	private StudentSjdaMapper studentSjdaMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private StudentMapperCustom studentMapperCustom;
	@Autowired
	private BssSysSysuserroleMapper bssSysSysuserroleMapper;
	
	@Autowired
	private HxlsOptRowsInterface hxlsOptRowsInterfaceKsglStudent;
	
	@Override
	public List<KsglStudentCustom> findKsglStudentList(
			KsglStudentQueryVo ksglStudentQueryVo) throws Exception {
		return ksglStudentMapperCustom.findKsglStudentList(ksglStudentQueryVo);
	}

	@Override
	public int findKsglStudentListCount(KsglStudentQueryVo ksglStudentQueryVo)
			throws Exception {
		return ksglStudentMapperCustom.findKsglStudentListCount(ksglStudentQueryVo);
	}

	@Override
	public void deleteKsglStudent(String uuid, String ksgluuid)
			throws Exception {
		Ksgl ksgl = ksglMapper.selectByPrimaryKey(ksgluuid);
		//只允许删除待考和已考状态的考试的考试人员
		if(ksgl!=null){
			Integer status = ksgl.getStatus();
			if(status==3)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1118, null));
		}
		String studentUuid = ksglStudentMapper.selectByPrimaryKey(uuid).getStudentUuid();
		
		ksglStudentMapper.deleteByPrimaryKey(uuid);

		//将该学生考试试卷删除
		StudentSjExample studentSjExample=new StudentSjExample();
		StudentSjExample.Criteria studentSjCriteria = studentSjExample.createCriteria();
		studentSjCriteria.andKsgluuidEqualTo(ksgluuid);
		studentSjCriteria.andStudentUuidEqualTo(studentUuid);
		List<StudentSj> studentSjList = studentSjMapper.selectByExample(studentSjExample);
		if(studentSjList!=null&&studentSjList.size()>0){
			String studentSjUuid = studentSjList.get(0).getUuid();
			studentSjMapper.deleteByExample(studentSjExample);
			
			//删除该学生试卷答案
			StudentSjdaExample studentSjdaExample=new StudentSjdaExample();
			StudentSjdaExample.Criteria studentSjDaCriteria = studentSjdaExample.createCriteria();
			studentSjDaCriteria.andStudentsjidEqualTo(studentSjUuid);
			studentSjdaMapper.deleteByExample(studentSjdaExample);
		}
	}

	@Override
	public void addKsglStudentCustom(String ksgluuid,StudentCustom studentCustom,
			ClassCustom classCustom,String roleId) throws Exception {
		//非空判断
		String studentId = studentCustom.getStudentId();
		if(!MyUtil.isNotNullAndEmptyByTrim(studentId))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"学号"}));
		
		String name = studentCustom.getStudentName();
		if(!MyUtil.isNotNullAndEmptyByTrim(name))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"姓名"}));
		
		Long xuuid = studentCustom.getXiUuid();
		if(xuuid==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"系"}));
		
		String zyname = classCustom.getZyname();
		if(!MyUtil.isNotNullAndEmptyByTrim(zyname))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"专业"}));
		
		Long njuuid = classCustom.getNjuuid();
		if(njuuid==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"年级"}));
		
		String classname = classCustom.getClassname();
		if(!MyUtil.isNotNullAndEmptyByTrim(classname))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"班级"}));
		
		Ksgl ksgl = ksglMapper.selectByPrimaryKey(ksgluuid);
		//只有当考试管理状态为待考试，才允许添加考试学生
		Integer status = ksgl.getStatus();
		if(status==2||status==3)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1308, null));
		
		//添加的学号不允许重复
		StudentExample studentExample=new StudentExample();
		StudentExample.Criteria studentCriteria = studentExample.createCriteria();
		studentCriteria.andStudentIdEqualTo(studentId);
		List<Student> studentList = studentMapper.selectByExample(studentExample);
		if(studentList!=null&&studentList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1301, null));
		
		//添加的系信息必须存在
		Xi xi = xiMapper.selectByPrimaryKey(xuuid);
		if(xi==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1302, null));
		
		//添加的专业信息必须存在
		ZyExample zyExample=new ZyExample();
		ZyExample.Criteria zyCriteria = zyExample.createCriteria();
		zyCriteria.andNameEqualTo(zyname);
		List<Zy> zyList = zyMapper.selectByExample(zyExample);
		if(zyList==null||zyList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1303, null));
		Zy zy = zyList.get(0);
		
		//添加的年级信息必须存在
		NjExample njExample=new NjExample();
		NjExample.Criteria njCriteria = njExample.createCriteria();
		njCriteria.andUuidEqualTo(njuuid);
		njCriteria.andStatusEqualTo(1);
		List<Nj> njList = njMapper.selectByExample(njExample);
		if(njList==null||njList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1304, null));
		
		//添加的班级信息必须存在
		ClassQueryVo classQueryVo=new ClassQueryVo();
		classQueryVo.setClassCustom(classCustom);
		List<ClassCustom> classList = classMapperCustom.findClassList(classQueryVo);
		if(classList==null||classList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1305, null));
		classCustom = classList.get(0);

		//添加的班级信息必须在相应系中（即专业在相应系中）
		zyCriteria.andXuuidEqualTo(xuuid);
		List<Zy> zyList2 = zyMapper.selectByExample(zyExample);
		if(zyList2==null||zyList2.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1306, null));
		
		
		//学生所属专业必须有相应考试课程信息

		Long kcuuid = ksgl.getKcuuid();
		KcZyExample kcZyExample=new KcZyExample();
		KcZyExample.Criteria kczyCriteria = kcZyExample.createCriteria();
		kczyCriteria.andKcuuidEqualTo(kcuuid);
		kczyCriteria.andZyuuidEqualTo(zy.getUuid());
		List<KcZy> kcZyList = kcZyMapper.selectByExample(kcZyExample);
		if(kcZyList==null||kcZyList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1307, null));
		
		//补全uuid
		String studentUuid = UUIDBuild.getUUID();
		studentCustom.setUuid(studentUuid);
		//补全班级信息
		studentCustom.setClassUuid(classCustom.getUuid());
		studentMapper.insert(studentCustom);
		
		//添加用户角色信息
		BssSysSysuserrole bssSysSysuserrole=new BssSysSysuserrole();
		bssSysSysuserrole.setSrid(UUIDBuild.getUUID());
		bssSysSysuserrole.setSysuserid(studentUuid);
		bssSysSysuserrole.setRoleid(roleId);
		
		//添加用户角色信息
		bssSysSysuserroleMapper.insert(bssSysSysuserrole);
		
		//添加考试学生信息
		
		KsglStudent ksglStudent=new KsglStudent();
		String uuid = UUIDBuild.getUUID();
		ksglStudent.setUuid(uuid);
		ksglStudent.setKsgluuid(ksgluuid);
		ksglStudent.setStatus(1);
		ksglStudent.setStudentUuid(studentUuid);
		ksglStudentMapper.insert(ksglStudent);
	}

	@Override
	public KsglStudentCustom findKsglStudentListByUuid(String uuid)
			throws Exception {
		KsglStudentQueryVo ksglStudentQueryVo=new KsglStudentQueryVo();
		KsglStudentCustom ksglStudentCustom=new KsglStudentCustom();
		ksglStudentCustom.setUuid(uuid);
		ksglStudentQueryVo.setKsglStudentCustom(ksglStudentCustom);
		
		List<KsglStudentCustom> ksglStudentList = ksglStudentMapperCustom.findKsglStudentList(ksglStudentQueryVo);
		
		if(ksglStudentList==null||ksglStudentList.size()<1)
			return null;
		return ksglStudentList.get(0);
	}

	@Override
	public void updateKsglStudent(String uuid, StudentCustom studentCustom,
			ClassCustom classCustom) throws Exception {
		//非空判断
		String studentId = studentCustom.getStudentId();
		if(!MyUtil.isNotNullAndEmptyByTrim(studentId))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"学号"}));
		
		String name = studentCustom.getStudentName();
		if(!MyUtil.isNotNullAndEmptyByTrim(name))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"姓名"}));
		
		Long xuuid = studentCustom.getXiUuid();
		if(xuuid==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"系"}));
		
		String zyname = classCustom.getZyname();
		if(!MyUtil.isNotNullAndEmptyByTrim(zyname))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"专业"}));
		
		Long njuuid = classCustom.getNjuuid();
		if(njuuid==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"年级"}));
		
		String classname = classCustom.getClassname();
		if(!MyUtil.isNotNullAndEmptyByTrim(classname))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"班级"}));
		
		//查询未修改前考试学生信息
		KsglStudentQueryVo ksglStudentQueryVo=new KsglStudentQueryVo();
		KsglStudentCustom ksglStudentCustom=new KsglStudentCustom();
		ksglStudentCustom.setUuid(uuid);
		ksglStudentQueryVo.setKsglStudentCustom(ksglStudentCustom);
		List<KsglStudentCustom> ksglStudentList = ksglStudentMapperCustom.findKsglStudentList(ksglStudentQueryVo);
		
		KsglStudentCustom ksglStudent = ksglStudentList.get(0);
		
		Ksgl ksgl = ksglMapper.selectByPrimaryKey(ksglStudent.getKsgluuid());
		//只有当考试管理状态为待考试，才允许修改考试学生
		Integer status = ksgl.getStatus();
		if(status==2||status==3)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1309, null));
		
		Student student = studentMapper.selectByPrimaryKey(ksglStudent.getStudentUuid());
		//如果修改了学号，则修改的学号不允许重复
		if(!ksglStudent.getStudentId().equals(studentCustom.getStudentId())){
			StudentExample studentExample=new StudentExample();
			StudentExample.Criteria studentCriteria = studentExample.createCriteria();
			studentCriteria.andStudentIdEqualTo(studentId);
			List<Student> studentList = studentMapper.selectByExample(studentExample);
			if(studentList!=null&&studentList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1310, null));
			
			//修改学号
			student.setStudentId(studentId);
		}

		
		//修改的系信息必须存在
		Xi xi = xiMapper.selectByPrimaryKey(xuuid);
		if(xi==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1302, null));
		
		//修改的专业信息必须存在
		ZyExample zyExample=new ZyExample();
		ZyExample.Criteria zyCriteria = zyExample.createCriteria();
		zyCriteria.andNameEqualTo(zyname);
		List<Zy> zyList = zyMapper.selectByExample(zyExample);
		if(zyList==null||zyList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1303, null));
		Zy zy = zyList.get(0);
		
		//修改的年级信息必须存在
		NjExample njExample=new NjExample();
		NjExample.Criteria njCriteria = njExample.createCriteria();
		njCriteria.andUuidEqualTo(njuuid);
		njCriteria.andStatusEqualTo(1);
		List<Nj> njList = njMapper.selectByExample(njExample);
		if(njList==null||njList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1304, null));
		
		//修改的班级信息必须存在
		ClassQueryVo classQueryVo=new ClassQueryVo();
		classQueryVo.setClassCustom(classCustom);
		List<ClassCustom> classList = classMapperCustom.findClassList(classQueryVo);
		if(classList==null||classList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1305, null));
		classCustom = classList.get(0);

		//修改的班级信息必须在相应系中（即专业在相应系中）
		zyCriteria.andXuuidEqualTo(xuuid);
		List<Zy> zyList2 = zyMapper.selectByExample(zyExample);
		if(zyList2==null||zyList2.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1306, null));
		
		
		//学生所属专业必须有相应考试课程信息
		Long kcuuid = ksgl.getKcuuid();
		KcZyExample kcZyExample=new KcZyExample();
		KcZyExample.Criteria kczyCriteria = kcZyExample.createCriteria();
		kczyCriteria.andKcuuidEqualTo(kcuuid);
		kczyCriteria.andZyuuidEqualTo(zy.getUuid());
		List<KcZy> kcZyList = kcZyMapper.selectByExample(kcZyExample);
		if(kcZyList==null||kcZyList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1307, null));
		
		//修改学生姓名
		student.setStudentName(name);
		
		//修改学生班级信息
		student.setClassUuid(classCustom.getUuid());
		
		studentMapper.updateByPrimaryKey(student);
	}

	@Override
	public List<StudentCustom> findKsglStudentAddList(
			StudentQueryVo studentQueryVo) throws Exception {
		return studentMapperCustom.findKsglStudentAddList(studentQueryVo);
	}

	@Override
	public int findKsglStudentAddListCount(StudentQueryVo studentQueryVo)
			throws Exception {
		return studentMapperCustom.findKsglStudentAddListCount(studentQueryVo);
	}

	@Override
	public void addKsglStudentChoose(String ksgluuid, String studentUuid)
			throws Exception {
		
		Ksgl ksgl = ksglMapper.selectByPrimaryKey(ksgluuid);
		//只有当考试管理状态为待考试，才允许添加考试学生
		Integer status = ksgl.getStatus();
		if(status==2||status==3)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1308, null));
		
		//学生所属专业存在考试试卷课程
		Student student = studentMapper.selectByPrimaryKey(studentUuid);
		Class classCustom = classMapper.selectByPrimaryKey(student.getClassUuid());
		KcZyExample kcZyExample=new KcZyExample();
		KcZyExample.Criteria kcZyCriteria = kcZyExample.createCriteria();
		kcZyCriteria.andKcuuidEqualTo(ksgl.getKcuuid());
		kcZyCriteria.andZyuuidEqualTo(classCustom.getZyuuid());
		List<KcZy> kcZyList = kcZyMapper.selectByExample(kcZyExample);
		if(kcZyList==null||kcZyList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1307, null));
		
		KsglStudent ksglStudent=new KsglStudent();
		String uuid = UUIDBuild.getUUID();
		ksglStudent.setUuid(uuid);
		ksglStudent.setKsgluuid(ksgluuid);
		ksglStudent.setStudentUuid(studentUuid);
		ksglStudent.setStatus(1);
		ksglStudentMapper.insert(ksglStudent);
	}

	@Override
	public SubmitResultInfo importKsglStudent(String filePath,String ksgluuid) throws Exception {

		Ksgl ksgl = ksglMapper.selectByPrimaryKey(ksgluuid);
		//只有当考试管理状态为待考试，才允许添加考试学生
		Integer status = ksgl.getStatus();
		if(status==2||status==3)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1308, null));
		
		
		//调用工具类进行考试学生 导入
		HxlsRead xls2csv = null;
		try {
			//第一个参数就是导入的文件
			//第二个参数就是导入文件中哪个sheet
			//第三个参数导入接口的实现类对象
			xls2csv = new HxlsRead(filePath,0,hxlsOptRowsInterfaceKsglStudent);
			xls2csv.process();
		}catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 321, null));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//提示导入成功条数
		long success_num = xls2csv.getOptRows_success();
		//导入失败数量
		long failure_num = xls2csv.getOptRows_failure();
		//导入学生学号重复数量
		long warn_num=0;
		
		//对导入失败记录处理
		//获取导入失败记录
		//xls2csv.getFailrows()
		//获取导入记录的title
		//xls2csv.getRowtitle();
		//获取导入失败原因
		List<String> list = xls2csv.getFailmsgs();
		List<ResultInfo> msgs_error = new ArrayList<ResultInfo>();
		for(String s:list){
			if(s.equals("学号重复")){
				warn_num++;
			}
			else{
				msgs_error.add(new ResultInfo(0, -1, s));
			}
		}

		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 1312, new Object[]{
				success_num,
				failure_num,
				warn_num
		}), msgs_error);
	}

	@Override
	public void updateKsglStudentStatus(String ksgluuid, String studentUuid,Integer status)
			throws Exception {
		KsglStudentExample ksglStudentExample=new KsglStudentExample();
		KsglStudentExample.Criteria ksglStudentCriteria = ksglStudentExample.createCriteria();
		ksglStudentCriteria.andKsgluuidEqualTo(ksgluuid);
		ksglStudentCriteria.andStudentUuidEqualTo(studentUuid);
		List<KsglStudent> ksglStudentList = ksglStudentMapper.selectByExample(ksglStudentExample);
		KsglStudent ksglStudent=null;
		if(ksglStudentList!=null&&ksglStudentList.size()>0)
			ksglStudent=ksglStudentList.get(0);
		ksglStudent.setStatus(status);
		ksglStudentMapper.updateByPrimaryKey(ksglStudent);
	}

	@Override
	public List<KsglStudentCustom> findJkKsglStudentList(
			KsglStudentQueryVo ksglStudentQueryVo) throws Exception {
		return ksglStudentMapperCustom.findKsglStudentListToJk(ksglStudentQueryVo);
	}

	@Override
	public int findJkKsglStudentListCount(KsglStudentQueryVo ksglStudentQueryVo)
			throws Exception {
		return ksglStudentMapperCustom.findKsglStudentListCountToJk(ksglStudentQueryVo);
	}
}
