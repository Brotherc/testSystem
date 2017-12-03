package ytk.business.business.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;

import ytk.base.dao.mapper.KcMapper;
import ytk.base.dao.mapper.KcZyMapper;
import ytk.base.pojo.po.Kc;
import ytk.base.pojo.po.KcExample;
import ytk.base.pojo.po.Sysuser;
import ytk.base.process.context.Config;   
import ytk.base.process.result.ResultUtil;
import ytk.business.business.SjEbo;
import ytk.business.dao.mapper.DxtMapper;
import ytk.business.dao.mapper.DxxztMapper;
import ytk.business.dao.mapper.JdtMapper;
import ytk.business.dao.mapper.KsglMapper;
import ytk.business.dao.mapper.KsglMapperCustom;
import ytk.business.dao.mapper.SjMapper;
import ytk.business.dao.mapper.SjMapperCustom;
import ytk.business.dao.mapper.SjTmMapper;
import ytk.business.dao.mapper.SjTmMapperCustom;
import ytk.business.dao.mapper.SjdaMapper;
import ytk.business.dao.mapper.SjmbMapper;
import ytk.business.dao.mapper.TktMapper;
import ytk.business.pojo.po.Dxt;
import ytk.business.pojo.po.Dxxzt;
import ytk.business.pojo.po.Jdt;
import ytk.business.pojo.po.Ksgl;
import ytk.business.pojo.po.KsglExample;
import ytk.business.pojo.po.Sj;
import ytk.business.pojo.po.SjExample;
import ytk.business.pojo.po.SjExample.Criteria;
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
import ytk.business.pojo.vo.SjQueryVo;
import ytk.business.pojo.vo.SjTmCustom;
import ytk.business.pojo.vo.SjTmQueryVo;
import ytk.business.pojo.vo.SjmbCustom;
import ytk.util.MyUtil;
import ytk.util.UUIDBuild;

public class SjEbi implements SjEbo{

	@Autowired
	private SjTmMapper sjTmMapper; 
	@Autowired
	private SjMapper sjMapper;
	@Autowired
	private KcMapper kcMapper;
	@Autowired
	private SjMapperCustom sjMapperCustom;
	@Autowired
	private SjTmMapperCustom sjTmMapperCustom;
	@Autowired
	private SjmbMapper sjmbMapper;
	@Autowired
	private DxtMapper dxtMapper;
	@Autowired
	private DxxztMapper dxxztMapper;
	@Autowired
	private TktMapper tktMapper;
	@Autowired
	private JdtMapper jdtMapper;
	@Autowired
	private KcZyMapper kcZyMapper;
	@Autowired
	private SjdaMapper sjdaMapper;
	@Autowired
	private KsglMapper ksglMapper;
	@Autowired
	private KsglMapperCustom ksglMapperCustom;
	
	private static final Integer TM_TYPE_ONE=1;
	private static final Integer TM_TYPE_TWO=2;
	private static final Integer TM_TYPE_THREE=3;
	private static final Integer TM_TYPE_FOUR=4;
	
	private static final String TM_TYPE_ONEVIEW="一";
	private static final String TM_TYPE_TWOVIEW="二";
	private static final String TM_TYPE_THREEVIEW="三";
	private static final String TM_TYPE_FOURVIEW="四";
	
	public static final Map< Integer, String>tmTypeMap=new HashMap<Integer, String>();
	
	static{
		tmTypeMap.put(TM_TYPE_ONE, TM_TYPE_ONEVIEW);
		tmTypeMap.put(TM_TYPE_TWO, TM_TYPE_TWOVIEW);
		tmTypeMap.put(TM_TYPE_THREE, TM_TYPE_THREEVIEW);
		tmTypeMap.put(TM_TYPE_FOUR, TM_TYPE_FOURVIEW);
	}
	
