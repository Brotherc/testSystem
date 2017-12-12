package ytk.base.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.business.TeacherKcEbo;
import ytk.base.dao.mapper.KcMapper;
import ytk.base.dao.mapper.TeacherKcMapper;
import ytk.base.pojo.po.Kc;
import ytk.base.pojo.po.TeacherKc;
import ytk.base.pojo.po.TeacherKcExample;
import ytk.base.pojo.po.TeacherKcExample.Criteria;

public class TeacherKcEbi implements TeacherKcEbo{

	@Autowired
	private TeacherKcMapper teacherKcMapper;
	@Autowired
	private KcMapper kcMapper;
	
	@Override
	public List<TeacherKc> findTeacherKcByKcUuid(Long kcuuid)
			throws Exception {
		TeacherKcExample teacherKcExample=new TeacherKcExample();
		Criteria createCriteria = teacherKcExample.createCriteria();
		createCriteria.andKcuuidEqualTo(kcuuid);
		return teacherKcMapper.selectByExample(teacherKcExample);
	}

	@Override
	public Kc findTeacherKcOneByTeacherUuid(String teacherUuid)
			throws Exception {
		TeacherKcExample teacherKcExample=new TeacherKcExample();
		Criteria teacherKcCriteria = teacherKcExample.createCriteria();
		teacherKcCriteria.andTeacheruuidEqualTo(teacherUuid);
		List<TeacherKc> teacherKcList = teacherKcMapper.selectByExample(teacherKcExample);
		if(teacherKcList!=null&&teacherKcList.size()>0){
			TeacherKc teacherKc = teacherKcList.get(0);
			return kcMapper.selectByPrimaryKey(teacherKc.getKcuuid());
		}
		return null;
	}

}
