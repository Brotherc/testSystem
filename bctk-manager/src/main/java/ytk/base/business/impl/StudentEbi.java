package ytk.base.business.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.business.StudentEbo;
import ytk.base.dao.mapper.StudentMapper;
import ytk.base.dao.mapper.SysuserMapperCustom;
import ytk.base.pojo.po.Student;
import ytk.base.pojo.po.StudentExample;
import ytk.base.pojo.po.StudentExample.Criteria;
import ytk.base.pojo.vo.Menu;
import ytk.base.pojo.vo.Operation;
import ytk.base.pojo.vo.StudentCustom;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;

public class StudentEbi implements StudentEbo{

	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private SysuserMapperCustom sysuserMapperCustom;
	
	@Override
	public StudentCustom loginCheck(String studentId,String lastloginip) throws Exception {
		//检验该用户是否存在
		Student student=findStudentByStudentId(studentId);
		if(student==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 108, null));

		StudentCustom studentCustom=new StudentCustom();
		BeanUtils.copyProperties(student, studentCustom);
		
		
		// 取出用户的菜单
		//根据角色id获取菜单
		List<Menu> menu_list = sysuserMapperCustom.findMenuBySysuserUuid(student.getUuid());
		for(Menu menu:menu_list){
			System.out.println(menu.getMenuname());
		}
		Menu menu = new Menu();
		menu.setMenus(menu_list);
		studentCustom.setMenu(menu);//将用户菜单存入用户身份对象中
		
		//根据用户角色获取操作权限
		List<Operation> operations = sysuserMapperCustom.findOperatBySysuserUuid(student.getUuid());
		for(Operation operation:operations){
			System.out.println(operation.getOperationName());
		}
		studentCustom.setOperationList(operations);//将用户操作权限存入用户身份对象中
		
		//修改用户的最后登录时间
		long time = System.currentTimeMillis();
		studentCustom.setLastlogintime(time);
		
		//修改用户最后登录的ip
		studentCustom.setLastloginip(lastloginip);
		
		//更新学生信息
		studentMapper.updateByPrimaryKey(studentCustom);
		
		return studentCustom;
	}

	@Override
	public Student findStudentByStudentId(String studentId) throws Exception {
		StudentExample studentExample=new StudentExample();
		Criteria studentCriteria = studentExample.createCriteria();
		studentCriteria.andStudentIdEqualTo(studentId);
		List<Student> studentList = studentMapper.selectByExample(studentExample);
		if(studentList!=null&&studentList.size()>0)
			return studentList.get(0);
		return null;
	}

	@Override
	public String findStudentNameByStudentId(String studentId) throws Exception {
		StudentExample studentExample=new StudentExample();
		Criteria studentCriteria = studentExample.createCriteria();
		studentCriteria.andStudentIdEqualTo(studentId);
		List<Student> studentList = studentMapper.selectByExample(studentExample);
		if(studentList!=null&&studentList.size()>0)
			return studentList.get(0).getStudentName();
		return null;
	}

}
