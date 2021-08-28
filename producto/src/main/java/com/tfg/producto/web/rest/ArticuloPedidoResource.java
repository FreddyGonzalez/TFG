package com.tfg.producto.web.rest;

import com.tfg.producto.domain.ArticuloPedido;
import com.tfg.producto.service.ArticuloPedidoService;
import com.tfg.producto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tfg.producto.domain.ArticuloPedido}.
 */
@RestController
@RequestMapping("/api")
public class ArticuloPedidoResource {

    private final Logger log = LoggerFactory.getLogger(ArticuloPedidoResource.class);

    private static final String ENTITY_NAME = "productoArticuloPedido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticuloPedidoService articuloPedidoService;

    public ArticuloPedidoResource(ArticuloPedidoService articuloPedidoService) {
        this.articuloPedidoService = articuloPedidoService;
    }

    /**
     * {@code POST  /articulo-pedidos} : Create a new articuloPedido.
     *
     * @param articuloPedido the articuloPedido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articuloPedido, or with status {@code 400 (Bad Request)} if the articuloPedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/articulo-pedidos")
    public ResponseEntity<ArticuloPedido> createArticuloPedido(@Valid @RequestBody ArticuloPedido articuloPedido) throws URISyntaxException {
        log.debug("REST request to save ArticuloPedido : {}", articuloPedido);
        if (articuloPedido.getId() != null) {
            throw new BadRequestAlertException("A new articuloPedido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticuloPedido result = articuloPedidoService.save(articuloPedido);
        return ResponseEntity.created(new URI("/api/articulo-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /articulo-pedidos} : Updates an existing articuloPedido.
     *
     * @param articuloPedido the articuloPedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articuloPedido,
     * or with status {@code 400 (Bad Request)} if the articuloPedido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articuloPedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/articulo-pedidos")
    public ResponseEntity<ArticuloPedido> updateArticuloPedido(@Valid @RequestBody ArticuloPedido articuloPedido) throws URISyntaxException {
        log.debug("REST request to update ArticuloPedido : {}", articuloPedido);
        if (articuloPedido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArticuloPedido result = articuloPedidoService.save(articuloPedido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articuloPedido.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /articulo-pedidos} : get all the articuloPedidos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articuloPedidos in body.
     */
    @GetMapping("/articulo-pedidos")
    public ResponseEntity<List<ArticuloPedido>> getAllArticuloPedidos(Pageable pageable) {
        log.debug("REST request to get a page of ArticuloPedidos");
        Page<ArticuloPedido> page = articuloPedidoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /articulo-pedidos/:id} : get the "id" articuloPedido.
     *
     * @param id the id of the articuloPedido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articuloPedido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/articulo-pedidos/{id}")
    public ResponseEntity<ArticuloPedido> getArticuloPedido(@PathVariable Long id) {
        log.debug("REST request to get ArticuloPedido : {}", id);
        Optional<ArticuloPedido> articuloPedido = articuloPedidoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articuloPedido);
    }

    /**
     * {@code DELETE  /articulo-pedidos/:id} : delete the "id" articuloPedido.
     *
     * @param id the id of the articuloPedido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/articulo-pedidos/{id}")
    public ResponseEntity<Void> deleteArticuloPedido(@PathVariable Long id) {
        log.debug("REST request to delete ArticuloPedido : {}", id);
        articuloPedidoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
