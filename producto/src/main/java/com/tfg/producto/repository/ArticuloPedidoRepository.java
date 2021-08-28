package com.tfg.producto.repository;

import com.tfg.producto.domain.ArticuloPedido;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArticuloPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticuloPedidoRepository extends JpaRepository<ArticuloPedido, Long> {
}
