package ytk.business.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ytk.base.dao.mapper.KcMapper;
import ytk.base.dao.mapper.KcZyMapper;
import ytk.base.pojo.po.Kc;
import ytk.base.pojo.po.KcExample;
import ytk.base.pojo.po.KcExample.Criteria;
import ytk.base.pojo.vo.PageQuery;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.business.business.SjTmEbo;
import ytk.business.dao.mapper.DxtMapper;
import ytk.business.dao.mapper.DxxztMapper;
import ytk.business.dao.mapper.PdtMapper;
import ytk.business.dao.mapper.KsglMapper;
import ytk.business.dao.mapper.KsglMapperCustom;
import ytk.business.dao.mapper.SjMapper;
import ytk.business.dao.mapper.SjTmMapper;
import ytk.business.dao.mapper.SjTmMapperCustom;
import ytk.business.dao.mapper.SjdaMapper;
import ytk.business.dao.mapper.SjmbMapper;
import ytk.business.dao.mapper.TktMapper;
import ytk.business.pojo.po.Dxt;
import ytk.business.pojo.po.Dxxzt;
import ytk.business.pojo.po.Pdt;
import ytk.business.pojo.po.Ksgl;
import ytk.business.pojo.po.KsglExample;
import ytk.business.pojo.po.Sj;
import ytk.business.pojo.po.SjTm;
import ytk.business.pojo.po.SjTmExample;
import ytk.business.pojo.po.Sjda;
import ytk.business.pojo.po.SjdaExample;
import ytk.business.pojo.po.Sjmb;
import ytk.business.pojo.po.SjmbExample;
import ytk.business.pojo.po.Tkt;
import ytk.business.pojo.vo.KsglCustom;
import ytk.business.pojo.vo.KsglQueryVo;
import ytk.business.pojo.vo.SjCustom;
import ytk.business.pojo.vo.SjTmCustom;
import ytk.business.pojo.vo.SjTmQueryVo;
import ytk.business.pojo.vo.SjmbCustom;
import ytk.jedis.JedisClient;
import ytk.util.JsonUtils;
import ytk.util.RandomUtils;
import ytk.util.UUIDBuild;

public class SjTmEbi implements SjTmEbo{

	@Autowired
	private SjTmMapperCustom sjTmMapperCustom;
	@Autowired
	private SjTmMapper sjTmMapper;
	@Autowired
	private KcMapper kcMapper;
	@Autowired
	private KcZyMapper kcZyMapper;
	@Autowired
	private DxtMapper dxtMapper;
	@Autowired
	private DxxztMapper dxxztMapper;
	@Autowired
	private TktMapper tktMapper;
	@Autowired
	private PdtMapper pdtMapper;
	@Autowired
	private SjdaMapper sjdaMapper;
	@Autowired
	private SjMapper sjMapper;
	@Autowired
	private SjmbMapper sjmbMapper;
	@Autowired
	private KsglMapper ksglMapper;
	@Autowired
	private KsglMapperCustom ksglMapperCustom;
	@Autowired
	private JedisClient jedisClient; 
	
	@Value("${DXT_ORDER}")
	private String DXT_ORDER;
	@Value("${TKT_ORDER}")
	private String TKT_ORDER;
	
	@Override
	public List<SjTmCustom> findSjTmList(SjTmQueryVo sjTmQueryVo)
			throws Exception {
		
		List<SjTmCustom> sjTmList = sjTmMapperCustom.findSjTmList(sjTmQueryVo);
		
		PageQuery pageQuery = sjTmQueryVo.getPageQuery();
		
		if(pageQuery!=null){
			int lastIndex=pageQuery.getPageQuery_start()+pageQuery.getPageQuery_pageSize();
			if(pageQuery.getPageQuery_pageSize()>sjTmList.size()-pageQuery.getPageQuery_start())
				lastIndex=sjTmList.size();
			List<SjTmCustom> subList = sjTmList.subList(pageQuery.getPageQuery_start(), lastIndex);
			return subList; 
		}
		return sjTmList; 
	}

	@Override
	public int findSjTmListCount(SjTmQueryVo sjTmQueryVo)
			throws Exception {
		System.out.println(sjTmMapperCustom.findSjTmListCount(sjTmQueryVo));
		return sjTmMapperCustom.findSjTmListCount(sjTmQueryVo);
	}

