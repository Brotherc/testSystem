package ytk.business.business.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.business.business.StudentSjEbo;
import ytk.business.dao.mapper.SjTmMapper;
import ytk.business.dao.mapper.SjdaMapper;
import ytk.business.dao.mapper.StudentSjMapper;
import ytk.business.dao.mapper.StudentSjMapperCustom;
import ytk.business.dao.mapper.StudentSjdaMapper;
import ytk.business.dao.mapper.TktMapper;
import ytk.business.pojo.po.SjTm;
import ytk.business.pojo.po.Sjda;
import ytk.business.pojo.po.SjdaExample;
import ytk.business.pojo.po.StudentSj;
import ytk.business.pojo.po.StudentSjda;
import ytk.business.pojo.po.StudentSjdaExample;
import ytk.business.pojo.po.StudentSjdaExample.Criteria;
import ytk.business.pojo.vo.StudentSjCustom;
import ytk.business.pojo.vo.StudentSjQueryVo;
import ytk.jedis.JedisClient;
import ytk.util.JsonUtils;
import ytk.util.UUIDBuild;

public class StudentSjEbi implements StudentSjEbo{

	@Autowired
	private StudentSjMapper studentSjMapper;
	@Autowired
	private StudentSjMapperCustom studentSjMapperCustom;
	@Autowired
	private StudentSjdaMapper studentSjdaMapper;
	@Autowired
	private SjdaMapper sjdaMapper;
	@Autowired
	private SjTmMapper sjTmMapper;
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TktMapper tktMapper;
	
	@Value("${STUDENT_SJ}")
	private String STUDENT_SJ;
	
	@Override
	public String addStudentSj(String studentUuid, String sjid,String ksgluuid) throws Exception { 
		StudentSj studentSj=new StudentSj();
		String uuid = UUIDBuild.getUUID();
		studentSj.setUuid(uuid);
		studentSj.setStudentUuid(studentUuid);
		studentSj.setSjid(sjid);
		studentSj.setKsgluuid(ksgluuid);
		studentSj.setScore(0);
		studentSj.setStatus(1);

		//判断redis中是否有该studentSjuuid，如果没有，则添加
		String json = jedisClient.hget(studentUuid+"_"+ksgluuid, STUDENT_SJ);
		if(!StringUtils.isNotBlank(json)){
			studentSjMapper.insert(studentSj);
			jedisClient.hset(studentUuid+"_"+ksgluuid, STUDENT_SJ, uuid);
		}

		return uuid;
	}

	@Override
	public List<StudentSjCustom> findStudentSjList(
			StudentSjQueryVo studentSjQueryVo) throws Exception {
		return studentSjMapperCustom.findStudentSjList(studentSjQueryVo);
	}

	@Override
	public int findStudentSjListCount(StudentSjQueryVo studentSjQueryVo)
			throws Exception {
		return studentSjMapperCustom.findStudentSjListCount(studentSjQueryVo);
	}

	@Override
	public void autoPStudentSj(String uuid) throws Exception {
		StudentSj studentSj = studentSjMapper.selectByPrimaryKey(uuid);
		
		//根据学生考试uuid查询对应学生试卷题目答案
		StudentSjdaExample studentSjdaExample=new StudentSjdaExample();
		Criteria criteria = studentSjdaExample.createCriteria();
		criteria.andStudentsjidEqualTo(uuid);
		//条件修改为单选题与填空题与判断题，只为那些未评分的题目评分
		criteria.andTypeEqualTo(1);
		criteria.andStatusEqualTo(1);
		List<StudentSjda> studentSjdaDxtList = studentSjdaMapper.selectByExample(studentSjdaExample);
		
		for(StudentSjda studentSjda:studentSjdaDxtList){
			//获取学生试卷答案对应的试卷系题目uuid
			String sjxitmid = studentSjda.getSjxitmid();
			//通过学生试卷答案的sjxitmid与试卷答案的sjxitmid相等找到题目的答案
			SjdaExample sjdaExample=new SjdaExample();
			SjdaExample.Criteria sjdaCriteria = sjdaExample.createCriteria();
			sjdaCriteria.andSjxitmidEqualTo(sjxitmid);
			List<Sjda> sjdaList = sjdaMapper.selectByExample(sjdaExample);
			if(sjdaList==null||sjdaList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1201, null));
			Sjda sjda = sjdaList.get(0);
			//获取试卷题目答案
			String answer = sjda.getAnswer();
			String studentAnswer = studentSjda.getAnswer();
			//判断学生考试试卷答案是否与题目答案相等
			//如果学生答案为空或不相等，则修改状态为已评分
			if(studentAnswer==null||studentAnswer.trim().equals("")||!studentAnswer.trim().equals(answer)){
				studentSjda.setStatus(2);
				studentSjdaMapper.updateByPrimaryKey(studentSjda);
			}
			else{
				//获取该题目的分数，并设置分数，修改状态为已评分
				SjTm sjTm = sjTmMapper.selectByPrimaryKey(sjxitmid);
				studentSjda.setScore(sjTm.getScore());
				studentSjda.setStatus(2);
				//更新学生试卷答案
				studentSjdaMapper.updateByPrimaryKey(studentSjda);
				//对应试卷总分加上该题目分数

				studentSj.setScore(studentSj.getScore()+sjTm.getScore());
				//更新学生试卷
				studentSjMapper.updateByPrimaryKey(studentSj);
			}
		}

