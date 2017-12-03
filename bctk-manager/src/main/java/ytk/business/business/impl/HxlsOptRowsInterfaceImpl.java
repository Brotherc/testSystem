package ytk.business.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.dao.mapper.DictinfoMapper;
import ytk.base.dao.mapper.KcMapper;
import ytk.base.dao.mapper.KcZyMapperCustom;
import ytk.base.dao.mapper.SysuserMapper;
import ytk.base.dao.mapper.TeacherKcMapper;
import ytk.base.dao.mapper.ZsdMapper;
import ytk.base.pojo.po.Dictinfo;
import ytk.base.pojo.po.DictinfoExample;
import ytk.base.pojo.po.Kc;
import ytk.base.pojo.po.KcExample;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.po.SysuserExample;
import ytk.base.pojo.po.TeacherKc;
import ytk.base.pojo.po.TeacherKcExample;
import ytk.base.pojo.po.Zsd;
import ytk.base.pojo.po.ZsdExample;
import ytk.base.pojo.po.SysuserExample.Criteria;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.business.dao.mapper.DxtMapper;
import ytk.business.dao.mapper.DxxztMapper;
import ytk.business.dao.mapper.JdtMapper;
import ytk.business.dao.mapper.TktMapper;
import ytk.business.dao.mapper.TmZsdMapper;
import ytk.business.pojo.po.Dxt;
import ytk.business.pojo.po.DxtExample;
import ytk.business.pojo.po.Dxxzt;
import ytk.business.pojo.po.DxxztExample;
import ytk.business.pojo.po.Jdt;
import ytk.business.pojo.po.JdtExample;
import ytk.business.pojo.po.TmZsd;
import ytk.util.HxlsOptRowsInterface;
import ytk.util.MyUtil;
import ytk.util.UUIDBuild;

/**
 * 测试导入数据接口
 * @author Thinkpad
 *
 */
public class HxlsOptRowsInterfaceImpl implements HxlsOptRowsInterface {

	@Autowired
	private DxtMapper dxtMapper;
	@Autowired
	private DxxztMapper dxxztMapper;
	@Autowired
	private TktMapper tktMapper;
	@Autowired
	private JdtMapper jdtMapper;
	@Autowired
	private SysuserMapper sysuserMapper;
	@Autowired
	private KcMapper kcMapper;
	@Autowired
	private KcZyMapperCustom kcZyMapperCustom;
	@Autowired
	private DictinfoMapper dictinfoMapper;
	@Autowired
	private TeacherKcMapper teacherKcMapper;
	@Autowired
	private ZsdMapper zsdMapper;
	@Autowired
	private TmZsdMapper tmZsdMapper;
	
