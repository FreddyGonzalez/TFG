package com.tfg.notificacion.repository;

import com.tfg.notificacion.domain.Notificacion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Notificacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}
