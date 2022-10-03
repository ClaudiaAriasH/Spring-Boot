package com.curso.items.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curso.items.models.Item;
import com.curso.items.models.Producto;
import com.curso.items.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@CrossOrigin("*")
public class ItemController {

	private final Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	@Qualifier("itemServiceFeign")
	// @Qualifier("serviceRestTemplate")
	private ItemService itemService;

	@GetMapping("/listar")
	public List<Item> listar(@RequestParam(name = "nombre", required = false) String nombre,
			@RequestHeader(name = "token-request", required = false) String token) {
		logger.info("RequestParameter: " + nombre);
		logger.info("RequestHeader: " + token);
		return itemService.findAll();
	}

	@HystrixCommand(fallbackMethod = "metosdoAlternativo")
	@GetMapping("/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findById(id, cantidad);
	}

	public Item metodoAlternativo(Long id, Integer cantidad) {
		Item item = new Item();
		Producto producto = new Producto();
		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setNombre("Camara sony");
		producto.setPrecio(500.00);
		item.setProducto(producto);
		return item;
	}
}
