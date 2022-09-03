package com.curso.items.config.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.curso.items.models.Producto;


//@FeignClient(name = "products-api", url = "localhost:8001") sin ribbon
@FeignClient(name = "products-api")
public interface ProductoClienteRest {
	
	@GetMapping("/productos/listar")
	public List<Producto> listar();
	
	@GetMapping("/productos/{id}")
	public Producto detalle(@PathVariable(value="id") Long id);
}
