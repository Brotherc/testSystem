package ytk.business.action;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ytk.base.business.SystemConfigEbo;
import ytk.base.pojo.po.Dictinfo;
import ytk.base.pojo.po.Zsd;
import ytk.base.pojo.po.Zy;
import ytk.base.process.context.Config;
import ytk.base.process.result.ResultUtil;
import ytk.base.process.result.SubmitResultInfo;
import ytk.business.business.DxtEbo;
import ytk.business.business.DxxztEbo;
import ytk.business.business.JdtEbo;
import ytk.business.business.PdtEbo;
import ytk.business.business.TktEbo;
import ytk.business.business.TmEbo;
import ytk.business.pojo.vo.DxtCustom;
import ytk.business.pojo.vo.DxxztCustom;
import ytk.business.pojo.vo.JdtCustom;
import ytk.business.pojo.vo.PdtCustom;
import ytk.business.pojo.vo.TktCustom;
import ytk.util.UUIDBuild;

@Controller
public class TmAction {
	
	@Autowired 
	private DxtEbo dxtEbo;
	@Autowired
	private DxxztEbo dxxztEbo;
	@Autowired 
	private TktEbo tktEbo;
	@Autowired
	private JdtEbo jdtEbo;
	@Autowired
	private PdtEbo pdtEbo;
	@Autowired
	private TmEbo tmEbo;
	@Autowired
	private SystemConfigEbo systemConfigEbo;
	
	@RequestMapping("/tm/detail")
	public String findTmDeatil(Model model,String uuid,Integer type) throws Exception{
		if(type==1){
			DxtCustom dxtCustom = dxtEbo.findDxtByUuid(uuid);
			List<Zsd> zsdList = dxtCustom.getZsdList();
			List<Zy> zyList = dxtCustom.getZyList();
			model.addAttribute("zsdList", zsdList);
			model.addAttribute("zyList", zyList);
			model.addAttribute("dxtCustom", dxtCustom);
			return "/business/tm/dxtdetail";
		}else if(type==2){
			DxxztCustom dxxztCustom = dxxztEbo.findDxxztByUuid(uuid);
			model.addAttribute("dxxztCustom", dxxztCustom);
			List<Zsd> zsdList = dxxztCustom.getZsdList();
			List<Zy> zyList = dxxztCustom.getZyList();
			model.addAttribute("zsdList", zsdList);
			model.addAttribute("zyList", zyList);
			return "/business/tm/dxxztdetail";
		}else if(type==3){
			TktCustom tktCustom = tktEbo.findTktByUuid(uuid);
			model.addAttribute("tktCustom", tktCustom);
			List<Zsd> zsdList = tktCustom.getZsdList();
			List<Zy> zyList = tktCustom.getZyList();
			model.addAttribute("zsdList", zsdList);
			model.addAttribute("zyList", zyList);
			return "/business/tm/tktdetail";
		}else if(type==4){
			JdtCustom jdtCustom = jdtEbo.findJdtByUuid(uuid);
			model.addAttribute("jdtCustom", jdtCustom);
			List<Zsd> zsdList = jdtCustom.getZsdList();
			List<Zy> zyList = jdtCustom.getZyList();
			model.addAttribute("zsdList", zsdList);
			model.addAttribute("zyList", zyList);
			return "/business/tm/jdtdetail";
		}else if(type==5){
			PdtCustom pdtCustom = pdtEbo.findPdtByUuid(uuid);
			model.addAttribute("pdtCustom", pdtCustom);
			List<Zsd> zsdList = pdtCustom.getZsdList();
			List<Zy> zyList = pdtCustom.getZyList();
			model.addAttribute("zsdList", zsdList);
			model.addAttribute("zyList", zyList);
			return "/business/tm/pdtdetail";			
		}
		return "";
	}
	//获取系题目类型信息(json)
	@RequestMapping("/xitm/typeJsonList")
	public @ResponseBody List<Dictinfo> getXitmTypeJsonList() throws Exception{
		return systemConfigEbo.findDictinfoByTypeCode("001");
	}
	
	@RequestMapping("/tmImport")
	public String toTmImport() throws Exception{
		return "/business/tm/import";
	}
	
	//题目导入
	@RequestMapping("/tm/import")
	public @ResponseBody SubmitResultInfo importTm(
			//写上传的文件
			MultipartFile tmImportFile
			)throws Exception{
		
		//将上传的文件写到磁盘
		String originalFilename  = tmImportFile.getOriginalFilename();
		//写入磁盘的文件
		String importPath=systemConfigEbo.findDictinfoByDictcode("011", "3").getRemark();
		File file = new File(importPath+UUIDBuild.getUUID()+originalFilename.substring(originalFilename.lastIndexOf(".")));
		if(!file.exists()){
			//如果文件目录 不存在则创建
			file.mkdirs();
		}
		
		//将内存中的文件写磁盘
		tmImportFile.transferTo(file);
		//上传文件磁盘上路径 
		String absolutePath = file.getAbsolutePath();
		
		String msg=tmEbo.importTm(absolutePath);
		String[] msgs = msg.split("_");
		System.out.println(msgs[0]+" "+msgs[1]);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 317, new Object[]{
				msgs[0],
				msgs[1]
		}));
	
	}
	
	//获取单选题难度信息(json)
	@RequestMapping("/dxt/ndTypeJsonList")
	public @ResponseBody List<Dictinfo> getDxtNdTypeJsonList() throws Exception{
		return systemConfigEbo.findNdTypeDictinfo();
	}
}
