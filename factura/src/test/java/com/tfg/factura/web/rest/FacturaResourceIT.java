package com.tfg.factura.web.rest;

import com.tfg.factura.FacturaApp;
import com.tfg.factura.domain.Factura;
import com.tfg.factura.repository.FacturaRepository;
import com.tfg.factura.service.FacturaService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tfg.factura.domain.enumeration.EstadoFactura;
import com.tfg.factura.domain.enumeration.MetodoPago;
/**
 * Integration tests for the {@link FacturaResource} REST controller.
 */
@SpringBootTest(classes = FacturaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FacturaResourceIT {

    private static final String DEFAULT_ID_FACTURA = "AAAAAAAAAA";
    private static final String UPDATED_ID_FACTURA = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DETALLES = "AAAAAAAAAA";
    private static final String UPDATED_DETALLES = "BBBBBBBBBB";

    private static final EstadoFactura DEFAULT_ESTADO = EstadoFactura.PAGADO;
    private static final EstadoFactura UPDATED_ESTADO = EstadoFactura.EMITIDO;

    private static final MetodoPago DEFAULT_METODO_PAGO = MetodoPago.TARJETA_CREDITO;
    private static final MetodoPago UPDATED_METODO_PAGO = MetodoPago.PAYPAL;

    private static final Instant DEFAULT_FECHA_PAGO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_PAGO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    private static final Long DEFAULT_ID_PEDIDO_PRODUCTO = 1L;
    private static final Long UPDATED_ID_PEDIDO_PRODUCTO = 2L;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacturaMockMvc;

    private Factura factura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factura createEntity(EntityManager em) {
        Factura factura = new Factura()
            .idFactura(DEFAULT_ID_FACTURA)
            .fecha(DEFAULT_FECHA)
            .detalles(DEFAULT_DETALLES)
            .estado(DEFAULT_ESTADO)
            .metodoPago(DEFAULT_METODO_PAGO)
            .fechaPago(DEFAULT_FECHA_PAGO)
            .total(DEFAULT_TOTAL)
            .idPedidoProducto(DEFAULT_ID_PEDIDO_PRODUCTO);
        return factura;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factura createUpdatedEntity(EntityManager em) {
        Factura factura = new Factura()
            .idFactura(UPDATED_ID_FACTURA)
            .fecha(UPDATED_FECHA)
            .detalles(UPDATED_DETALLES)
            .estado(UPDATED_ESTADO)
            .metodoPago(UPDATED_METODO_PAGO)
            .fechaPago(UPDATED_FECHA_PAGO)
            .total(UPDATED_TOTAL)
            .idPedidoProducto(UPDATED_ID_PEDIDO_PRODUCTO);
        return factura;
    }

    @BeforeEach
    public void initTest() {
        factura = createEntity(em);
    }

    @Test
    @Transactional
    public void createFactura() throws Exception {
        int databaseSizeBeforeCreate = facturaRepository.findAll().size();
        // Create the Factura
        restFacturaMockMvc.perform(post("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isCreated());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeCreate + 1);
        Factura testFactura = facturaList.get(facturaList.size() - 1);
        assertThat(testFactura.getIdFactura()).isEqualTo(DEFAULT_ID_FACTURA);
        assertThat(testFactura.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testFactura.getDetalles()).isEqualTo(DEFAULT_DETALLES);
        assertThat(testFactura.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testFactura.getMetodoPago()).isEqualTo(DEFAULT_METODO_PAGO);
        assertThat(testFactura.getFechaPago()).isEqualTo(DEFAULT_FECHA_PAGO);
        assertThat(testFactura.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testFactura.getIdPedidoProducto()).isEqualTo(DEFAULT_ID_PEDIDO_PRODUCTO);
    }

    @Test
    @Transactional
    public void createFacturaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facturaRepository.findAll().size();

        // Create the Factura with an existing ID
        factura.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacturaMockMvc.perform(post("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isBadRequest());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdFacturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setIdFactura(null);

        // Create the Factura, which fails.


        restFacturaMockMvc.perform(post("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setFecha(null);

        // Create the Factura, which fails.


        restFacturaMockMvc.perform(post("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setEstado(null);

        // Create the Factura, which fails.


        restFacturaMockMvc.perform(post("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMetodoPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setMetodoPago(null);

        // Create the Factura, which fails.


        restFacturaMockMvc.perform(post("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setFechaPago(null);

        // Create the Factura, which fails.


        restFacturaMockMvc.perform(post("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTotal(null);

        // Create the Factura, which fails.


        restFacturaMockMvc.perform(post("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdPedidoProductoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setIdPedidoProducto(null);

        // Create the Factura, which fails.


        restFacturaMockMvc.perform(post("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacturas() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList
        restFacturaMockMvc.perform(get("/api/facturas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factura.getId().intValue())))
            .andExpect(jsonPath("$.[*].idFactura").value(hasItem(DEFAULT_ID_FACTURA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].detalles").value(hasItem(DEFAULT_DETALLES)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].metodoPago").value(hasItem(DEFAULT_METODO_PAGO.toString())))
            .andExpect(jsonPath("$.[*].fechaPago").value(hasItem(DEFAULT_FECHA_PAGO.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].idPedidoProducto").value(hasItem(DEFAULT_ID_PEDIDO_PRODUCTO.intValue())));
    }
    
    @Test
    @Transactional
    public void getFactura() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get the factura
        restFacturaMockMvc.perform(get("/api/facturas/{id}", factura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factura.getId().intValue()))
            .andExpect(jsonPath("$.idFactura").value(DEFAULT_ID_FACTURA))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.detalles").value(DEFAULT_DETALLES))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.metodoPago").value(DEFAULT_METODO_PAGO.toString()))
            .andExpect(jsonPath("$.fechaPago").value(DEFAULT_FECHA_PAGO.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()))
            .andExpect(jsonPath("$.idPedidoProducto").value(DEFAULT_ID_PEDIDO_PRODUCTO.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFactura() throws Exception {
        // Get the factura
        restFacturaMockMvc.perform(get("/api/facturas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFactura() throws Exception {
        // Initialize the database
        facturaService.save(factura);

        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();

        // Update the factura
        Factura updatedFactura = facturaRepository.findById(factura.getId()).get();
        // Disconnect from session so that the updates on updatedFactura are not directly saved in db
        em.detach(updatedFactura);
        updatedFactura
            .idFactura(UPDATED_ID_FACTURA)
            .fecha(UPDATED_FECHA)
            .detalles(UPDATED_DETALLES)
            .estado(UPDATED_ESTADO)
            .metodoPago(UPDATED_METODO_PAGO)
            .fechaPago(UPDATED_FECHA_PAGO)
            .total(UPDATED_TOTAL)
            .idPedidoProducto(UPDATED_ID_PEDIDO_PRODUCTO);

        restFacturaMockMvc.perform(put("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFactura)))
            .andExpect(status().isOk());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
        Factura testFactura = facturaList.get(facturaList.size() - 1);
        assertThat(testFactura.getIdFactura()).isEqualTo(UPDATED_ID_FACTURA);
        assertThat(testFactura.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFactura.getDetalles()).isEqualTo(UPDATED_DETALLES);
        assertThat(testFactura.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testFactura.getMetodoPago()).isEqualTo(UPDATED_METODO_PAGO);
        assertThat(testFactura.getFechaPago()).isEqualTo(UPDATED_FECHA_PAGO);
        assertThat(testFactura.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testFactura.getIdPedidoProducto()).isEqualTo(UPDATED_ID_PEDIDO_PRODUCTO);
    }

    @Test
    @Transactional
    public void updateNonExistingFactura() throws Exception {
        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacturaMockMvc.perform(put("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isBadRequest());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFactura() throws Exception {
        // Initialize the database
        facturaService.save(factura);

        int databaseSizeBeforeDelete = facturaRepository.findAll().size();

        // Delete the factura
        restFacturaMockMvc.perform(delete("/api/facturas/{id}", factura.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
