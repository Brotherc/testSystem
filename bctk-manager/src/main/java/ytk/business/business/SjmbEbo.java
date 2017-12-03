package ytk.business.business;

import java.util.List;
import ytk.base.pojo.vo.Menu;
import ytk.business.pojo.po.Sjmb;

public interface SjmbEbo {
	//查询所有试卷模板信息
	public List<Sjmb> findSjmbList() throws Exception;
	
	//根据uuid查询试卷模板信息
	public Sjmb findSjmbByUuid(String uuid) throws Exception;
	
	//根据试卷模板加载试卷菜单
	public Menu findSjmbMenuBySjmb(Sjmb sjmb) throws Exception;
}