	@Override
	public void addSj(SjCustom sjCustom,SjmbCustom sjmbCustom,Sysuser sysuser) throws Exception {
		//非空检验
		//试卷名称不允许为空
		String name = sjCustom.getName();
		if(name==null||name.trim().equals(""))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1013, null));
		//难度不允许为空
		Integer ndtype = sjCustom.getNdtype();
		if(ndtype==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1015, null));
		//课程不允许为空
		String kcname = sjCustom.getKcname();
		if(!MyUtil.isNotNullAndEmptyByTrim(kcname))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1007, null));
		
		//根据课程名称查询课程信息
		KcExample kcExample=new KcExample();
		KcExample.Criteria kcCriteria = kcExample.createCriteria();
		kcCriteria.andNameEqualTo(sjCustom.getKcname().trim());
		List<Kc> kcList = kcMapper.selectByExample(kcExample);
		if(kcList==null||kcList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1008, null));
		Kc kc = kcList.get(0);
		
		String uuid=sjCustom.getUuid();

		//题目的编号必须连续
		SjTmQueryVo sjTmQueryVo3=new SjTmQueryVo();
		SjTmCustom sjTmCustom3=new SjTmCustom();
		sjTmQueryVo3.setSjTmCustom(sjTmCustom3);
		sjTmCustom3.setSjid(uuid);
		
		//查询试卷单选题信息
		sjTmCustom3.setType(1);
		List<SjTmCustom> sjTmDxtList = sjTmMapperCustom.findSjTmList(sjTmQueryVo3);
		if(sjTmDxtList!=null&&sjTmDxtList.size()>0){
			for(int i=0;i<sjTmDxtList.size();i++){
				SjTm sjTm=sjTmDxtList.get(i);
				if(sjTm.getSjtmid()!=i+1)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1045, null));
			}
		}
		//查询试卷多项选择题信息
		sjTmCustom3.setType(2);
		List<SjTmCustom> sjTmDxxztList = sjTmMapperCustom.findSjTmList(sjTmQueryVo3);
		if(sjTmDxxztList!=null&&sjTmDxxztList.size()>0){
			for(int i=0;i<sjTmDxxztList.size();i++){
				SjTm sjTm=sjTmDxxztList.get(i);
				if(sjTm.getSjtmid()!=i+1)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1046, null));
			}
		}
		
		//查询试卷填空题信息
		sjTmCustom3.setType(3);
		List<SjTmCustom> sjTmTktList = sjTmMapperCustom.findSjTmList(sjTmQueryVo3);
		if(sjTmTktList!=null&&sjTmTktList.size()>0){
			for(int i=0;i<sjTmTktList.size();i++){
				SjTm sjTm=sjTmTktList.get(i);
				if(sjTm.getSjtmid()!=i+1)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1047, null));
			}
		}
		
		//查询试卷简答题信息
		sjTmCustom3.setType(4);
		List<SjTmCustom> sjTmJdtList = sjTmMapperCustom.findSjTmList(sjTmQueryVo3);
		if(sjTmJdtList!=null&&sjTmJdtList.size()>0){
			for(int i=0;i<sjTmJdtList.size();i++){
				SjTm sjTm=sjTmJdtList.get(i);
				if(sjTm.getSjtmid()!=i+1)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1048, null));
			}
		}
		
		//添加的试卷必须拥有试题
		SjTmExample sjTmExample=new SjTmExample();
		SjTmExample.Criteria sjTmCriteria = sjTmExample.createCriteria();
		sjTmCriteria.andSjidEqualTo(sjCustom.getUuid());
		List<SjTm> sjTmList = sjTmMapper.selectByExample(sjTmExample);
		if(sjTmList==null||sjTmList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1011, null));

		//添加的试卷模板中的总分必须等于各类型题总分相加
		
		//非空判断
		Integer dxtcount = sjmbCustom.getDxtcount();
		if(dxtcount==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1032, null));
		Integer dxtscore = sjmbCustom.getDxtscore();
		if(dxtscore==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1036, null));
		Integer dxxztcount = sjmbCustom.getDxxztcount();
		if(dxxztcount==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1033, null));
		Integer dxxztscore = sjmbCustom.getDxxztscore();
		if(dxxztscore==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1037, null));
		Integer tktcount = sjmbCustom.getTktcount();
		if(tktcount==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1034, null));
		Integer tktscore = sjmbCustom.getTktscore();
		if(tktscore==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1038, null));
		Integer jdtcount = sjmbCustom.getJdtcount();
		if(jdtcount==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1035, null));
		Integer jdtscore = sjmbCustom.getJdtscore();
		if(jdtscore==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1039, null));
		
		//如果题目数量大于0，则对应类型题目分数不能=0
		if(dxtcount!=0&&dxtscore==0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1051, null));
		if(dxxztcount!=0&&dxxztscore==0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1052, null));
		if(tktcount!=0&&tktscore==0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1053, null));
		if(jdtcount!=0&&jdtscore==0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1054, null));
		
		//试卷模板总分
		Integer sjScore=dxtscore+dxxztscore+tktscore+jdtscore;
		if(sjScore==null||sjScore.equals("")||sjScore==0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1055, null));
		
		//该试卷的各类型题目分数相加必须等于模板中各类型题总分,各类型题目数量相加必须等于模板中各类型题数量
		
		
		//获取该试卷单选题目总分
		SjTmQueryVo sjTmQueryVo=new SjTmQueryVo();
		SjTmCustom sjTmCustom=new SjTmCustom();
		sjTmCustom.setSjid(uuid);
		sjTmCustom.setType(1);  
		sjTmQueryVo.setSjTmCustom(sjTmCustom);
		Integer dxtScore = sjTmMapperCustom.findSjTmScoreByType(sjTmQueryVo);
		
		//若试卷单选题目总分不等于模板单选题总分，则抛出异常
		if(dxtScore==null){
			if(sjmbCustom.getDxtscore()!=0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1023, null));
		}
		else if(dxtScore!=sjmbCustom.getDxtscore())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1023, null));
		
		int dxtCount = sjTmMapperCustom.findSjTmCountByType(sjTmQueryVo);
		//若试卷单选题目总量不等于模板单选题总量，则抛出异常
		if(dxtCount!=sjmbCustom.getDxtcount())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1027, null));
		
		//获取该试卷多项选择题目总分
		sjTmCustom.setType(2);
		sjTmQueryVo.setSjTmCustom(sjTmCustom);
		Integer dxxztScore = sjTmMapperCustom.findSjTmScoreByType(sjTmQueryVo);
		//若试卷多项选择题目总分不等于模板多项选择题总分，则抛出异常
		if(dxxztScore==null){
			if(sjmbCustom.getDxxztscore()!=0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1024, null));
		}
		else if(dxxztScore!=sjmbCustom.getDxxztscore())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1024, null));
		
		int dxxztCount = sjTmMapperCustom.findSjTmCountByType(sjTmQueryVo);
		//若试卷多项选择题目总量不等于模板多项选择题总量，则抛出异常
		if(dxxztCount!=sjmbCustom.getDxxztcount())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1028, null));
		
		//获取该试卷填空题目总分
		sjTmCustom.setType(3);
		sjTmQueryVo.setSjTmCustom(sjTmCustom);
		Integer tktScore = sjTmMapperCustom.findSjTmScoreByType(sjTmQueryVo);
		//若试卷填空题目总分不等于模板填空题总分，则抛出异常
		
		if(tktScore==null){
			if(sjmbCustom.getTktscore()!=0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1025, null));
		}
		else if(tktScore!=sjmbCustom.getTktscore())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1025, null));
		
		int tktCount = sjTmMapperCustom.findSjTmCountByType(sjTmQueryVo);
		//若试卷填空题目总量不等于模板填空题总量，则抛出异常
		if(tktCount!=sjmbCustom.getTktcount())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1029, null));
		
		//获取该试卷简答题目总分
		sjTmCustom.setType(4);
		sjTmQueryVo.setSjTmCustom(sjTmCustom);
		Integer jdtScore = sjTmMapperCustom.findSjTmScoreByType(sjTmQueryVo);
		//若试卷简答目总分不等于模板简答题总分，则抛出异常
		
		if(jdtScore==null){
			if(sjmbCustom.getJdtscore()!=0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1026, null));
		}
		else if(jdtScore!=sjmbCustom.getJdtscore())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1026, null));
		
		int jdtCount = sjTmMapperCustom.findSjTmCountByType(sjTmQueryVo);
		//若试卷简答题目总量不等于模板简答题总量，则抛出异常
		if(jdtCount!=sjmbCustom.getJdtcount())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1030, null));
		
		//添加相应试卷模板
		//判断是否已存在该模板，若不存在，则添加
		SjmbExample sjmbExample=new SjmbExample();
		SjmbExample.Criteria sjmbCriteria = sjmbExample.createCriteria();
		sjmbCriteria.andScoreEqualTo(sjScore);
		sjmbCriteria.andDxtcountEqualTo(dxtcount);
		sjmbCriteria.andDxtscoreEqualTo(dxtscore);
		sjmbCriteria.andDxxztcountEqualTo(dxxztcount);
		sjmbCriteria.andDxxztscoreEqualTo(dxxztscore);
		sjmbCriteria.andTktcountEqualTo(tktcount);
		sjmbCriteria.andTktscoreEqualTo(tktscore);
		sjmbCriteria.andJdtcountEqualTo(jdtcount);
		sjmbCriteria.andJdtscoreEqualTo(jdtscore);
		List<Sjmb> sjmbList = sjmbMapper.selectByExample(sjmbExample);
		if(sjmbList==null||sjmbList.size()<1){
			//添加试卷模板uuid
			sjmbCustom.setUuid(UUIDBuild.getUUID());
			//添加试卷模板状态
			sjmbCustom.setStatus(1);
			sjmbCustom.setScore(sjScore);
			//添加试卷模板
			sjmbMapper.insert(sjmbCustom);
		}
		else
			sjmbCustom.setUuid(sjmbList.get(0).getUuid());
		
		//试卷总分必须等于试卷总题目总分
		int sjTmSumScore=0;
		for(SjTm sjTm:sjTmList){
			sjTmSumScore+=sjTm.getScore();
		}
		if(sjScore!=sjTmSumScore)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1012, null));
		
		//添加试卷
		//添加页面传过来uuid
		Sj sj=new Sj();
		sj.setUuid(uuid);
		
		sj.setName(name);
		sj.setScore(sjScore);
		//添加试卷模板uuid
		sj.setSjmbid(sjmbCustom.getUuid());

		sj.setKcid(kc.getUuid());
		
		sj.setNdtype(ndtype);
		sj.setTeacherid(sysuser.getUuid());
		sj.setCreatetime(System.currentTimeMillis());
		sj.setUpdatetime(System.currentTimeMillis());
		sjMapper.insert(sj);
		
		//添加试卷答案
		SjTmExample sjTmExample2=new SjTmExample();
		SjTmExample.Criteria sjTmCriteria2 = sjTmExample2.createCriteria();
		sjTmCriteria2.andSjidEqualTo(uuid);
		List<SjTm> sjTmList2 = sjTmMapper.selectByExample(sjTmExample2);
		//获取该试卷的题目信息
		for(SjTm sjTm:sjTmList2){
			Sjda sjda=new Sjda();
			sjda.setUuid(UUIDBuild.getUUID());
			sjda.setSjid(uuid);
			sjda.setSjxitmid(sjTm.getUuid());
			sjda.setSjtmid(sjTm.getSjtmid());
			sjda.setType(sjTm.getType());
			//单选题
			if(sjTm.getType()==1){
				Dxt dxt = dxtMapper.selectByPrimaryKey(sjTm.getTuuid());
				sjda.setAnswer(dxt.getAnswer());
			}
			//多项选择题
			else if(sjTm.getType()==2){
				Dxxzt dxxzt = dxxztMapper.selectByPrimaryKey(sjTm.getTuuid());
				sjda.setAnswer(dxxzt.getAnswer());
			}
			//填空题
			else if(sjTm.getType()==3){
				Tkt tkt = tktMapper.selectByPrimaryKey(sjTm.getTuuid());
				sjda.setAnswer(tkt.getAnswer());
			}
			//简答题
			else if(sjTm.getType()==4){
				Jdt jdt = jdtMapper.selectByPrimaryKey(sjTm.getTuuid());
				sjda.setAnswer(jdt.getAnswer());
			}
			sjdaMapper.insert(sjda);
		}
		
		//将之前添加的试卷题目信息状态修改为正常
		for(SjTm sjTm:sjTmList){
			sjTm.setState(1);
			sjTmMapper.updateByPrimaryKey(sjTm);
		}
	}

	@Override
	public List<SjCustom> findSjList(SjQueryVo sjQueryVo) throws Exception {
		return sjMapperCustom.findSjList(sjQueryVo);
	}

	@Override
	public int findSjListCount(SjQueryVo sjQueryVo) throws Exception {
		return sjMapperCustom.findSjListCount(sjQueryVo);
	}

	@Override
	public void deleteSj(String uuid) throws Exception {
		
		//如果删除的试卷存在考试，则不允许删除
		KsglExample ksglExample=new KsglExample();
		KsglExample.Criteria ksglCriteria = ksglExample.createCriteria();
		ksglCriteria.andSjuuidEqualTo(uuid);
		List<Ksgl> ksglList = ksglMapper.selectByExample(ksglExample);
		if(ksglList!=null&&ksglList.size()>0)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1059, null));
		
		//删除的试卷信息必须存在
		Sj sj = sjMapper.selectByPrimaryKey(uuid);
		if(sj==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1020, null));
		
		sjMapper.deleteByPrimaryKey(uuid);
		
		//删除试卷对应的试卷题目信息
		SjTmExample sjTmExample=new SjTmExample();
		SjTmExample.Criteria criteria = sjTmExample.createCriteria();
		criteria.andSjidEqualTo(uuid);
		sjTmMapper.deleteByExample(sjTmExample);
		
		//删除试卷对应答案信息
		SjdaExample sjdaExample=new SjdaExample();
		SjdaExample.Criteria sjdaCriteria = sjdaExample.createCriteria();
		sjdaCriteria.andSjidEqualTo(uuid);
		sjdaMapper.deleteByExample(sjdaExample);
	}

	@Override
	public String exportSj(SjQueryVo sjQueryVo,String filePath) throws Exception {
		
		//试卷信息存在
		List<SjCustom> sjList = sjMapperCustom.findSjList(sjQueryVo);
		if(sjList==null||sjList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1020, null));
		
		SjCustom sjCustom = sjList.get(0);
		
		//导出文件的前缀
		String fileName="sj_"+MyUtil.getCurrentTimeStr()+".doc";
	       File file = new File(filePath+fileName);
	       
	       // 设置纸张大小
	       Document document = new Document(PageSize.A4);
	       
	       RtfWriter2.getInstance(document, new FileOutputStream(file));

	       document.open();

	       // 设置中文字体
	       BaseFont bfChinese = BaseFont.createFont(BaseFont.HELVETICA,
	                  BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);

	       // 标题字体风格
	       Font titleFont = new Font(bfChinese, 12, Font.BOLD);

	       //正文字体风格
	       Font contextFont = new Font(bfChinese, 10, Font.NORMAL);

	       Paragraph sjName = new Paragraph(sjCustom.getName());
	       // 设置标题格式对齐方式
	       sjName.setAlignment(Element.ALIGN_CENTER);

	       sjName.setFont(titleFont);

	       document.add(sjName);

	       String sjScore = "卷面总分:"+sjCustom.getScore()+"分";

	       Paragraph sjScoreP = new Paragraph(sjScore);

	       sjScoreP.setAlignment(Element.ALIGN_CENTER);
	       
	       sjScoreP.setSpacingBefore(1);
	       
	       document.add(sjScoreP);
	       
	       //添加题目信息
	       
	       //获取该试卷单选题信息
	       SjTmQueryVo sjTmQueryVo=new SjTmQueryVo();
	       SjTmCustom sjTmCustom=new SjTmCustom();
	       sjTmCustom.setSjid(sjCustom.getUuid());
	       sjTmCustom.setType(1);
	       sjTmQueryVo.setSjTmCustom(sjTmCustom);
	       List<SjTmCustom> sjTmList = sjTmMapperCustom.findSjTmList(sjTmQueryVo);
			//题目类型编号
			Integer tmTypeId=1;
	       if(sjTmList!=null&&sjTmList.size()>0){
	    	   //doc输入单选题信息
	    	   Paragraph dxtsTitle = new Paragraph(tmTypeMap.get(tmTypeId)+"丶单选题");
	    	   tmTypeId++;
	    	   dxtsTitle.setAlignment(Element.ALIGN_LEFT);
	    	   dxtsTitle.setSpacingBefore(2);
	    	   document.add(dxtsTitle);
	    	   
				int i=1;
				for(SjTmCustom sjTm:sjTmList){
					Dxt dxt = dxtMapper.selectByPrimaryKey(sjTm.getTuuid());
					
					String sjtmid=i+".";
					String content = dxt.getContent();
					String optiona="A.  "+dxt.getOptiona();
					String optionb="B.  "+dxt.getOptionb();
					String optionc="C.  "+dxt.getOptionc();
					String optiond="D.  "+dxt.getOptiond();
					i++;
					
			    	//doc输入单选题题号与内容信息
					Paragraph dxtTitle = new Paragraph(sjtmid+"   "+content);
					dxtTitle.setAlignment(Element.ALIGN_LEFT);
					dxtTitle.setSpacingBefore(1);
			    	document.add(dxtTitle);
			    	
			    	//doc输入单选题选项A与选项B				
					Paragraph optionA_B = new Paragraph(optiona+"             "+optionb);
					optionA_B.setAlignment(Element.ALIGN_LEFT);
					optionA_B.setFirstLineIndent(5);
					optionA_B.setSpacingBefore(1);
			    	document.add(optionA_B);
			    	
			    	//doc输入单选题选项C与选项D						
					Paragraph optionC_D = new Paragraph(optionc+"             "+optiond);
					optionC_D.setAlignment(Element.ALIGN_LEFT);
					optionC_D.setFirstLineIndent(5);
					optionC_D.setSpacingBefore(1);
			    	document.add(optionC_D);
				}
	       }
	       
	       //获取该试卷多项选题信息
	       sjTmCustom.setType(2);
	       sjTmQueryVo.setSjTmCustom(sjTmCustom);
	       sjTmList = sjTmMapperCustom.findSjTmList(sjTmQueryVo);
	       //该试卷存在多项选择题信息
	       if(sjTmList!=null&&sjTmList.size()>0){
	    	   //doc输入多项选择题信息
	    	   Paragraph dxxztsTitle = new Paragraph(tmTypeMap.get(tmTypeId)+"丶多项选择题");
	    	   tmTypeId++;
	    	   dxxztsTitle.setAlignment(Element.ALIGN_LEFT);
	    	   dxxztsTitle.setSpacingBefore(2);
	    	   document.add(dxxztsTitle);
	    	   
				int i=1;
				for(SjTmCustom sjTm:sjTmList){
					Dxxzt dxxzt = dxxztMapper.selectByPrimaryKey(sjTm.getTuuid());
					
					String sjtmid=i+".";
					String content = dxxzt.getContent();
					String optiona="A.  "+dxxzt.getOptiona();
					String optionb="B.  "+dxxzt.getOptionb();
					String optionc="C.  "+dxxzt.getOptionc();
					String optiond="D.  "+dxxzt.getOptiond();
					String optione="E.  "+dxxzt.getOptione();
					String optionf="F.  "+dxxzt.getOptionf();
					i++;
					
			    	//doc输入多项选择题号与内容信息
					Paragraph dxxztTitle = new Paragraph(sjtmid+"   "+content);
					dxxztTitle.setAlignment(Element.ALIGN_LEFT);
					dxxztTitle.setSpacingBefore(1);
			    	document.add(dxxztTitle);
			    	
			    	//doc输入多项选择题选项A与选项B与选项C				
					Paragraph optionA_B_C = new Paragraph(optiona+"             "+optionb+"             "+optionc);
					optionA_B_C.setAlignment(Element.ALIGN_LEFT);
					optionA_B_C.setFirstLineIndent(5);
					optionA_B_C.setSpacingBefore(1);
			    	document.add(optionA_B_C);
			    	
			    	//doc输入多项选择题选项D与选项E与选项F	
					Paragraph optionD_E_F = new Paragraph(optiond+"             "+optione+"             "+optionf);
					optionD_E_F.setAlignment(Element.ALIGN_LEFT);
					optionD_E_F.setFirstLineIndent(5);
					optionD_E_F.setSpacingBefore(1);
			    	document.add(optionD_E_F);
				}
			}
	       
	       
	       //获取该试卷填空题信息
	       sjTmCustom.setType(3);
	       sjTmQueryVo.setSjTmCustom(sjTmCustom);
	       sjTmList = sjTmMapperCustom.findSjTmList(sjTmQueryVo);
	       if(sjTmList!=null&&sjTmList.size()>0){
	    	   //doc输入填空题信息
	    	   Paragraph tktsTitle = new Paragraph(tmTypeMap.get(tmTypeId)+"丶填空题");
	    	   tmTypeId++;
	    	   tktsTitle.setAlignment(Element.ALIGN_LEFT);
	    	   tktsTitle.setSpacingBefore(2);
	    	   document.add(tktsTitle);
	    	   
				int i=1;
				for(SjTmCustom sjTm:sjTmList){
					Tkt tkt = tktMapper.selectByPrimaryKey(sjTm.getTuuid());
					
					String sjtmid=i+".";
					String content = tkt.getContent();
					i++;
					
			    	//doc输入填空题号与内容信息
					Paragraph tktTitle = new Paragraph(sjtmid+"   "+content);
					tktTitle.setAlignment(Element.ALIGN_LEFT);
					tktTitle.setSpacingBefore(1);
			    	document.add(tktTitle);
				}
			}
	       
	       
	       //获取该试卷简答题信息
	       sjTmCustom.setType(4);
	       sjTmQueryVo.setSjTmCustom(sjTmCustom);
	       sjTmList = sjTmMapperCustom.findSjTmList(sjTmQueryVo);
	       if(sjTmList!=null&&sjTmList.size()>0){
	    	   //doc输入简答题信息
	    	   Paragraph jstsTitle = new Paragraph(tmTypeMap.get(tmTypeId)+"丶简答题");
	    	   tmTypeId++;
	    	   jstsTitle.setAlignment(Element.ALIGN_LEFT);
	    	   jstsTitle.setSpacingBefore(2);
	    	   document.add(jstsTitle);
	    	   
				int i=1;
				for(SjTmCustom sjTm:sjTmList){
					Jdt jdt = jdtMapper.selectByPrimaryKey(sjTm.getTuuid());
					
					String sjtmid=i+".";
					String content = jdt.getContent();
					i++;
					
			    	//doc输入简答题号与内容信息
					Paragraph jdtTitle = new Paragraph(sjtmid+"   "+content);
					jdtTitle.setAlignment(Element.ALIGN_LEFT);
					jdtTitle.setSpacingBefore(1);
			    	document.add(jdtTitle);
				}
			}
	       
