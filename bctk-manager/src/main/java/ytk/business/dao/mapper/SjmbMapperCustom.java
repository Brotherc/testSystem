package ytk.business.dao.mapper;



import ytk.base.pojo.vo.Menu;

public interface SjmbMapperCustom {
	public Menu findMenuByName(String name) throws Exception;
}