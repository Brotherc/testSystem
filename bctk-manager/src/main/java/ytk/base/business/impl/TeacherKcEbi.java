package ytk.base.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.business.TeacherKcEbo;
import ytk.base.dao.mapper.TeacherKcMapper;
import ytk.base.pojo.po.TeacherKc;
import ytk.base.pojo.po.TeacherKcExample;
import ytk.base.pojo.po.TeacherKcExample.Criteria;

public class TeacherKcEbi implements TeacherKcEbo{

	@Autowired
	private TeacherKcMapper teacherKcMapper;
	
	@Override
	public List<TeacherKc> findTeacherKcByKcUuid(Long kcuuid)
			throws Exception {
		TeacherKcExample teacherKcExample=new TeacherKcExample();
		Criteria createCriteria = teacherKcExample.createCriteria();
		createCriteria.andKcuuidEqualTo(kcuuid);
		return teacherKcMapper.selectByExample(teacherKcExample);
	}

}
