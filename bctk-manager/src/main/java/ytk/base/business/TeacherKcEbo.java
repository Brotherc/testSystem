package ytk.base.business;

import java.util.List;

import ytk.base.pojo.po.TeacherKc;

public interface TeacherKcEbo {
	//根据课程uuid查询任课教师
	public List<TeacherKc> findTeacherKcByKcUuid(Long kcuuid) throws Exception;
}
