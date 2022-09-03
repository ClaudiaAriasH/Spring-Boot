package com.curso.products.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.curso.products.models.entity.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Long>{

}
