package com.curso.items.models.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.curso.items.config.feign.ProductoClienteRest;
import com.curso.items.models.Item;
import com.curso.items.models.service.ItemService;

@Service("itemServiceFeign")
//@Primary //Para que al correr tome la implementación con feign cuando no hay qualifier
public class ItemServiceFeignImpl  implements ItemService{
	
	@Autowired
	private ProductoClienteRest productoClienteRest;

	@Override
	public List<Item> findAll() {
		return productoClienteRest.listar().stream().map(prod -> new Item(prod, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		return new Item(productoClienteRest.detalle(id), cantidad);
	}

}
