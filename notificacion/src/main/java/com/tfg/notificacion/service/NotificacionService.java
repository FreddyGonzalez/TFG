package com.tfg.notificacion.service;

import com.tfg.notificacion.domain.Notificacion;
import com.tfg.notificacion.repository.NotificacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Notificacion}.
 */
@Service
@Transactional
public class NotificacionService {

    private final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    /**
     * Save a notificacion.
     *
     * @param notificacion the entity to save.
     * @return the persisted entity.
     */
    public Notificacion save(Notificacion notificacion) {
        log.debug("Request to save Notificacion : {}", notificacion);
        return notificacionRepository.save(notificacion);
    }

    /**
     * Get all the notificacions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Notificacion> findAll() {
        log.debug("Request to get all Notificacions");
        return notificacionRepository.findAll();
    }


    /**
     * Get one notificacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Notificacion> findOne(Long id) {
        log.debug("Request to get Notificacion : {}", id);
        return notificacionRepository.findById(id);
    }

    /**
     * Delete the notificacion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Notificacion : {}", id);
        notificacionRepository.deleteById(id);
    }
}
