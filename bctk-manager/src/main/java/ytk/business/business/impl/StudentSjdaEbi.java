package ytk.business.business.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.business.business.StudentSjdaEbo;
import ytk.business.dao.mapper.SjTmMapper;
import ytk.business.dao.mapper.StudentSjMapper;
import ytk.business.dao.mapper.StudentSjdaMapper;
import ytk.business.dao.mapper.StudentSjdaMapperCustom;
import ytk.business.pojo.po.SjTm;
import ytk.business.pojo.po.SjTmExample;
import ytk.business.pojo.po.SjTmExample.Criteria;
import ytk.business.pojo.po.StudentSj;
import ytk.business.pojo.po.StudentSjda;
import ytk.business.pojo.po.StudentSjdaExample;
import ytk.business.pojo.vo.StudentSjdaCustom;
import ytk.business.pojo.vo.StudentSjdaQueryVo;
import ytk.jedis.JedisClient;
import ytk.util.JsonUtils;
import ytk.util.UUIDBuild;

public class StudentSjdaEbi implements StudentSjdaEbo{  

	@Autowired
	private StudentSjdaMapper studentSjdaMapper;
	
	@Autowired
	private SjTmMapper sjTmMapper;
	@Autowired
	private StudentSjdaMapperCustom studentSjdaMapperCustom;
	@Autowired
	private StudentSjMapper studentSjMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${DXT_ANSWER}")
	private String DXT_ANSWER;
	@Value("${TKT_ANSWER}")
	private String TKT_ANSWER;
	