	@Override
	public void addSjTm(SjCustom sjCustom,SjTm sjTm) throws Exception {
		
		String sjid = sjCustom.getUuid();
		Sj sj = sjMapper.selectByPrimaryKey(sjid);
		
		//说明是在修改试卷时添加题目
		if(sj!=null){
			//查询该试卷所对应的所有考试中最早开考的时间
			KsglQueryVo ksglQueryVo=new KsglQueryVo();
			KsglCustom ksglCustom=new KsglCustom();
			ksglCustom.setSjuuid(sjid);
			ksglQueryVo.setKsglCustom(ksglCustom);
			
			Long starttime = ksglMapperCustom.findKsglMinStarttime(ksglQueryVo);
			//如果当前时间大于最早开考时间，则不允许修改
			if(starttime!=null&&System.currentTimeMillis()>starttime)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1044, null));
		}
		//TODO 添加的题目信息必须存在
		
		//获取要添加的试卷题目信息
		Integer type = sjTm.getType();
		
		//不允许添加重复的题目
		SjTm sjTmTemp = findSjTmBySjIdAndTmuuid(sjCustom.getUuid(), sjTm.getTuuid());
		if(sjTmTemp!=null){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1004, null));
		}
		
		//设置试卷题目编号
		//查询该试卷的所有题目信息
		SjTmExample sjTmExample2=new SjTmExample();
		sjTmExample2.setOrderByClause("sjtmid ASC");
		SjTmExample.Criteria sjTmCriteria2 = sjTmExample2.createCriteria();
		sjTmCriteria2.andSjidEqualTo(sjid);
		sjTmCriteria2.andTypeEqualTo(type);
		List<SjTm> sjTmList2 = sjTmMapper.selectByExample(sjTmExample2);
		
		boolean isEnter=false;
		//如果为空，则设置为1
		if(sjTmList2==null||sjTmList2.size()<1){
			sjTm.setSjtmid(1);
		}else{
			//否则遍历该试卷所有题目信息，若中间有间断，则设置间断为试卷题目编号
			for(int i=1;i<=sjTmList2.size();i++){
				if(i!=sjTmList2.get(i-1).getSjtmid()){
					sjTm.setSjtmid(i);
					isEnter=true;
					break;
				}
			}
			//若没有则设置试卷题目编号为试卷题目长度+1
			if(!isEnter){
				sjTm.setSjtmid(sjTmList2.size()+1);
			}
		}
		
		//生成uuid
		sjTm.setUuid(UUIDBuild.getUUID());
		//为试卷题目添加题目类型
		sjTm.setType(type);
		
		//判断是组卷时添加试卷题目还是修改试卷时添加试卷题目
		if(sj==null){
			//组卷
			//为试卷题目添加临时状态
			sjTm.setState(2);
		}else{
			//修改试卷
			//为试卷题目添加正常状态
			sjTm.setState(1);
			
			//生成试卷答案
			Sjda sjda=new Sjda();
			sjda.setUuid(UUIDBuild.getUUID());
			sjda.setSjid(sjid);
			sjda.setSjxitmid(sjTm.getUuid());
			sjda.setSjtmid(sjTm.getSjtmid());
			sjda.setType(type);
			//单选题
			if(type==1){
				Dxt dxt = dxtMapper.selectByPrimaryKey(sjTm.getTuuid());
				sjda.setAnswer(dxt.getAnswer());
			}
			//填空题
			else if(type==3){
				Tkt tkt = tktMapper.selectByPrimaryKey(sjTm.getTuuid());
				sjda.setAnswer(tkt.getAnswer());
			}
			//判断题
			else if(type==5){
				Pdt pdt = pdtMapper.selectByPrimaryKey(sjTm.getTuuid());
				sjda.setAnswer(pdt.getAnswer());
			}
			sjdaMapper.insert(sjda);
			
			if(sj!=null){
				int score=sj.getScore()+sjTm.getScore();
				System.out.println(sj.getScore());
				System.out.println(sjTm.getScore());
				//试卷总分加上添加题目分数
				sj.setScore(score);
				//更新试卷信息
				sjMapper.updateByPrimaryKey(sj);
				//获取该试卷模板信息
				Sjmb sjmb = sjmbMapper.selectByPrimaryKey(sj.getSjmbid());
				//查询是否有添加题目后的模板信息
				SjmbExample sjmbExample=new SjmbExample();
				SjmbExample.Criteria sjmbCriteria = sjmbExample.createCriteria();
				sjmbCriteria.andScoreEqualTo(score);
				
				//判断添加的是什么类型题目
				if(type==1){
					sjmb.setDxtcount(sjmb.getDxtcount()+1);
					sjmbCriteria.andDxtcountEqualTo(sjmb.getDxtcount());
					sjmb.setDxtscore(sjmb.getDxtscore()+sjTm.getScore());
					sjmbCriteria.andDxtscoreEqualTo(sjmb.getDxtscore());
					
					sjmbCriteria.andTktcountEqualTo(sjmb.getTktcount());
					sjmbCriteria.andTktscoreEqualTo(sjmb.getTktscore());
					sjmbCriteria.andPdtcountEqualTo(sjmb.getPdtcount());
					sjmbCriteria.andPdtscoreEqualTo(sjmb.getPdtscore());
				}else if(type==3){
					sjmb.setTktcount(sjmb.getTktcount()+1);
					sjmbCriteria.andTktcountEqualTo(sjmb.getTktcount());
					sjmb.setTktscore(sjmb.getTktscore()+sjTm.getScore());
					sjmbCriteria.andTktscoreEqualTo(sjmb.getTktscore());
					
					sjmbCriteria.andDxtcountEqualTo(sjmb.getDxtcount());
					sjmbCriteria.andDxtscoreEqualTo(sjmb.getDxtscore());
					sjmbCriteria.andPdtcountEqualTo(sjmb.getPdtcount());
					sjmbCriteria.andPdtscoreEqualTo(sjmb.getPdtscore());
				}else if(type==5){
					sjmb.setPdtcount(sjmb.getPdtcount()+1);
					sjmbCriteria.andPdtcountEqualTo(sjmb.getPdtcount());
					sjmb.setPdtscore(sjmb.getPdtscore()+sjTm.getScore());
					sjmbCriteria.andPdtscoreEqualTo(sjmb.getPdtscore());
					
					sjmbCriteria.andDxtcountEqualTo(sjmb.getDxtcount());
					sjmbCriteria.andDxtscoreEqualTo(sjmb.getDxtscore());
					sjmbCriteria.andTktcountEqualTo(sjmb.getTktcount());
					sjmbCriteria.andTktscoreEqualTo(sjmb.getTktscore());
				}
				
				List<Sjmb> sjmbList = sjmbMapper.selectByExample(sjmbExample);
				if(sjmbList==null||sjmbList.size()<1){
					//添加试卷模板uuid
					//添加试卷模板状态
					Sjmb sjmb2=new Sjmb();
					sjmb2.setUuid(UUIDBuild.getUUID());
					sjmb2.setDxtcount(sjmb.getDxtcount());
					sjmb2.setDxtscore(sjmb.getDxtscore());
					sjmb2.setPdtcount(sjmb.getPdtcount());
					sjmb2.setPdtscore(sjmb.getPdtscore());
					sjmb2.setTktcount(sjmb.getTktcount());
					sjmb2.setTktscore(sjmb.getTktscore());
					sjmb2.setScore(score);
					sjmb2.setStatus(1);
					//添加试卷模板
					sjmbMapper.insert(sjmb2);
					sj.setSjmbid(sjmb2.getUuid());
				}
				else
					sj.setSjmbid(sjmbList.get(0).getUuid());
				sjMapper.updateByPrimaryKey(sj);
			
			}
		}

		//为试卷题目添加试卷id
		sjTm.setSjid(sjid);
		
		//添加试卷题目信息
		sjTmMapper.insert(sjTm);
	}

	@Override
	public SjTm findSjTmBySjIdAndTmuuid(String sjId,String tmuuid) throws Exception { 
		SjTmCustom sjTmCustom=new SjTmCustom();
		sjTmCustom.setSjid(sjId);
		sjTmCustom.setTuuid(tmuuid);
		SjTmQueryVo sjTmQueryVo=new SjTmQueryVo();
		sjTmQueryVo.setSjTmCustom(sjTmCustom);
		List<SjTmCustom> sjTmList = sjTmMapperCustom.findSjTmList(sjTmQueryVo);
		if(sjTmList!=null&&sjTmList.size()>0)
			return sjTmList.get(0);
		return null;
	}

	@Override
	public List<SjTmCustom> findAddSjTmList(SjTmQueryVo sjTmQueryVo)
			throws Exception {
		//非空检验
		List<SjTmCustom> addSjTmList = sjTmMapperCustom.findAddSjTmList(sjTmQueryVo);
		
		PageQuery pageQuery = sjTmQueryVo.getPageQuery();
		if(pageQuery!=null){
			int lastIndex=pageQuery.getPageQuery_start()+pageQuery.getPageQuery_pageSize();
			if(pageQuery.getPageQuery_pageSize()>addSjTmList.size()-pageQuery.getPageQuery_start())
				lastIndex=addSjTmList.size();
			List<SjTmCustom> subList = addSjTmList.subList(pageQuery.getPageQuery_start(), lastIndex);
			return subList;
		}
		return addSjTmList;
	}

	@Override
	public int findAddSjTmListCount(SjTmQueryVo sjTmQueryVo) throws Exception {
		//非空检验
		sjTmQueryVo=sjTmQueryVo==null?new SjTmQueryVo():sjTmQueryVo;
		SjTmCustom sjTmCustom = sjTmQueryVo.getSjTmCustom();
		if(sjTmCustom==null)
			sjTmCustom=new SjTmCustom();
		sjTmQueryVo.setSjTmCustom(sjTmCustom);
		return sjTmMapperCustom.findAddSjTmListCount(sjTmQueryVo); 
	}

	@Override
	public void deleteSjTm(String uuid) throws Exception {
		
		//删除的试卷题目信息必须存在
		SjTm sjTm = sjTmMapper.selectByPrimaryKey(uuid);
		if(sjTm==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1005, null));
		
		//判断该试卷系题目是否有sjid，如果有，说明是修改试卷时删除试卷系题目(并且不是临时的)，如果没有，说明是添加试卷时删除试卷系题目
		String sjid=sjTm.getSjid();
		if(sjid!=null&&!sjid.trim().equals("")&&sjTm.getState()!=2){
			
			//如果正在进行和已考的考试中包含该试卷，则不允许删除试卷题目
			KsglExample ksglExample=new KsglExample();
			KsglExample.Criteria ksglCriteria = ksglExample.createCriteria();
			List<Integer> status=new ArrayList<Integer>();
			status.add(2);
			status.add(3);
			ksglCriteria.andStatusIn(status);
			ksglCriteria.andSjuuidEqualTo(sjid);
			List<Ksgl> ksglList = ksglMapper.selectByExample(ksglExample);
			if(ksglList!=null&&ksglList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1005, null));
			
			Sj sj = sjMapper.selectByPrimaryKey(sjid);
			if(sj!=null){
				int score=sj.getScore()-sjTm.getScore();
				//试卷总分减去删除题目分数
				sj.setScore(score);
				//更新试卷信息
				sjMapper.updateByPrimaryKey(sj);
				//获取该试卷模板信息
				Sjmb sjmb = sjmbMapper.selectByPrimaryKey(sj.getSjmbid());
				//查询是否有删除题目后的模板信息
				SjmbExample sjmbExample=new SjmbExample();
				SjmbExample.Criteria sjmbCriteria = sjmbExample.createCriteria();
				sjmbCriteria.andScoreEqualTo(score);
				
				//判断删除的是什么类型题目
				Integer type = sjTm.getType();
				if(type==1){
					sjmb.setDxtcount(sjmb.getDxtcount()-1);
					sjmbCriteria.andDxtcountEqualTo(sjmb.getDxtcount());
					sjmb.setDxtscore(sjmb.getDxtscore()-sjTm.getScore());
					sjmbCriteria.andDxtscoreEqualTo(sjmb.getDxtscore());
					
					sjmbCriteria.andTktcountEqualTo(sjmb.getTktcount());
					sjmbCriteria.andTktscoreEqualTo(sjmb.getTktscore());
					sjmbCriteria.andPdtcountEqualTo(sjmb.getPdtcount());
					sjmbCriteria.andPdtscoreEqualTo(sjmb.getPdtscore());
				}else if(type==3){
					sjmb.setTktcount(sjmb.getTktcount()-1);
					sjmbCriteria.andTktcountEqualTo(sjmb.getTktcount());
					sjmb.setTktscore(sjmb.getTktscore()-sjTm.getScore());
					sjmbCriteria.andTktscoreEqualTo(sjmb.getTktscore());
					
					sjmbCriteria.andDxtcountEqualTo(sjmb.getDxtcount());
					sjmbCriteria.andDxtscoreEqualTo(sjmb.getDxtscore());
					sjmbCriteria.andPdtcountEqualTo(sjmb.getPdtcount());
					sjmbCriteria.andPdtscoreEqualTo(sjmb.getPdtscore());
				}else if(type==5){
					sjmb.setPdtcount(sjmb.getPdtcount()-1);
					sjmbCriteria.andPdtcountEqualTo(sjmb.getPdtcount());
					sjmb.setPdtscore(sjmb.getPdtscore()-sjTm.getScore());
					sjmbCriteria.andPdtscoreEqualTo(sjmb.getPdtscore());
					
					sjmbCriteria.andDxtcountEqualTo(sjmb.getDxtcount());
					sjmbCriteria.andDxtscoreEqualTo(sjmb.getDxtscore());
					sjmbCriteria.andTktcountEqualTo(sjmb.getTktcount());
					sjmbCriteria.andTktscoreEqualTo(sjmb.getTktscore());
				}
				
				List<Sjmb> sjmbList = sjmbMapper.selectByExample(sjmbExample);
				if(sjmbList==null||sjmbList.size()<1){
					//添加试卷模板uuid
					//添加试卷模板状态
					Sjmb sjmb2=new Sjmb();
					sjmb2.setUuid(UUIDBuild.getUUID());
					sjmb2.setDxtcount(sjmb.getDxtcount());
					sjmb2.setDxtscore(sjmb.getDxtscore());
					sjmb2.setPdtcount(sjmb.getPdtcount());
					sjmb2.setPdtscore(sjmb.getPdtscore());
					sjmb2.setTktcount(sjmb.getTktcount());
					sjmb2.setTktscore(sjmb.getTktscore());
					sjmb2.setScore(score);
					sjmb2.setStatus(1);
					//添加试卷模板
					sjmbMapper.insert(sjmb2);
					sj.setSjmbid(sjmb2.getUuid());
				}
				else
					sj.setSjmbid(sjmbList.get(0).getUuid());
				sjMapper.updateByPrimaryKey(sj);
				
				//删除该试卷该题目的试卷答案
				SjdaExample sjdaExample=new SjdaExample();
				SjdaExample.Criteria sjdaCriteria = sjdaExample.createCriteria();
				sjdaCriteria.andSjidEqualTo(sjid);
				sjdaCriteria.andSjxitmidEqualTo(uuid);
				sjdaMapper.deleteByExample(sjdaExample);
				
				sjTmMapper.deleteByPrimaryKey(uuid);
				
				//题目重新排序
				SjTmQueryVo sjTmQueryVo=new SjTmQueryVo();
				SjTmCustom sjTmCustom=new SjTmCustom();
				sjTmQueryVo.setSjTmCustom(sjTmCustom);
				sjTmCustom.setSjid(sjid);

				if(type==1){
					//查询该试卷所有单选题信息
					//修改其编号依次从1开始
					sjTmCustom.setType(1);
					List<SjTmCustom> sjTmList = sjTmMapperCustom.findSjTmList(sjTmQueryVo);
					if(sjTmList!=null&&sjTmList.size()>0){
						for(int i=0;i<sjTmList.size();i++){
							SjTm tm=sjTmList.get(i);
							Integer sjtmid=tm.getSjtmid();
							//如果题目编号不连续
							if(sjtmid!=i+1){
								//修改该题目编号，并且修改该题目对应答案的编号
								SjdaExample sjdaExample2=new SjdaExample();
								SjdaExample.Criteria sjdaDxtCriteria = sjdaExample2.createCriteria();
								sjdaDxtCriteria.andSjidEqualTo(sjid);
								sjdaDxtCriteria.andTypeEqualTo(1);
								sjdaDxtCriteria.andSjtmidEqualTo(sjtmid);
								List<Sjda> sjdaList = sjdaMapper.selectByExample(sjdaExample2);
								if(sjdaList!=null&&sjdaList.size()>0){
									Sjda sjda = sjdaList.get(0);
									sjda.setSjtmid(i+1);
									//更新试卷答案题目编号
									sjdaMapper.updateByPrimaryKey(sjda);
								}
								//更新试卷系题目编号
								tm.setSjtmid(i+1);
								tm.setType(1);
								sjTmMapper.updateByPrimaryKey(tm);
							}
						}
					}
				}else if(type==3){
					//查询该试卷所有填空题信息
					//修改其编号依次从1开始
					sjTmCustom.setType(3);
					List<SjTmCustom> sjTmList = sjTmMapperCustom.findSjTmList(sjTmQueryVo);
					if(sjTmList!=null&&sjTmList.size()>0){
						for(int i=0;i<sjTmList.size();i++){
							SjTm tm=sjTmList.get(i);
							Integer sjtmid=tm.getSjtmid();
							//如果题目编号不连续
							if(sjtmid!=i+1){
								//修改该题目编号，并且修改该题目对应答案的编号
								SjdaExample sjdaExample2=new SjdaExample();
								SjdaExample.Criteria sjdaDxtCriteria = sjdaExample2.createCriteria();
								sjdaDxtCriteria.andSjidEqualTo(sjid);
								sjdaDxtCriteria.andTypeEqualTo(3);
								sjdaDxtCriteria.andSjtmidEqualTo(sjtmid);
								List<Sjda> sjdaList = sjdaMapper.selectByExample(sjdaExample2);
								if(sjdaList!=null&&sjdaList.size()>0){
									Sjda sjda = sjdaList.get(0);
									sjda.setSjtmid(sjtmid);
									//更新试卷答案题目编号
									sjdaMapper.updateByPrimaryKey(sjda);
								}
								//更新试卷系题目编号
								tm.setSjtmid(i+1);
								tm.setType(3);
								sjTmMapper.updateByPrimaryKey(tm);
							}
						}
					}
				}else if(type==5){
					//查询该试卷所有判断题信息
					//修改其编号依次从1开始
					sjTmCustom.setType(5);
					List<SjTmCustom> sjTmList = sjTmMapperCustom.findSjTmList(sjTmQueryVo);
					if(sjTmList!=null&&sjTmList.size()>0){
						for(int i=0;i<sjTmList.size();i++){
							SjTm tm=sjTmList.get(i);
							Integer sjtmid=tm.getSjtmid();
							//如果题目编号不连续
							if(sjtmid!=i+1){
								//修改该题目编号，并且修改该题目对应答案的编号
								SjdaExample sjdaExample2=new SjdaExample();
								SjdaExample.Criteria sjdaDxtCriteria = sjdaExample2.createCriteria();
								sjdaDxtCriteria.andSjidEqualTo(sjid);
								sjdaDxtCriteria.andTypeEqualTo(5);
								sjdaDxtCriteria.andSjtmidEqualTo(sjtmid);
								List<Sjda> sjdaList = sjdaMapper.selectByExample(sjdaExample2);
								if(sjdaList!=null&&sjdaList.size()>0){
									Sjda sjda = sjdaList.get(0);
									sjda.setSjtmid(sjtmid);
									//更新试卷答案题目编号
									sjdaMapper.updateByPrimaryKey(sjda);
								}
								//更新试卷系题目编号
								tm.setSjtmid(i+1);
								tm.setType(5);
								sjTmMapper.updateByPrimaryKey(tm);
							}
						}
					}
				}
			}
			
		}
		else{
			//删除试卷题目信息
			sjTmMapper.deleteByPrimaryKey(uuid);
		}
	}

	@Override
	public void sjTmListPre(SjCustom sjCustom,SjmbCustom sjmbCustom) throws Exception {
		//课程名称不允许为空
		String kcname = sjCustom.getKcname();
		if(kcname==null||kcname.trim().equals(""))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1007, null));
		
		//试卷所属课程必须已存在,根据课程名称查询课程信息
		KcExample kcExample=new KcExample();
		Criteria criteria = kcExample.createCriteria();
		criteria.andNameEqualTo(kcname.trim());
		List<Kc> kcList = kcMapper.selectByExample(kcExample);
		if(kcList==null||kcList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1008, null));

		//单选题数量不允许为空
		Integer dxtcount = sjmbCustom.getDxtcount();
		if(dxtcount==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1032, null));
		
		//填空题数量不允许为空
		Integer tktcount = sjmbCustom.getTktcount();
		if(tktcount==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1034, null));
		
		//判断题题数量不允许为空
		Integer pdtcount = sjmbCustom.getPdtcount();
		if(pdtcount==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1035, null));
		
		//单选题分值不允许为空
		Integer dxtscore = sjmbCustom.getDxtscore();
		if(dxtscore==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1036, null));
		
		//填空题分值不允许为空
		Integer tktscore = sjmbCustom.getTktscore();
		if(tktscore==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1038, null));
		
		//判断题分值不允许为空
		Integer pdtscore = sjmbCustom.getPdtscore();
		if(pdtscore==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1039, null));
	}

	@Override
	public SjTm findSjTmByUuid(String uuid) throws Exception {
		return sjTmMapper.selectByPrimaryKey(uuid);
	}

	@Override
	public void updateSjTmScore(String uuid, SjTmQueryVo sjTmQueryVo)
			throws Exception {
		//试卷题目信息存在
		SjTm sjTm = sjTmMapper.selectByPrimaryKey(uuid);
		if(sjTm==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1018, null));
		
		//非空判断
		Integer score = sjTmQueryVo.getSjTmCustom().getScore();
		if(score==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1019, null));
		if(score==0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1057, null));
		
		Integer scoreDb=sjTm.getScore();
		String sjid=sjTm.getSjid();
		
		//修改试卷时，修改试卷题目分数
		if(sjid!=null&&!sjid.trim().equals("")&&sjTm.getState()!=2){
			Sj sj = sjMapper.selectByPrimaryKey(sjid);
			if(sj!=null){
				int sjScore=sj.getScore()-scoreDb+score;
				//试卷总分减去原先题目分数，再加上修改的题目分数
				sj.setScore(sjScore);
				//更新试卷信息
				sjMapper.updateByPrimaryKey(sj);
				//获取该试卷模板信息
				Sjmb sjmb = sjmbMapper.selectByPrimaryKey(sj.getSjmbid());
				//查询是否有添加题目后的模板信息
				SjmbExample sjmbExample=new SjmbExample();
				SjmbExample.Criteria sjmbCriteria = sjmbExample.createCriteria();
				sjmbCriteria.andScoreEqualTo(sjScore);
				
				//判断修改的是什么类型题目
				Integer type = sjTm.getType();
				if(type==1){
					sjmbCriteria.andDxtcountEqualTo(sjmb.getDxtcount());
					sjmb.setDxtscore(sjmb.getDxtscore()-scoreDb+score);
					sjmbCriteria.andDxtscoreEqualTo(sjmb.getDxtscore());
					
					sjmbCriteria.andTktcountEqualTo(sjmb.getTktcount());
					sjmbCriteria.andTktscoreEqualTo(sjmb.getTktscore());
					sjmbCriteria.andPdtcountEqualTo(sjmb.getPdtcount());
					sjmbCriteria.andPdtscoreEqualTo(sjmb.getPdtscore());
				}else if(type==3){
					sjmbCriteria.andTktcountEqualTo(sjmb.getTktcount());
					sjmb.setTktscore(sjmb.getTktscore()-scoreDb+score);
					sjmbCriteria.andTktscoreEqualTo(sjmb.getTktscore());
					
					sjmbCriteria.andDxtcountEqualTo(sjmb.getDxtcount());
					sjmbCriteria.andDxtscoreEqualTo(sjmb.getDxtscore());
					sjmbCriteria.andPdtcountEqualTo(sjmb.getPdtcount());
					sjmbCriteria.andPdtscoreEqualTo(sjmb.getPdtscore());
				}else if(type==5){
					sjmbCriteria.andPdtcountEqualTo(sjmb.getPdtcount());
					sjmb.setPdtscore(sjmb.getPdtscore()-scoreDb+score);
					sjmbCriteria.andPdtscoreEqualTo(sjmb.getPdtscore());
					
					sjmbCriteria.andDxtcountEqualTo(sjmb.getDxtcount());
					sjmbCriteria.andDxtscoreEqualTo(sjmb.getDxtscore());
					sjmbCriteria.andTktcountEqualTo(sjmb.getTktcount());
					sjmbCriteria.andTktscoreEqualTo(sjmb.getTktscore());
				}
				
				List<Sjmb> sjmbList = sjmbMapper.selectByExample(sjmbExample);
				if(sjmbList==null||sjmbList.size()<1){
					//添加试卷模板uuid
					//添加试卷模板状态
					Sjmb sjmb2=new Sjmb();
					sjmb2.setUuid(UUIDBuild.getUUID());
					sjmb2.setDxtcount(sjmb.getDxtcount());
					sjmb2.setDxtscore(sjmb.getDxtscore());
					sjmb2.setPdtcount(sjmb.getPdtcount());
					sjmb2.setPdtscore(sjmb.getPdtscore());
					sjmb2.setTktcount(sjmb.getTktcount());
					sjmb2.setTktscore(sjmb.getTktscore());
					sjmb2.setScore(sjScore);
					sjmb2.setStatus(1);
					//添加试卷模板
					sjmbMapper.insert(sjmb2);
					sj.setSjmbid(sjmb2.getUuid());
				}
				else
					sj.setSjmbid(sjmbList.get(0).getUuid());
				sjMapper.updateByPrimaryKey(sj);
			}
		}
		sjTm.setScore(score);
		sjTmMapper.updateByPrimaryKey(sjTm);
	}
	
	@Override
	public void updateSjTmSjtmid(String uuid, SjTmQueryVo sjTmQueryVo)
			throws Exception {
		//试卷题目必须存在
		SjTm sjTmDb = sjTmMapper.selectByPrimaryKey(uuid);
		if(sjTmDb==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1018, null));
		
		//非空判断
		Integer sjtmid = sjTmQueryVo.getSjTmCustom().getSjtmid();
		if(sjtmid==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1050, null));
		
		Integer sjtmidDb=sjTmDb.getSjtmid();
		String sjid=sjTmDb.getSjid();
		
		//修改试卷时，修改试卷题目编号
		if(sjid!=null&&!sjid.trim().equals("")&&sjTmDb.getState()!=2){
			Sj sj = sjMapper.selectByPrimaryKey(sjid);
			if(sj!=null){
				//如果修改了题目编号
				if(sjtmid!=sjtmidDb){
					//判断修改题目编号是否超出试卷该类型题目数量
					SjTmExample sjTmExample=new SjTmExample();
					SjTmExample.Criteria sjTmCriteria = sjTmExample.createCriteria();
					sjTmCriteria.andSjidEqualTo(sjid);
					sjTmCriteria.andTypeEqualTo(sjTmDb.getType());
					int count = sjTmMapper.countByExample(sjTmExample);
					if(sjtmid>count||sjtmid==0)
						ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1056, null));
					//交换两道题目编号，并且修改两道题目对应的题目答案编号
					
					//修改交换编号的题目的编号

					sjTmCriteria.andSjtmidEqualTo(sjtmid);
					List<SjTm> sjTmList = sjTmMapper.selectByExample(sjTmExample);
					if(sjTmList!=null&&sjTmList.size()>0){
						SjTm sjTm = sjTmList.get(0);
						sjTm.setSjtmid(sjtmidDb);
						sjTmMapper.updateByPrimaryKey(sjTm);
					}
					
					
					//修改原先的题目的编号
					sjTmDb.setSjtmid(sjtmid);
					sjTmMapper.updateByPrimaryKey(sjTmDb);
					
					
					//修改原先的题目答案的编号
					SjdaExample sjdaExample=new SjdaExample();
					SjdaExample.Criteria sjdaCriteria = sjdaExample.createCriteria();
					sjdaCriteria.andSjidEqualTo(sjid);
					sjdaCriteria.andTypeEqualTo(sjTmDb.getType());
					sjdaCriteria.andSjtmidEqualTo(sjtmidDb);
					List<Sjda> sjdaList = sjdaMapper.selectByExample(sjdaExample);
					
					Sjda sjdaDb=null;
					
					if(sjdaList!=null&&sjdaList.size()>0){
						sjdaDb = sjdaList.get(0);
						sjdaDb.setSjtmid(sjtmid);
					}

					//修改交换编号的题目答案的编号
					SjdaExample sjdaExample2=new SjdaExample();
					SjdaExample.Criteria sjdaCriteria2 = sjdaExample2.createCriteria();
					sjdaCriteria2.andSjidEqualTo(sjid);
					sjdaCriteria2.andTypeEqualTo(sjTmDb.getType());
					sjdaCriteria2.andSjtmidEqualTo(sjtmid);
					List<Sjda> sjdaList2 = sjdaMapper.selectByExample(sjdaExample2);
					
					Sjda sjda=null;
					
					if(sjdaList2!=null&&sjdaList2.size()>0){
						sjda = sjdaList2.get(0);
						sjda.setSjtmid(sjtmidDb);
					}
					
					if(sjdaDb!=null)
						sjdaMapper.updateByPrimaryKey(sjdaDb);
					
					if(sjda!=null)
						sjdaMapper.updateByPrimaryKey(sjda);
				}
			}
		}else{
			//修改试卷题目编号
			sjTmDb.setSjtmid(sjtmid);
			//更新试卷题目信息
			sjTmMapper.updateByPrimaryKey(sjTmDb);
		}
	}

	@Override
	public List<Dxt> findDxtBySjUuid(SjTmQueryVo sjTmQueryVo) throws Exception {
		//查询该试卷的单选题试卷题目信息，取出题uuid添加到list，再查询tuuid在list中的单选题信息
		sjTmQueryVo.getSjTmCustom().setType(1);
		sjTmQueryVo.getSjTmCustom().setState(1);
		List<SjTmCustom> sjTmList = findSjTmList(sjTmQueryVo);
		
		//存在相应试卷题目再去查询具体类型题目信息
		List<Dxt> dxtList=new ArrayList<Dxt>();;
		if(sjTmList!=null&&sjTmList.size()>0){
			for(SjTmCustom sjTmCustom:sjTmList){
				Dxt dxt = dxtMapper.selectByPrimaryKey(sjTmCustom.getTuuid());
				dxtList.add(dxt);
			}
		}
		return dxtList;
	}

	@Override
	public List<Dxxzt> findDxxztBySjUuid(SjTmQueryVo sjTmQueryVo)
			throws Exception {
		//查询该试卷的多项选择题试卷题目信息，取出题uuid添加到list，再查询tuuid在list中的多项选择题信息
		sjTmQueryVo.getSjTmCustom().setType(2);
		sjTmQueryVo.getSjTmCustom().setState(1);
		List<SjTmCustom> sjTmList = findSjTmList(sjTmQueryVo);
		
		//存在相应试卷题目再去查询具体类型题目信息
		List<Dxxzt> dxxztList=new ArrayList<Dxxzt>();
		if(sjTmList!=null&&sjTmList.size()>0){
			for(SjTmCustom sjTmCustom:sjTmList){
				Dxxzt dxxzt = dxxztMapper.selectByPrimaryKey(sjTmCustom.getTuuid());
				dxxztList.add(dxxzt);
			}
		}
		return dxxztList;
	}

	@Override
	public List<Tkt> findTktBySjUuid(SjTmQueryVo sjTmQueryVo)
			throws Exception {
		//查询该试卷的填空题题试卷题目信息，取出题uuid添加到list，再查询tuuid在list中的填空题信息
		sjTmQueryVo.getSjTmCustom().setType(3);
		sjTmQueryVo.getSjTmCustom().setState(1);
		List<SjTmCustom> sjTmList = findSjTmList(sjTmQueryVo);
		
		//存在相应试卷题目再去查询具体类型题目信息
		List<Tkt> tktList=new ArrayList<Tkt>();
		if(sjTmList!=null&&sjTmList.size()>0){
			for(SjTmCustom sjTmCustom:sjTmList){
				Tkt tkt = tktMapper.selectByPrimaryKey(sjTmCustom.getTuuid());
				tktList.add(tkt);
			}
		}
		return tktList;
	}

	@Override
	public List<Pdt> findPdtBySjUuid(SjTmQueryVo sjTmQueryVo)
			throws Exception {
		//查询该试卷的判断题题试卷题目信息，取出题uuid添加到list，再查询tuuid在list中的判断题信息
		sjTmQueryVo.getSjTmCustom().setType(5);
		sjTmQueryVo.getSjTmCustom().setState(1);
		List<SjTmCustom> sjTmList = findSjTmList(sjTmQueryVo);
		
		//存在相应试卷题目再去查询具体类型题目信息
		List<Pdt> pdtList=new ArrayList<Pdt>();
		if(sjTmList!=null&&sjTmList.size()>0){
			for(SjTmCustom sjTmCustom:sjTmList){
				Pdt pdt = pdtMapper.selectByPrimaryKey(sjTmCustom.getTuuid());
				pdtList.add(pdt);
			}
		}
		return pdtList;
	}

	@Override
	public void deleteSjTmBySjUuidAndStatus(String sjUuid) throws Exception {
		SjTmExample sjTmExample=new SjTmExample();
		SjTmExample.Criteria sjTmCriteria = sjTmExample.createCriteria();
		sjTmCriteria.andSjidEqualTo(sjUuid);
		sjTmCriteria.andStateEqualTo(2);
		sjTmMapper.deleteByExample(sjTmExample);
	}

	@Override
	public List<Integer> getSjTmOrderByType(String sysuseruuid, String ksgluuid,
			Integer type,Integer size) throws Exception {
		String tm_order="";
		if(type==1)
			tm_order=DXT_ORDER;
		else if(type==3)
			tm_order=TKT_ORDER;
		
		String student_ksgl=sysuseruuid+"_"+ksgluuid;
		//查询缓存
		try {
			//如果缓存中有直接相应结果

			String json = jedisClient.hget(student_ksgl, tm_order);
			System.out.println(json);
			if(StringUtils.isNotBlank(json)){
				List<Integer> list = JsonUtils.jsonToList(json, Integer.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果没有,生成题目顺序
		List<Integer> list = RandomUtils.getDiffNO(size);
        System.out.println(JsonUtils.objectToJson(list));
		//把结果添加到缓存
		try {
			jedisClient.hset(student_ksgl, tm_order, JsonUtils.objectToJson(list));
			System.out.println("添加缓存");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void deleteSjTmByStatus(Integer status) throws Exception {
		SjTmExample sjTmExample=new SjTmExample();
		SjTmExample.Criteria sjTmCriteria = sjTmExample.createCriteria();
		sjTmCriteria.andStateEqualTo(status);
		sjTmMapper.deleteByExample(sjTmExample);
	}
	
}
