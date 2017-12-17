package ytk.business.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ytk.base.pojo.vo.Menu;
import ytk.business.business.SjmbEbo;
import ytk.business.dao.mapper.SjmbMapper;
import ytk.business.dao.mapper.SjmbMapperCustom;
import ytk.business.pojo.po.Sjmb;
import ytk.business.pojo.po.SjmbExample;

public class SjmbEbi implements SjmbEbo{

	@Autowired
	private SjmbMapper sjmbMapper;
	@Autowired
	private SjmbMapperCustom sjmbMapperCustom;
	
	@Override
	public List<Sjmb> findSjmbList() throws Exception {
		return sjmbMapper.selectByExample(new SjmbExample());
	}

	@Override
	public Sjmb findSjmbByUuid(String uuid) throws Exception {
		return sjmbMapper.selectByPrimaryKey(uuid);
	}

	@Override
	public Menu findSjmbMenuBySjmb(Sjmb sjmb) throws Exception {
		Integer dxtcount = sjmb.getDxtcount();
		Integer tktcount = sjmb.getTktcount();
		Integer pdtcount = sjmb.getPdtcount();
		List<Menu> menus=new ArrayList<Menu>();
		Menu menu = new Menu();
		menu.setMenus(menus);
		if(dxtcount!=null&&dxtcount!=0){
			Menu dxtMenu = sjmbMapperCustom.findMenuByName("试卷单选题");
			menus.add(dxtMenu);
		}
		if(tktcount!=null&&tktcount!=0){
			Menu tktMenu = sjmbMapperCustom.findMenuByName("试卷填空题");
			menus.add(tktMenu);
		}
		if(pdtcount!=null&&pdtcount!=0){
			Menu pdtMenu = sjmbMapperCustom.findMenuByName("试卷判断题");
			menus.add(pdtMenu);
		}
		Menu sjSubmitMenu = sjmbMapperCustom.findMenuByName("提交试卷");
		menus.add(sjSubmitMenu);
		return menu;
	}


}