/*	       // 正文格式左对齐

	       context.setAlignment(Element.ALIGN_LEFT);

	       // context.setFont(contextFont);

	       // 离上一段落（标题）空的行数

	       context.setSpacingBefore(5);

	       // 设置第一行空的列数

	       context.setFirstLineIndent(20);

	       document.add(context);
	       //
	       // // 利用类FontFactory结合Font和Color可以设置各种各样字体样式
	       //
	       // Paragraph underline = new Paragraph("下划线的实现", FontFactory.getFont(
	       // FontFactory.HELVETICA_BOLDOBLIQUE, 18, Font.UNDERLINE,
	       // new Color(0, 0, 255)));
	       //
	       // document.add(underline);
	       //

	       // // 添加图片 Image.getInstance即可以放路径又可以放二进制字节流
	       //


	       //Image img = Image.getInstance("D:\\eclipseWorkspace\\wordTest\\01055378_0.jpg");

	       //img.setAbsolutePosition(0, 0);

	       //img.setAlignment(Image.LEFT);// 设置图片显示位置

	       //
	       // img.scaleAbsolute(60, 60);// 直接设定显示尺寸
	       //
	       // // img.scalePercent(50);//表示显示的大小为原尺寸的50%
	       //
	       // // img.scalePercent(25, 12);//图像高宽的显示比例
	       //
	       // // img.setRotation(30);//图像旋转一定角度
	       //
	       //document.add(img);
*/
	       document.close();
	       
	       String path="upload/"+fileName;
	       return path;
	}

	@Override
	public SjCustom findSjCustomByUuid(String uuid) throws Exception {
		SjQueryVo sjQueryVo=new SjQueryVo();
		SjCustom sjCustom=new SjCustom();
		sjCustom.setUuid(uuid);
		sjQueryVo.setSjCustom(sjCustom);
		List<SjCustom> sjList = sjMapperCustom.findSjList(sjQueryVo);
		return sjList==null?null:sjList.get(0);
	}

	@Override
	public void updateSj(SjCustom sjCustom, SjmbCustom sjmbCustom) throws Exception {
		String uuid=sjCustom.getUuid();
		
		//查询该试卷多对应的所有考试中最早开考的时间
		KsglQueryVo ksglQueryVo=new KsglQueryVo();
		KsglCustom ksglCustom=new KsglCustom();
		ksglCustom.setSjuuid(uuid);
		ksglQueryVo.setKsglCustom(ksglCustom);
		
		Long starttime = ksglMapperCustom.findKsglMinStarttime(ksglQueryVo);
		//如果当前时间大于最早开考时间，则不允许修改
		if(starttime!=null&&System.currentTimeMillis()>starttime)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1044, null));
		//非空检验
		String name = sjCustom.getName();
		if(name==null||name.trim().equals(""))
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1013, null));
/*		Integer sjScore = sjmbCustom.getScore();
		if(sjScore==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1014, null));*/
		Integer ndtype = sjCustom.getNdtype();
		if(ndtype==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1015, null));