	@Override
	public void addStudentSjda(String sjid,String studentsjid,List<StudentSjdaCustom> studentSjdaList)
			throws Exception {
		//dxtList的index就是试卷题目的编号，value就是试卷题目的答案
		for(int i=1;i<studentSjdaList.size();i++){
			StudentSjdaCustom studentSjdaCustom = studentSjdaList.get(i);
			//设置uuid
			String uuid = UUIDBuild.getUUID();
			studentSjdaCustom.setUuid(uuid);
			//设置学生试卷答案的考试试卷id
			studentSjdaCustom.setStudentsjid(studentsjid);
			//设置试卷系题目id
			SjTmExample sjTmExample = new SjTmExample();
			Criteria criteria = sjTmExample.createCriteria();
			criteria.andSjidEqualTo(sjid);
			criteria.andSjtmidEqualTo(studentSjdaCustom.getSjtmid());
			criteria.andTypeEqualTo(studentSjdaCustom.getType());
			
			List<SjTm> sjTmList = sjTmMapper.selectByExample(sjTmExample);
			if(sjTmList==null||sjTmList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1040, null));
			
			studentSjdaCustom.setSjxitmid(sjTmList.get(0).getUuid());
			studentSjdaCustom.setScore(0);
			studentSjdaCustom.setStatus(1);
			
			studentSjdaMapper.insert(studentSjdaCustom);
		}
	}

	@Override
	public List<StudentSjdaCustom> findStudentSjdaList(
			StudentSjdaQueryVo studentSjdaQueryVo) throws Exception {
		return studentSjdaMapperCustom.findStudentSjdaList(studentSjdaQueryVo);
	}

	@Override
	public int findStudentSjdaListCount(StudentSjdaQueryVo studentSjdaQueryVo)  
			throws Exception {
		return studentSjdaMapperCustom.findStudentSjdaListCount(studentSjdaQueryVo);   
	}

	@Override
	public StudentSjda findStudentSjdaByUuid(String uuid) throws Exception {
		return studentSjdaMapper.selectByPrimaryKey(uuid);
	}

	@Override
	public void pScore(String studentSjdaUuid, Integer score, Integer mscore)
			throws Exception {
		if(score==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1202, null));
		if(score<0||score>mscore)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1203, null));
		//获取学生考试试卷题目答案
		StudentSjda studentSjda = studentSjdaMapper.selectByPrimaryKey(studentSjdaUuid);
		//获取未修改前的分数
		Integer scoreDb = studentSjda.getScore();
		//修改分数
		studentSjda.setScore(score);
		//修改学生试卷分数
		//获取该学生考试试卷
		StudentSj studentSj = studentSjMapper.selectByPrimaryKey(studentSjda.getStudentsjid());
		studentSj.setScore(studentSj.getScore()+score-scoreDb);
		//更新学生试卷
		studentSjMapper.updateByPrimaryKey(studentSj);
		//修改状态
		studentSjda.setStatus(2);
		//更新学生试卷答案信息
		studentSjdaMapper.updateByPrimaryKey(studentSjda);
		//判断该试卷的学生试卷答案是否都已经完成评分，若都完成，则修改学生试卷状态为完成评分
		StudentSjdaExample studentSjdaExample=new StudentSjdaExample();
		StudentSjdaExample.Criteria criteria = studentSjdaExample.createCriteria();
		criteria.andStudentsjidEqualTo(studentSjda.getStudentsjid());
		criteria.andStatusEqualTo(1);
		List<StudentSjda> studentSjdaList = studentSjdaMapper.selectByExample(studentSjdaExample);
		if(studentSjdaList==null||studentSjdaList.size()<1){
			//修改状态
			studentSj.setStatus(2);
			//更新学生试卷
			studentSjMapper.updateByPrimaryKey(studentSj);
		}
	}

	@Override
	public void addStudentSjdaDxt(Long sysuseruuid, String ksgluuid,List<String> dxtList,Integer dxtSize)
			throws Exception {

		Map< Integer, String> map=new LinkedHashMap<Integer, String>();
		//如果用户没写就提交，设置答案全为空
		
		//如果用户全写了，则中间为null的答案设置为“”
		
		//如果用户则写了一部分，则后面的全部设置为“”，在校验全部，如果中间为null，则设置为“”
		
		if(dxtList==null||dxtList.size()<1){
			for(int i=1;i<=dxtSize;i++){
				map.put(i, "");
			}
		}else if(dxtList.size()-1==dxtSize){
			System.out.println("相等"+dxtList.size());
			for(int i=1;i<=dxtSize;i++){
				String answer = dxtList.get(i);
				if(answer==null)
					answer="";
				map.put(i, answer);
			}
		}else if(dxtList.size()-1<dxtSize){
			System.out.println("小于"+dxtList.size());
			for(int i=0;i<=dxtSize-dxtList.size();i++){
				dxtList.add("");
			}
			for(int i=1;i<=dxtSize;i++){
				String answer = dxtList.get(i);
				if(answer==null)
					answer="";
				map.put(i, answer);
			}
		}
		jedisClient.hset(sysuseruuid+"_"+ksgluuid, DXT_ANSWER, JsonUtils.objectToJson(map)); 
	}

	@Override
	public Map<Integer, String> findStudentSjDaDxt(Long sysuseruuid,
			String ksgluuid) throws Exception {
		String json = jedisClient.hget(sysuseruuid+"_"+ksgluuid, DXT_ANSWER);
		if(StringUtils.isNoneBlank(json)){
			return JsonUtils.jsonToMap(json, Integer.class,String.class);
		}
		return null;
	}

	@Override
	public void addStudentSjdaTkt(Long sysuseruuid, String ksgluuid,
			List<List<String>> tktList) throws Exception {
		Map< Integer, List> map=new LinkedHashMap<Integer, List>();
		
		for(int i=0;i<tktList.size();i++){
			List<String> list = tktList.get(i);
			map.put(i+1, list);
		}	
		jedisClient.hset(sysuseruuid+"_"+ksgluuid, TKT_ANSWER, JsonUtils.objectToJson(map)); 
	}

	@Override
	public Map<Integer, List> findStudentSjDaTkt(Long sysuseruuid,
			String ksgluuid) {
		String json = jedisClient.hget(sysuseruuid+"_"+ksgluuid, TKT_ANSWER);
		System.out.println(json);
		if(StringUtils.isNoneBlank(json)){
			return JsonUtils.jsonToMap(json, Integer.class,List.class);
		}

		
		return null;
	}

}
