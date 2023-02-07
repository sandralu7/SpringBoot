package com.bolsaideas.springboot.app.item.models.services;

import java.util.List;

import com.bolsaideas.springboot.app.item.models.Item;
import com.bolsaideas.springboot.app.commons.models.Producto;

public interface ItemService {

	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);
	public Producto save (Producto producto);
	public Producto update (Producto producto, Long id);
	public void delete(Long id);
}