/*		Long time = sjCustom.getTime();
		if(time==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1016, null));*/
/*		if(zyList==null||zyList.length<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1041, null));*/
		
		//选择的考试专业必须存在对应的考试课程
		//根据课程名称查询课程信息
		KcExample kcExample=new KcExample();
		KcExample.Criteria kcCriteria = kcExample.createCriteria();
		kcCriteria.andNameEqualTo(sjCustom.getKcname().trim());
		List<Kc> kcList = kcMapper.selectByExample(kcExample);
		if(kcList==null||kcList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1008, null));
		

/*		for(Long zyUuid:zyList){
			KcZyExample kcZyExample=new KcZyExample();
			KcZyExample.Criteria kcZyCriteria = kcZyExample.createCriteria();
			kcZyCriteria.andKcuuidEqualTo(kc.getUuid());
			kcZyCriteria.andZyuuidEqualTo(zyUuid);
			List<KcZy> kcZyList = kcZyMapper.selectByExample(kcZyExample);
			if(kcZyList==null||kcZyList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1042, null));
		}*/
		

		
		//获取未修改前的试卷信息
		Sj sj = sjMapper.selectByPrimaryKey(uuid);
		
		//如果试卷名修改了，则不允许添加重复的试卷，即名称必须唯一
		if(!sj.getName().equals(sjCustom.getName())){
			SjExample sjExample=new SjExample();
			Criteria criteria = sjExample.createCriteria();
			criteria.andNameEqualTo(name);
			List<Sj> sjList = sjMapper.selectByExample(sjExample);
			if(sjList!=null&&sjList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1010, null));
		}
		//添加的试卷必须拥有试题
		SjTmExample sjTmExample=new SjTmExample();
		SjTmExample.Criteria sjTmCriteria = sjTmExample.createCriteria();
		sjTmCriteria.andSjidEqualTo(sjCustom.getUuid());
		List<SjTm> sjTmList = sjTmMapper.selectByExample(sjTmExample);
		if(sjTmList==null||sjTmList.size()<1)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1011, null));

		//添加的试卷模板中的总分必须等于各类型题总分相加
		Integer dxtcount = sjmbCustom.getDxtcount();
		if(dxtcount==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1032, null));
		Integer dxtscore = sjmbCustom.getDxtscore();
		if(dxtscore==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1036, null));
		Integer dxxztcount = sjmbCustom.getDxxztcount();
		if(dxxztcount==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1033, null));
		Integer dxxztscore = sjmbCustom.getDxxztscore();
		if(dxxztscore==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1037, null));
		Integer tktcount = sjmbCustom.getTktcount();
		if(tktcount==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1034, null));
		Integer tktscore = sjmbCustom.getTktscore();
		if(tktscore==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1038, null));
		Integer jdtcount = sjmbCustom.getJdtcount();
		if(jdtcount==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1035, null));
		Integer jdtscore = sjmbCustom.getJdtscore();
		if(jdtscore==null)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1039, null));
		