	@Override
	public String optRows(int sheetIndex, int curRow, List<String> rowlist)
			throws Exception {
		try {
			//得到导入的数据
			//rowlist数据 是一行数据，按照模版解析
			String typeString=rowlist.get(0);
			if(!MyUtil.isNotNullAndEmptyByTrim(typeString))
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"题目类型"}));
			Integer type=new Integer(typeString);//类型
			String kcname=rowlist.get(1);//课程
			if(!MyUtil.isNotNullAndEmptyByTrim(kcname))
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"课程名称"}));
			String content = rowlist.get(2);//内容
			if(!MyUtil.isNotNullAndEmptyByTrim(content))
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"内容"}));
			String optiona = rowlist.get(3);//A
			if(type==1||type==2){
				if(!MyUtil.isNotNullAndEmptyByTrim(optiona))
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项A"}));
			}
			String optionb = rowlist.get(4);//B
			if(type==1||type==2){
				if(!MyUtil.isNotNullAndEmptyByTrim(optionb))
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项B"}));
			}
			String optionc = rowlist.get(5);//C
			if(type==1||type==2){
				if(!MyUtil.isNotNullAndEmptyByTrim(optionc))
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项C"}));
			}
			String optiond = rowlist.get(6);//D
			if(type==1||type==2){
				if(!MyUtil.isNotNullAndEmptyByTrim(optiond))
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项D"}));
			}
			String optione = rowlist.get(7);//E
			if(type==2){
				if(!MyUtil.isNotNullAndEmptyByTrim(optione))
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项E"}));
			}
			String optionf = rowlist.get(8);//F
			if(type==2){
				if(!MyUtil.isNotNullAndEmptyByTrim(optionf))
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"选项F"}));
			}
			String answer = rowlist.get(9);//答案
			if(!MyUtil.isNotNullAndEmptyByTrim(answer))
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"答案"}));
			String ndtype=rowlist.get(10);//难度
			if(!MyUtil.isNotNullAndEmptyByTrim(ndtype))
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"难度类型"}));
			String userid=rowlist.get(11);//用户账号
			if(!MyUtil.isNotNullAndEmptyByTrim(userid))
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"用户账号"}));
			String zsdString=rowlist.get(12);//知识点
			if(!MyUtil.isNotNullAndEmptyByTrim(zsdString))
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 912, new Object[]{"知识点"}));
			
			SysuserExample sysuserExample=new SysuserExample();
			Criteria criteria = sysuserExample.createCriteria();
			criteria.andUseridEqualTo(userid);
			List<Sysuser> sysuserList = sysuserMapper.selectByExample(sysuserExample);
			if(sysuserList==null||sysuserList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 323, null));
			
			Sysuser sysuser = sysuserList.get(0);
			//进行校验
			//添加唯一校验
			//.....
			
			//暂时无法录入填空题题目
			if(type==3)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 329, null));
			
			//题目类型必须存在
			DictinfoExample dictinfoExample=new DictinfoExample();
			DictinfoExample.Criteria dictinfoCriteria = dictinfoExample.createCriteria();
			dictinfoCriteria.andDictcodeEqualTo(String.valueOf(type));
			dictinfoCriteria.andTypecodeEqualTo("001");
			List<Dictinfo> dictinfoList = dictinfoMapper.selectByExample(dictinfoExample);
			if(dictinfoList==null||dictinfoList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 324, null));
			
			//添加的课程名必须存在,先根据kcname查询对应的题目信息
			KcExample kcExample=new KcExample();
			KcExample.Criteria kcCriteria = kcExample.createCriteria();
			kcCriteria.andNameEqualTo(kcname);
			List<Kc> kcList = kcMapper.selectByExample(kcExample);
			
			//若不存在该课程抛出异常
			if(kcList==null||kcList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 302, null));
			
			Kc kc = kcList.get(0);
			Long kcuuid=kc.getUuid();
			
			//教师添加题目只能添加自己任课的题目
			TeacherKcExample teacherKcExample=new TeacherKcExample();
			TeacherKcExample.Criteria teacherKcCriteria = teacherKcExample.createCriteria();
			teacherKcCriteria.andKcuuidEqualTo(kcuuid);
			teacherKcCriteria.andTeacheruuidEqualTo(sysuser.getUuid());
			List<TeacherKc> teacherKcList = teacherKcMapper.selectByExample(teacherKcExample);
			if(teacherKcList==null||teacherKcList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 320, null));
	
			//题目类型必须存在
			DictinfoExample dictinfoExample2=new DictinfoExample();
			DictinfoExample.Criteria dictinfoCriteria2 = dictinfoExample2.createCriteria();
			dictinfoCriteria2.andDictcodeEqualTo(String.valueOf(ndtype));
			dictinfoCriteria2.andTypecodeEqualTo("002");
			List<Dictinfo> dictinfoList2 = dictinfoMapper.selectByExample(dictinfoExample);
			if(dictinfoList2==null||dictinfoList2.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 325, null));
			
			//解析知识点
			String[] zsdArr = zsdString.split("-");
			List<String> zsdList=new ArrayList<String>();
			for(String zsd:zsdArr){
				ZsdExample zsdExample=new ZsdExample();
				ZsdExample.Criteria zsdCriteria = zsdExample.createCriteria();
				zsdCriteria.andNameEqualTo(zsd);
				List<Zsd> zsdListTemp = zsdMapper.selectByExample(zsdExample);
				if(zsdListTemp==null||zsdListTemp.size()<1)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 330, null));
				zsdList.add(zsdListTemp.get(0).getUuid());
			}
			
			//不允许添加相同题目内容，选项相同的题目
			if(type==1){
				DxtExample dxtExample=new DxtExample();
				DxtExample.Criteria dxtCriteria = dxtExample.createCriteria();
				dxtCriteria.andContentEqualTo(content);
				dxtCriteria.andOptionaEqualTo(optiona);
				dxtCriteria.andOptionbEqualTo(optionb);
				dxtCriteria.andOptioncEqualTo(optionc);
				dxtCriteria.andOptiondEqualTo(optiond);
				dxtCriteria.andKcuuidEqualTo(kcuuid);
				List<Dxt> dxtList = dxtMapper.selectByExample(dxtExample);
				if(dxtList!=null&&dxtList.size()>0)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 305, null));
			}else if(type==2){
				DxxztExample dxxztExample=new DxxztExample();
				DxxztExample.Criteria dxxztCriteria = dxxztExample.createCriteria();
				dxxztCriteria.andContentEqualTo(content);
				dxxztCriteria.andOptionaEqualTo(optiona);
				dxxztCriteria.andOptionbEqualTo(optionb);
				dxxztCriteria.andOptioncEqualTo(optionc);
				dxxztCriteria.andOptiondEqualTo(optiond);
				dxxztCriteria.andOptioneEqualTo(optione);
				dxxztCriteria.andOptionfEqualTo(optionf);
				dxxztCriteria.andKcuuidEqualTo(kcuuid);
				List<Dxxzt> dxxztList = dxxztMapper.selectByExample(dxxztExample);
				if(dxxztList!=null&&dxxztList.size()>0)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 305, null));
			}
			else if(type==4){
				JdtExample jdtExample=new JdtExample();
				JdtExample.Criteria jdtCriteria = jdtExample.createCriteria();
				jdtCriteria.andContentEqualTo(content);
				jdtCriteria.andKcuuidEqualTo(kcuuid);
				List<Jdt> jdtList = jdtMapper.selectByExample(jdtExample);
				if(jdtList!=null&&jdtList.size()>0)
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 305, null));
			}

			String uuid = UUIDBuild.getUUID();
			if(type==1){
				Dxt dxt=new Dxt();
				dxt.setUuid(uuid);
				dxt.setType(type);
				dxt.setKcuuid(kcuuid);
				dxt.setOptiona(optiona.trim());
				dxt.setOptionb(optionb.trim());
				dxt.setOptionc(optionc.trim());
				dxt.setOptiond(optiond.trim());
				dxt.setContent(content.trim());
				dxt.setAnswer(answer.trim());
				dxt.setNdtype(new Integer(ndtype));
				dxt.setTeacheruuid(sysuser.getUuid());
				dxt.setCreatetime(System.currentTimeMillis());
				dxt.setStatus(2);
				
				//校验调用mapper
				dxtMapper.insert(dxt);
			}else if(type==2){
				Dxxzt dxxzt=new Dxxzt();
				dxxzt.setUuid(uuid);
				dxxzt.setType(type);
				dxxzt.setKcuuid(kcuuid);
				dxxzt.setOptiona(optiona.trim());
				dxxzt.setOptionb(optionb.trim());
				dxxzt.setOptionc(optionc.trim());
				dxxzt.setOptiond(optiond.trim());
				dxxzt.setOptione(optione.trim());
				dxxzt.setOptionf(optionf.trim());
				dxxzt.setContent(content.trim());
				dxxzt.setAnswer(answer.trim());
				dxxzt.setNdtype(new Integer(ndtype));
				dxxzt.setTeacheruuid(sysuser.getUuid());
				dxxzt.setCreatetime(System.currentTimeMillis());
				dxxzt.setStatus(2);
				dxxztMapper.insert(dxxzt);
			}else if(type==4){
				Jdt jdt=new Jdt();
				jdt.setUuid(uuid);
				jdt.setType(type);
				jdt.setKcuuid(kcuuid);
				jdt.setContent(content.trim());
				jdt.setAnswer(answer.trim());
				jdt.setNdtype(new Integer(ndtype));
				jdt.setTeacheruuid(sysuser.getUuid());
				jdt.setCreatetime(System.currentTimeMillis());
				jdt.setStatus(2);
				jdtMapper.insert(jdt);
			}
			//添加题目知识点
			for(String zsdUuid:zsdList){
				TmZsd tmZsd=new TmZsd();
				String tmZsdUuid = UUIDBuild.getUUID();
				tmZsd.setUuid(tmZsdUuid);
				tmZsd.setTmuuid(uuid);
				tmZsd.setType(type);
				tmZsd.setZsduuid(zsdUuid);
				tmZsdMapper.insert(tmZsd);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "导入失败！";
		}	
		return "success";
	}
	
}