		//评填空题
		//根据学生考试uuid查询对应学生试卷题目答案
		StudentSjdaExample studentSjdaExample3=new StudentSjdaExample();
		Criteria criteria3 = studentSjdaExample3.createCriteria();
		criteria3.andStudentsjidEqualTo(uuid);
		criteria3.andTypeEqualTo(3);
		criteria3.andStatusEqualTo(1);
		List<StudentSjda> studentSjdaTktList = studentSjdaMapper.selectByExample(studentSjdaExample3);
		

		for(StudentSjda studentSjda:studentSjdaTktList){
			//获取学生试卷答案对应的试卷系题目uuid
			String sjxitmid = studentSjda.getSjxitmid();
			//通过学生试卷答案的sjxitmid与试卷答案的sjxitmid相等找到题目的答案
			SjdaExample sjdaExample=new SjdaExample();
			SjdaExample.Criteria sjdaCriteria = sjdaExample.createCriteria();
			sjdaCriteria.andSjxitmidEqualTo(sjxitmid);
			List<Sjda> sjdaList = sjdaMapper.selectByExample(sjdaExample);
			if(sjdaList==null||sjdaList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1201, null));
			Sjda sjda = sjdaList.get(0);
			//获取试卷题目答案
			String answer = sjda.getAnswer();
			String studentAnswer = studentSjda.getAnswer();
			
			
			//解析答案
			
			//该题目每个空可能的答案，Integer是题目空格号，List是该空可能的答案
			Map<Integer, List> answerMap = JsonUtils.jsonToMap(answer, Integer.class, List.class);
			
			//该学生填空题每个空的答案
			List<String> studentAnswerList = JsonUtils.jsonToList(studentAnswer, String.class);
			
			//每个空的分数
			SjTm sjTm = sjTmMapper.selectByPrimaryKey(sjxitmid);
			Integer tktA=sjTm.getScore()/studentAnswerList.size();
			
			//遍历学生该题从第一个空到最后一个空的答案
			for(int i=0;i<studentAnswerList.size();i++){
				//学生该空的答案
				String ans = studentAnswerList.get(i);
				//该空的所有答案
				List<String> list = answerMap.get(i+1);
				if(list.contains(ans)){
					//获取该题目的分数，并设置分数，修改状态为已评分
					studentSjda.setScore(studentSjda.getScore()+tktA);
				}
					
			}
			studentSjda.setStatus(2);
			//更新学生试卷答案
			studentSjdaMapper.updateByPrimaryKey(studentSjda);
			
			studentSj.setScore(studentSj.getScore()+studentSjda.getScore());
			//更新学生试卷
			studentSjMapper.updateByPrimaryKey(studentSj);
		}
		
		//评判断题
		//根据学生考试uuid查询对应学生试卷题目答案
		StudentSjdaExample studentSjdaExample4=new StudentSjdaExample();
		Criteria criteria4 = studentSjdaExample4.createCriteria();
		criteria4.andStudentsjidEqualTo(uuid);
		criteria4.andTypeEqualTo(5);
		criteria4.andStatusEqualTo(1);
		List<StudentSjda> studentSjdaPdtList = studentSjdaMapper.selectByExample(studentSjdaExample4);
		
		for(StudentSjda studentSjda:studentSjdaPdtList){
			//获取学生试卷答案对应的试卷系题目uuid
			String sjxitmid = studentSjda.getSjxitmid();
			//通过学生试卷答案的sjxitmid与试卷答案的sjxitmid相等找到题目的答案
			SjdaExample sjdaExample=new SjdaExample();
			SjdaExample.Criteria sjdaCriteria = sjdaExample.createCriteria();
			sjdaCriteria.andSjxitmidEqualTo(sjxitmid);
			List<Sjda> sjdaList = sjdaMapper.selectByExample(sjdaExample);
			if(sjdaList==null||sjdaList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1201, null));
			Sjda sjda = sjdaList.get(0);
			//获取试卷题目答案
			String answer = sjda.getAnswer();
			String studentAnswer = studentSjda.getAnswer();
			//判断学生考试试卷答案是否与题目答案相等
			//如果学生答案为空或不相等，则修改状态为已评分
			if(studentAnswer==null||studentAnswer.trim().equals("")||!studentAnswer.trim().equals(answer)){
				studentSjda.setStatus(2);
				studentSjdaMapper.updateByPrimaryKey(studentSjda);
			}
			else{
				//获取该题目的分数，并设置分数，修改状态为已评分
				SjTm sjTm = sjTmMapper.selectByPrimaryKey(sjxitmid);
				studentSjda.setScore(sjTm.getScore());
				studentSjda.setStatus(2);
				//更新学生试卷答案
				studentSjdaMapper.updateByPrimaryKey(studentSjda);
				//对应试卷总分加上该题目分数

				studentSj.setScore(studentSj.getScore()+sjTm.getScore());
				//更新学生试卷
				studentSjMapper.updateByPrimaryKey(studentSj);
			}
		}
		
		
		//判断该试卷的学生试卷答案是否都已经完成评分，若都完成，则修改学生试卷状态为完成评分
		StudentSjdaExample studentSjdaExample2=new StudentSjdaExample();
		StudentSjdaExample.Criteria criteria2 = studentSjdaExample2.createCriteria();
		criteria2.andStudentsjidEqualTo(uuid);
		criteria2.andStatusEqualTo(1);
		List<StudentSjda> studentSjdaList = studentSjdaMapper.selectByExample(studentSjdaExample2);
		if(studentSjdaList==null||studentSjdaList.size()<1){
			//修改状态
			studentSj.setStatus(2);
			//更新学生试卷
			studentSjMapper.updateByPrimaryKey(studentSj);
		}
	}

}