/*		if(sjScore!=dxtscore+dxxztscore+tktscore+jdtscore)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1022, null));*/
		Integer sjScore=dxtscore+dxxztscore+tktscore+jdtscore;
		
		//该试卷的各类型题目分数相加必须等于模板中各类型题总分,各类型题目数量相加必须等于模板中各类型题数量
		//获取该试卷单选题目总分
		SjTmQueryVo sjTmQueryVo=new SjTmQueryVo();
		SjTmCustom sjTmCustom=new SjTmCustom();
		sjTmCustom.setSjid(uuid);
		sjTmCustom.setType(1);
		sjTmQueryVo.setSjTmCustom(sjTmCustom);
		Integer dxtScore = sjTmMapperCustom.findSjTmScoreByType(sjTmQueryVo);
		//若试卷单选题目总分不等于模板单选题总分，则抛出异常
		if(dxtScore==null){
			if(sjmbCustom.getDxtscore()!=0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1023, null));
		}
		else if(dxtScore!=sjmbCustom.getDxtscore())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1023, null));
		int dxtCount = sjTmMapperCustom.findSjTmCountByType(sjTmQueryVo);
		//若试卷单选题目总量不等于模板单选题总量，则抛出异常
		if(dxtCount!=sjmbCustom.getDxtcount())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1027, null));
		
		//获取该试卷多项选择题目总分
		sjTmCustom.setType(2);
		sjTmQueryVo.setSjTmCustom(sjTmCustom);
		Integer dxxztScore = sjTmMapperCustom.findSjTmScoreByType(sjTmQueryVo);
		//若试卷多项选择题目总分不等于模板多项选择题总分，则抛出异常
		System.out.println(dxxztScore+"   "+sjmbCustom.getDxxztscore());
		if(dxxztScore==null){
			if(sjmbCustom.getDxxztscore()!=0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1024, null));
		}
		else if(dxxztScore!=sjmbCustom.getDxxztscore())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1024, null));
		
		int dxxztCount = sjTmMapperCustom.findSjTmCountByType(sjTmQueryVo);
		//若试卷多项选择题目总量不等于模板多项选择题总量，则抛出异常
		if(dxxztCount!=sjmbCustom.getDxxztcount())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1028, null));
		
		//获取该试卷填空题目总分
		sjTmCustom.setType(3);
		sjTmQueryVo.setSjTmCustom(sjTmCustom);
		Integer tktScore = sjTmMapperCustom.findSjTmScoreByType(sjTmQueryVo);
		//若试卷填空题目总分不等于模板填空题总分，则抛出异常
		
		if(tktScore==null){
			if(sjmbCustom.getTktscore()!=0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1025, null));
		}
		else if(tktScore!=sjmbCustom.getTktscore())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1025, null));
		
		int tktCount = sjTmMapperCustom.findSjTmCountByType(sjTmQueryVo);
		//若试卷填空题目总量不等于模板填空题总量，则抛出异常
		if(tktCount!=sjmbCustom.getTktcount())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1029, null));
		
		
		//获取该试卷简答题目总分
		sjTmCustom.setType(4);
		sjTmQueryVo.setSjTmCustom(sjTmCustom);
		Integer jdtScore = sjTmMapperCustom.findSjTmScoreByType(sjTmQueryVo);
		//若试卷简答目总分不等于模板简答题总分，则抛出异常
		
		if(jdtScore==null){
			if(sjmbCustom.getJdtscore()!=0)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1026, null));
		}
		else if(jdtScore!=sjmbCustom.getJdtscore())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1026, null));
		
		int jdtCount = sjTmMapperCustom.findSjTmCountByType(sjTmQueryVo);
		//若试卷简答题目总量不等于模板简答题总量，则抛出异常
		if(jdtCount!=sjmbCustom.getJdtcount())
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1030, null));
		
		//添加相应试卷模板
		//判断是否已存在该模板，若不存在，则添加
		SjmbExample sjmbExample=new SjmbExample();
		SjmbExample.Criteria sjmbCriteria = sjmbExample.createCriteria();
		sjmbCriteria.andScoreEqualTo(sjScore);
		sjmbCriteria.andDxtcountEqualTo(dxtcount);
		sjmbCriteria.andDxtscoreEqualTo(dxtscore);
		sjmbCriteria.andDxxztcountEqualTo(dxxztcount);
		sjmbCriteria.andDxxztscoreEqualTo(dxxztscore);
		sjmbCriteria.andTktcountEqualTo(tktcount);
		sjmbCriteria.andTktscoreEqualTo(tktscore);
		sjmbCriteria.andJdtcountEqualTo(jdtcount);
		sjmbCriteria.andJdtscoreEqualTo(jdtscore);
		List<Sjmb> sjmbList = sjmbMapper.selectByExample(sjmbExample);
		if(sjmbList==null||sjmbList.size()<1){
			//添加试卷模板uuid
			//添加试卷模板状态
			sjmbCustom.setUuid(UUIDBuild.getUUID());
			sjmbCustom.setStatus(1);
			sjmbCustom.setScore(sjScore);
			//添加试卷模板
			sjmbMapper.insert(sjmbCustom);
		}
		else
			sjmbCustom.setUuid(sjmbList.get(0).getUuid());
		
		
		//试卷总分必须等于试卷总题目总分
		int sjTmSumScore=0;
		for(SjTm sjTm:sjTmList){
			sjTmSumScore+=sjTm.getScore();
		}
		if(sjScore!=sjTmSumScore)
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1012, null));
		

		
		sj.setName(name);
		sj.setScore(sjScore);
		//添加试卷模板uuid
		sj.setSjmbid(sjmbCustom.getUuid());
		
		
		sj.setNdtype(ndtype);
