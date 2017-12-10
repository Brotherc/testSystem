package ytk.business.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.dao.mapper.BssSysSysuserroleMapper;
import ytk.base.dao.mapper.ClassMapper;
import ytk.base.dao.mapper.DictinfoMapper;
import ytk.base.dao.mapper.NjMapper;
import ytk.base.dao.mapper.StudentMapper;
import ytk.base.dao.mapper.XiMapper;
import ytk.base.dao.mapper.ZyMapper;
import ytk.base.pojo.po.BssSysSysuserrole;
import ytk.base.pojo.po.Class;
import ytk.base.pojo.po.ClassExample;
import ytk.base.pojo.po.Dictinfo;
import ytk.base.pojo.po.DictinfoExample;
import ytk.base.pojo.po.DictinfoExample.Criteria;
import ytk.base.pojo.po.Nj;
import ytk.base.pojo.po.NjExample;
import ytk.base.pojo.po.Student;
import ytk.base.pojo.po.StudentExample;
import ytk.base.pojo.po.Xi;
import ytk.base.pojo.po.XiExample;
import ytk.base.pojo.po.Zy;
import ytk.base.pojo.po.ZyExample;
import ytk.base.process.context.Config;
import ytk.base.process.result.ExceptionResultInfo;
import ytk.base.process.result.ResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.util.HxlsOptRowsInterface;
import ytk.util.UUIDBuild;

/**
 * 测试导入数据接口
 * @author Thinkpad
 *
 */
public class HxlsOptRowsInterfaceKsglStudentImpl implements HxlsOptRowsInterface {
	
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private DictinfoMapper dictinfoMapper;
	@Autowired
	private XiMapper xiMapper;
	@Autowired
	private ZyMapper zyMapper;
	@Autowired
	private NjMapper njMapper;
	@Autowired
	private ClassMapper classMapper;
	@Autowired
	private BssSysSysuserroleMapper bssSysSysuserroleMapper;
	
	@Override
	public String optRows(int sheetIndex, int curRow, List<String> rowlist)
			throws Exception {
		try {
			//得到导入的数据
			//rowlist数据 是一行数据，按照模版解析
			String studentId=rowlist.get(0);
			String studentName=rowlist.get(1);
			String xiName=rowlist.get(2);
			String zyName=rowlist.get(3);
			String njName=rowlist.get(4);
			String className=rowlist.get(5);
			
			//如果学号重复，则跳过不导入，否则添加考试学生
			StudentExample studentExample=new StudentExample();
			StudentExample.Criteria studentCriteria = studentExample.createCriteria();
			studentCriteria.andStudentIdEqualTo(studentId);
			List<Student> studentList = studentMapper.selectByExample(studentExample);
			if(studentList!=null&&studentList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 1311, null));
			System.out.println("考试学生导入");
			
			//添加的系信息必须存在
			XiExample xiExample=new XiExample();
			XiExample.Criteria xiCriteria = xiExample.createCriteria();
			xiCriteria.andNameEqualTo(xiName);
			
			List<Xi> xiList = xiMapper.selectByExample(xiExample);
			if(xiList==null||xiList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1313,new Object[]{studentId} ));
			Xi xi = xiList.get(0);
			
			//添加的专业信息必须存在
			ZyExample zyExample=new ZyExample();
			ZyExample.Criteria zyCriteria = zyExample.createCriteria();
			zyCriteria.andNameEqualTo(zyName);
			List<Zy> zyList = zyMapper.selectByExample(zyExample);
			if(zyList==null||zyList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1314,new Object[]{studentId} ));
			Zy zy = zyList.get(0);
			
			//添加的年级信息必须存在
			NjExample njExample=new NjExample();
			NjExample.Criteria njCriteria = njExample.createCriteria();
			njCriteria.andNjnaneEqualTo(njName);
			njCriteria.andStatusEqualTo(1);
			List<Nj> njList = njMapper.selectByExample(njExample);
			if(njList==null||njList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1315,new Object[]{studentId} ));
			Nj nj = njList.get(0);
			
			//添加的班级信息必须存在
			ClassExample classExample=new ClassExample();
			ClassExample.Criteria classCriteria = classExample.createCriteria();
			classCriteria.andClassnameEqualTo(className);
			classCriteria.andZyuuidEqualTo(zy.getUuid());
			classCriteria.andNjuuidEqualTo(nj.getUuid());
			List<Class> classList = classMapper.selectByExample(classExample);
			if(classList==null||classList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1316, new Object[]{studentId}));
			Class classCustom = classList.get(0);

			//添加的班级信息必须在相应系中（即专业在相应系中）
			zyCriteria.andXuuidEqualTo(xi.getUuid());
			List<Zy> zyList2 = zyMapper.selectByExample(zyExample);
			if(zyList2==null||zyList2.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1317, new Object[]{studentId}));
			
			Student student=new Student();
			//设置uuid
			String uuid = UUIDBuild.getUUID();
			student.setUuid(uuid);
			//设置学号
			student.setStudentId(studentId);
			//设置姓名
			student.setStudentName(studentName);
			//补全班级信息
			student.setClassUuid(classCustom.getUuid());
			
			studentMapper.insert(student);
			
			//查询学生用户角色id
			DictinfoExample dictinfoExample=new DictinfoExample();
			Criteria dictinfoCriteria = dictinfoExample.createCriteria();
			dictinfoCriteria.andTypecodeEqualTo("s01");
			dictinfoCriteria.andDictcodeEqualTo("3");
			List<Dictinfo> dictinfoList = dictinfoMapper.selectByExample(dictinfoExample);
			Dictinfo dictinfo=null;
			if(dictinfoList!=null&&dictinfoList.size()>0)
				dictinfoList.get(0);
			
			//添加用户角色信息
			BssSysSysuserrole bssSysSysuserrole=new BssSysSysuserrole();
			bssSysSysuserrole.setSrid(UUIDBuild.getUUID());
			bssSysSysuserrole.setSysuserid(uuid);
			bssSysSysuserrole.setRoleid(dictinfo.getRemark());
			
			//添加用户角色信息
			bssSysSysuserroleMapper.insert(bssSysSysuserrole);
			
		} catch(ExceptionResultInfo e){
			e.printStackTrace();
			ResultInfo resultInfo = e.getResultInfo();
			int type = resultInfo.getType();
			if(type==0){
				return resultInfo.getMessage();
			}else if(type==2){
				return "学号重复";
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "导入失败！";
		}	
		return "success";
	}
	
}
