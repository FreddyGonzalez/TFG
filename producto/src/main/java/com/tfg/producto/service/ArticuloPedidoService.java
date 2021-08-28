package com.tfg.producto.service;

import com.tfg.producto.domain.ArticuloPedido;
import com.tfg.producto.repository.ArticuloPedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ArticuloPedido}.
 */
@Service
@Transactional
public class ArticuloPedidoService {

    private final Logger log = LoggerFactory.getLogger(ArticuloPedidoService.class);

    private final ArticuloPedidoRepository articuloPedidoRepository;

    public ArticuloPedidoService(ArticuloPedidoRepository articuloPedidoRepository) {
        this.articuloPedidoRepository = articuloPedidoRepository;
    }

    /**
     * Save a articuloPedido.
     *
     * @param articuloPedido the entity to save.
     * @return the persisted entity.
     */
    public ArticuloPedido save(ArticuloPedido articuloPedido) {
        log.debug("Request to save ArticuloPedido : {}", articuloPedido);
        return articuloPedidoRepository.save(articuloPedido);
    }

    /**
     * Get all the articuloPedidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArticuloPedido> findAll(Pageable pageable) {
        log.debug("Request to get all ArticuloPedidos");
        return articuloPedidoRepository.findAll(pageable);
    }


    /**
     * Get one articuloPedido by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArticuloPedido> findOne(Long id) {
        log.debug("Request to get ArticuloPedido : {}", id);
        return articuloPedidoRepository.findById(id);
    }

    /**
     * Delete the articuloPedido by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ArticuloPedido : {}", id);
        articuloPedidoRepository.deleteById(id);
    }
}
