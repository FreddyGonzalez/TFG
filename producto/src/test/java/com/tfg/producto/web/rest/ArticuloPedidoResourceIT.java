package com.tfg.producto.web.rest;

import com.tfg.producto.ProductoApp;
import com.tfg.producto.domain.ArticuloPedido;
import com.tfg.producto.domain.Producto;
import com.tfg.producto.domain.Pedido;
import com.tfg.producto.repository.ArticuloPedidoRepository;
import com.tfg.producto.service.ArticuloPedidoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tfg.producto.domain.enumeration.EstadoArticuloPedido;
/**
 * Integration tests for the {@link ArticuloPedidoResource} REST controller.
 */
@SpringBootTest(classes = ProductoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArticuloPedidoResourceIT {

    private static final Integer DEFAULT_CANTIDAD = 0;
    private static final Integer UPDATED_CANTIDAD = 1;

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(1);

    private static final EstadoArticuloPedido DEFAULT_ESTADO = EstadoArticuloPedido.DISPONIBLE;
    private static final EstadoArticuloPedido UPDATED_ESTADO = EstadoArticuloPedido.FUERA_DE_STOCK;

    @Autowired
    private ArticuloPedidoRepository articuloPedidoRepository;

    @Autowired
    private ArticuloPedidoService articuloPedidoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticuloPedidoMockMvc;

    private ArticuloPedido articuloPedido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticuloPedido createEntity(EntityManager em) {
        ArticuloPedido articuloPedido = new ArticuloPedido()
            .cantidad(DEFAULT_CANTIDAD)
            .total(DEFAULT_TOTAL)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        articuloPedido.setProducto(producto);
        // Add required entity
        Pedido pedido;
        if (TestUtil.findAll(em, Pedido.class).isEmpty()) {
            pedido = PedidoResourceIT.createEntity(em);
            em.persist(pedido);
            em.flush();
        } else {
            pedido = TestUtil.findAll(em, Pedido.class).get(0);
        }
        articuloPedido.setPedido(pedido);
        return articuloPedido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticuloPedido createUpdatedEntity(EntityManager em) {
        ArticuloPedido articuloPedido = new ArticuloPedido()
            .cantidad(UPDATED_CANTIDAD)
            .total(UPDATED_TOTAL)
            .estado(UPDATED_ESTADO);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        articuloPedido.setProducto(producto);
        // Add required entity
        Pedido pedido;
        if (TestUtil.findAll(em, Pedido.class).isEmpty()) {
            pedido = PedidoResourceIT.createUpdatedEntity(em);
            em.persist(pedido);
            em.flush();
        } else {
            pedido = TestUtil.findAll(em, Pedido.class).get(0);
        }
        articuloPedido.setPedido(pedido);
        return articuloPedido;
    }

    @BeforeEach
    public void initTest() {
        articuloPedido = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticuloPedido() throws Exception {
        int databaseSizeBeforeCreate = articuloPedidoRepository.findAll().size();
        // Create the ArticuloPedido
        restArticuloPedidoMockMvc.perform(post("/api/articulo-pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articuloPedido)))
            .andExpect(status().isCreated());

        // Validate the ArticuloPedido in the database
        List<ArticuloPedido> articuloPedidoList = articuloPedidoRepository.findAll();
        assertThat(articuloPedidoList).hasSize(databaseSizeBeforeCreate + 1);
        ArticuloPedido testArticuloPedido = articuloPedidoList.get(articuloPedidoList.size() - 1);
        assertThat(testArticuloPedido.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testArticuloPedido.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testArticuloPedido.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createArticuloPedidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articuloPedidoRepository.findAll().size();

        // Create the ArticuloPedido with an existing ID
        articuloPedido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticuloPedidoMockMvc.perform(post("/api/articulo-pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articuloPedido)))
            .andExpect(status().isBadRequest());

        // Validate the ArticuloPedido in the database
        List<ArticuloPedido> articuloPedidoList = articuloPedidoRepository.findAll();
        assertThat(articuloPedidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = articuloPedidoRepository.findAll().size();
        // set the field null
        articuloPedido.setCantidad(null);

        // Create the ArticuloPedido, which fails.


        restArticuloPedidoMockMvc.perform(post("/api/articulo-pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articuloPedido)))
            .andExpect(status().isBadRequest());

        List<ArticuloPedido> articuloPedidoList = articuloPedidoRepository.findAll();
        assertThat(articuloPedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = articuloPedidoRepository.findAll().size();
        // set the field null
        articuloPedido.setTotal(null);

        // Create the ArticuloPedido, which fails.


        restArticuloPedidoMockMvc.perform(post("/api/articulo-pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articuloPedido)))
            .andExpect(status().isBadRequest());

        List<ArticuloPedido> articuloPedidoList = articuloPedidoRepository.findAll();
        assertThat(articuloPedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = articuloPedidoRepository.findAll().size();
        // set the field null
        articuloPedido.setEstado(null);

        // Create the ArticuloPedido, which fails.


        restArticuloPedidoMockMvc.perform(post("/api/articulo-pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articuloPedido)))
            .andExpect(status().isBadRequest());

        List<ArticuloPedido> articuloPedidoList = articuloPedidoRepository.findAll();
        assertThat(articuloPedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArticuloPedidos() throws Exception {
        // Initialize the database
        articuloPedidoRepository.saveAndFlush(articuloPedido);

        // Get all the articuloPedidoList
        restArticuloPedidoMockMvc.perform(get("/api/articulo-pedidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articuloPedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getArticuloPedido() throws Exception {
        // Initialize the database
        articuloPedidoRepository.saveAndFlush(articuloPedido);

        // Get the articuloPedido
        restArticuloPedidoMockMvc.perform(get("/api/articulo-pedidos/{id}", articuloPedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articuloPedido.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingArticuloPedido() throws Exception {
        // Get the articuloPedido
        restArticuloPedidoMockMvc.perform(get("/api/articulo-pedidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticuloPedido() throws Exception {
        // Initialize the database
        articuloPedidoService.save(articuloPedido);

        int databaseSizeBeforeUpdate = articuloPedidoRepository.findAll().size();

        // Update the articuloPedido
        ArticuloPedido updatedArticuloPedido = articuloPedidoRepository.findById(articuloPedido.getId()).get();
        // Disconnect from session so that the updates on updatedArticuloPedido are not directly saved in db
        em.detach(updatedArticuloPedido);
        updatedArticuloPedido
            .cantidad(UPDATED_CANTIDAD)
            .total(UPDATED_TOTAL)
            .estado(UPDATED_ESTADO);

        restArticuloPedidoMockMvc.perform(put("/api/articulo-pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticuloPedido)))
            .andExpect(status().isOk());

        // Validate the ArticuloPedido in the database
        List<ArticuloPedido> articuloPedidoList = articuloPedidoRepository.findAll();
        assertThat(articuloPedidoList).hasSize(databaseSizeBeforeUpdate);
        ArticuloPedido testArticuloPedido = articuloPedidoList.get(articuloPedidoList.size() - 1);
        assertThat(testArticuloPedido.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testArticuloPedido.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testArticuloPedido.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingArticuloPedido() throws Exception {
        int databaseSizeBeforeUpdate = articuloPedidoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticuloPedidoMockMvc.perform(put("/api/articulo-pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articuloPedido)))
            .andExpect(status().isBadRequest());

        // Validate the ArticuloPedido in the database
        List<ArticuloPedido> articuloPedidoList = articuloPedidoRepository.findAll();
        assertThat(articuloPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArticuloPedido() throws Exception {
        // Initialize the database
        articuloPedidoService.save(articuloPedido);

        int databaseSizeBeforeDelete = articuloPedidoRepository.findAll().size();

        // Delete the articuloPedido
        restArticuloPedidoMockMvc.perform(delete("/api/articulo-pedidos/{id}", articuloPedido.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticuloPedido> articuloPedidoList = articuloPedidoRepository.findAll();
        assertThat(articuloPedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
