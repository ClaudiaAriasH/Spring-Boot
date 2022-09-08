package com.curso.products.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.products.models.entity.Producto;
import com.curso.products.models.service.impl.ProductoServiceImpl;

@RestController
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private Environment env;

	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private ProductoServiceImpl productoServiceImpl;
	
	@GetMapping("/listar")
	public List<Producto> listar(){
		return productoServiceImpl.findAll().stream().map(producto -> {
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public Producto detalle(@PathVariable Long id){
		Producto producto = productoServiceImpl.findById(id);
		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return producto;
	}
}
