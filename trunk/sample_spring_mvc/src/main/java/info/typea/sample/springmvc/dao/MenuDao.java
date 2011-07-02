package info.typea.sample.springmvc.dao;

import info.typea.sample.springmvc.entity.Menu;

import java.util.List;

public interface MenuDao {
	public List<Menu> findAll();
}
