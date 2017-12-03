package ytk.business.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.dao.mapper.ClassMapper;
import ytk.base.dao.mapper.DictinfoMapper;
import ytk.base.dao.mapper.NjMapper;
import ytk.base.dao.mapper.SysuserMapper;
import ytk.base.dao.mapper.XiMapper;
import ytk.base.dao.mapper.ZyMapper;
import ytk.base.pojo.po.Class;
import ytk.base.pojo.po.ClassExample;
import ytk.base.pojo.po.Nj;
import ytk.base.pojo.po.NjExample;
import ytk.base.pojo.po.Sysuser;
import ytk.base.pojo.po.SysuserExample;
import ytk.base.pojo.po.Xi;
import ytk.base.pojo.po.XiExample;
import ytk.base.pojo.po.Zy;
import ytk.base.pojo.po.ZyExample;
import ytk.base.pojo.po.SysuserExample.Criteria;
import ytk.base.process.context.Config;
import ytk.base.process.result.ExceptionResultInfo;
import ytk.base.process.result.ResultInfo;
import ytk.base.process.result.ResultUtil;
import ytk.util.HxlsOptRowsInterface;
import ytk.util.MD5;

/**
 * 测试导入数据接口
 * @author Thinkpad
 *
 */
public class HxlsOptRowsInterfaceKsglStudentImpl implements HxlsOptRowsInterface {
	
	@Autowired
	private SysuserMapper sysuserMapper;
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
	
	@Override
	public String optRows(int sheetIndex, int curRow, List<String> rowlist)
			throws Exception {
		try {
			//得到导入的数据
			//rowlist数据 是一行数据，按照模版解析
			String userid=rowlist.get(0);
			String xiname=rowlist.get(1);
			String zyname=rowlist.get(2);
			String njname=rowlist.get(3);
			String classname=rowlist.get(4);
			
			//如果学号重复，则跳过不导入，否则添加考试学生
			SysuserExample sysuserExample=new SysuserExample();
			Criteria sysuserCriteria = sysuserExample.createCriteria();
			sysuserCriteria.andUseridEqualTo(userid);
			List<Sysuser> sysuserList = sysuserMapper.selectByExample(sysuserExample);
			if(sysuserList!=null&&sysuserList.size()>0)
				ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 1311, null));
			System.out.println("考试学生导入");
			
			//添加的系信息必须存在
			XiExample xiExample=new XiExample();
			XiExample.Criteria xiCriteria = xiExample.createCriteria();
			xiCriteria.andNameEqualTo(xiname);
			
			List<Xi> xiList = xiMapper.selectByExample(xiExample);
			if(xiList==null||xiList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1313,new Object[]{userid} ));
			Xi xi = xiList.get(0);
			
			//添加的专业信息必须存在
			ZyExample zyExample=new ZyExample();
			ZyExample.Criteria zyCriteria = zyExample.createCriteria();
			zyCriteria.andNameEqualTo(zyname);
			List<Zy> zyList = zyMapper.selectByExample(zyExample);
			if(zyList==null||zyList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1314,new Object[]{userid} ));
			Zy zy = zyList.get(0);
			
			//添加的年级信息必须存在
			NjExample njExample=new NjExample();
			NjExample.Criteria njCriteria = njExample.createCriteria();
			njCriteria.andNjnaneEqualTo(njname);
			njCriteria.andStatusEqualTo(1);
			List<Nj> njList = njMapper.selectByExample(njExample);
			if(njList==null||njList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1315,new Object[]{userid} ));
			Nj nj = njList.get(0);
			
			//添加的班级信息必须存在
			ClassExample classExample=new ClassExample();
			ClassExample.Criteria classCriteria = classExample.createCriteria();
			classCriteria.andClassnameEqualTo(classname);
			classCriteria.andZyuuidEqualTo(zy.getUuid());
			classCriteria.andNjuuidEqualTo(nj.getUuid());
			List<Class> classList = classMapper.selectByExample(classExample);
			if(classList==null||classList.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1316, new Object[]{userid}));
			Class classCustom = classList.get(0);

			//添加的班级信息必须在相应系中（即专业在相应系中）
			zyCriteria.andXuuidEqualTo(xi.getUuid());
			List<Zy> zyList2 = zyMapper.selectByExample(zyExample);
			if(zyList2==null||zyList2.size()<1)
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 1317, new Object[]{userid}));
			
			Sysuser sysuser=new Sysuser();
			sysuser.setUserid(userid);
			//补全密码（初始化123456）
			//加密
			String md5Pwd=new MD5().getMD5ofStr("123456");
			sysuser.setPwd(md5Pwd);
			//补全用户名（初始化学号）
			sysuser.setUsername(userid);
			//补全分组（学生）
			sysuser.setGroupid("3");
			//补全状态（正常）
			sysuser.setUserstate("1");
			//补全系uuid
			sysuser.setXuuid(xi.getUuid());
			//补全班级信息
			sysuser.setClassuuid(classCustom.getUuid());
			sysuserMapper.insert(sysuser);
			
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