/*		sj.setTime(time);*/
		sj.setUpdatetime(System.currentTimeMillis());
		sjMapper.updateByPrimaryKey(sj);
		
		//将之前试卷的专业信息删除
/*		SjZyExample sjZyExample=new SjZyExample();
		SjZyExample.Criteria sjZyCriteria = sjZyExample.createCriteria();
		sjZyCriteria.andSjuuidEqualTo(uuid);
		sjZyMapper.deleteByExample(sjZyExample);*/
		//添加试卷考试专业信息
/*		for(Long zyUuid:zyList){
			SjZy sjZy=new SjZy();
			String sjZyUuid = UUIDBuild.getUUID();
			sjZy.setUuid(sjZyUuid);
			sjZy.setSjuuid(uuid);
			sjZy.setZyuuid(zyUuid);
			sjZyMapper.insert(sjZy);
		}*/
		
		//修改试卷答案(将之前的试卷答案删除，添加修改后的试卷答案)
/*		SjdaExample sjdaExample=new SjdaExample();
		SjdaExample.Criteria sjdaCriteria = sjdaExample.createCriteria();
		sjdaCriteria.andSjidEqualTo(uuid);
		sjdaMapper.deleteByExample(sjdaExample);
		
		SjTmQueryVo sjTmQueryVo2=new SjTmQueryVo();
		SjTmCustom sjTmCustom2=new SjTmCustom();
		sjTmCustom2.setSjid(uuid);
		sjTmQueryVo2.setSjTmCustom(sjTmCustom2);
		//获取该试卷的系题目信息
		List<SjTmCustom> sjTmList2 = sjTmMapperCustom.SjTmList(sjTmQueryVo2);
		for(SjTmCustom sjTm:sjTmList2){
			Sjda sjda=new Sjda();
			sjda.setUuid(UUIDBuild.getUUID());
			sjda.setSjid(uuid);
			sjda.setSjxitmid(sjTm.getUuid());
			sjda.setSjtmid(sjTm.getSjtmid());
			sjda.setType(sjTm.getXttype());
			//单选题
			if(sjTm.getXttype()==1){
				Dxt dxt = dxtMapper.selectByPrimaryKey(sjTm.getTuuid());
				sjda.setAnswer(dxt.getAnswer());
			}
			//多项选择题
			else if(sjTm.getXttype()==2){
				Dxxzt dxxzt = dxxztMapper.selectByPrimaryKey(sjTm.getTuuid());
				sjda.setAnswer(dxxzt.getAnswer());
			}
			//填空题
			else if(sjTm.getXttype()==3){
				Tkt tkt = tktMapper.selectByPrimaryKey(sjTm.getTuuid());
				sjda.setAnswer(tkt.getAnswer());
			}
			//简答题
			else if(sjTm.getXttype()==4){
				Jdt jdt = jdtMapper.selectByPrimaryKey(sjTm.getTuuid());
				sjda.setAnswer(jdt.getAnswer());
			}
			sjdaMapper.insert(sjda);
		}*/
		
		//将之前添加的试卷题目信息状态修改为正常
/*		for(SjTm sjTm:sjTmList){
			sjTm.setState(1);
			sjTmMapper.updateByPrimaryKey(sjTm);
		}*/
		
	}

}
