package com.tfg.notificacion.web.rest;

import com.tfg.notificacion.NotificacionApp;
import com.tfg.notificacion.domain.Notificacion;
import com.tfg.notificacion.repository.NotificacionRepository;
import com.tfg.notificacion.service.NotificacionService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tfg.notificacion.domain.enumeration.TipoNotificacion;
/**
 * Integration tests for the {@link NotificacionResource} REST controller.
 */
@SpringBootTest(classes = NotificacionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NotificacionResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DETALLES = "AAAAAAAAAA";
    private static final String UPDATED_DETALLES = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_ENVIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ENVIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final TipoNotificacion DEFAULT_TIPO = TipoNotificacion.EMAIL;
    private static final TipoNotificacion UPDATED_TIPO = TipoNotificacion.SMS;

    private static final Long DEFAULT_CLIENTE_ID = 1L;
    private static final Long UPDATED_CLIENTE_ID = 2L;

    private static final Long DEFAULT_PRODUCTO_ID = 1L;
    private static final Long UPDATED_PRODUCTO_ID = 2L;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificacionMockMvc;

    private Notificacion notificacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notificacion createEntity(EntityManager em) {
        Notificacion notificacion = new Notificacion()
            .fecha(DEFAULT_FECHA)
            .detalles(DEFAULT_DETALLES)
            .fechaEnvio(DEFAULT_FECHA_ENVIO)
            .tipo(DEFAULT_TIPO)
            .clienteId(DEFAULT_CLIENTE_ID)
            .productoId(DEFAULT_PRODUCTO_ID);
        return notificacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notificacion createUpdatedEntity(EntityManager em) {
        Notificacion notificacion = new Notificacion()
            .fecha(UPDATED_FECHA)
            .detalles(UPDATED_DETALLES)
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .tipo(UPDATED_TIPO)
            .clienteId(UPDATED_CLIENTE_ID)
            .productoId(UPDATED_PRODUCTO_ID);
        return notificacion;
    }

    @BeforeEach
    public void initTest() {
        notificacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotificacion() throws Exception {
        int databaseSizeBeforeCreate = notificacionRepository.findAll().size();
        // Create the Notificacion
        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacion)))
            .andExpect(status().isCreated());

        // Validate the Notificacion in the database
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeCreate + 1);
        Notificacion testNotificacion = notificacionList.get(notificacionList.size() - 1);
        assertThat(testNotificacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testNotificacion.getDetalles()).isEqualTo(DEFAULT_DETALLES);
        assertThat(testNotificacion.getFechaEnvio()).isEqualTo(DEFAULT_FECHA_ENVIO);
        assertThat(testNotificacion.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testNotificacion.getClienteId()).isEqualTo(DEFAULT_CLIENTE_ID);
        assertThat(testNotificacion.getProductoId()).isEqualTo(DEFAULT_PRODUCTO_ID);
    }

    @Test
    @Transactional
    public void createNotificacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificacionRepository.findAll().size();

        // Create the Notificacion with an existing ID
        notificacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacion)))
            .andExpect(status().isBadRequest());

        // Validate the Notificacion in the database
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionRepository.findAll().size();
        // set the field null
        notificacion.setFecha(null);

        // Create the Notificacion, which fails.


        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacion)))
            .andExpect(status().isBadRequest());

        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaEnvioIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionRepository.findAll().size();
        // set the field null
        notificacion.setFechaEnvio(null);

        // Create the Notificacion, which fails.


        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacion)))
            .andExpect(status().isBadRequest());

        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionRepository.findAll().size();
        // set the field null
        notificacion.setTipo(null);

        // Create the Notificacion, which fails.


        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacion)))
            .andExpect(status().isBadRequest());

        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClienteIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionRepository.findAll().size();
        // set the field null
        notificacion.setClienteId(null);

        // Create the Notificacion, which fails.


        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacion)))
            .andExpect(status().isBadRequest());

        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductoIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionRepository.findAll().size();
        // set the field null
        notificacion.setProductoId(null);

        // Create the Notificacion, which fails.


        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacion)))
            .andExpect(status().isBadRequest());

        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotificacions() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList
        restNotificacionMockMvc.perform(get("/api/notificacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].detalles").value(hasItem(DEFAULT_DETALLES)))
            .andExpect(jsonPath("$.[*].fechaEnvio").value(hasItem(DEFAULT_FECHA_ENVIO.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].clienteId").value(hasItem(DEFAULT_CLIENTE_ID.intValue())))
            .andExpect(jsonPath("$.[*].productoId").value(hasItem(DEFAULT_PRODUCTO_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getNotificacion() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get the notificacion
        restNotificacionMockMvc.perform(get("/api/notificacions/{id}", notificacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificacion.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.detalles").value(DEFAULT_DETALLES))
            .andExpect(jsonPath("$.fechaEnvio").value(DEFAULT_FECHA_ENVIO.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.clienteId").value(DEFAULT_CLIENTE_ID.intValue()))
            .andExpect(jsonPath("$.productoId").value(DEFAULT_PRODUCTO_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingNotificacion() throws Exception {
        // Get the notificacion
        restNotificacionMockMvc.perform(get("/api/notificacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificacion() throws Exception {
        // Initialize the database
        notificacionService.save(notificacion);

        int databaseSizeBeforeUpdate = notificacionRepository.findAll().size();

        // Update the notificacion
        Notificacion updatedNotificacion = notificacionRepository.findById(notificacion.getId()).get();
        // Disconnect from session so that the updates on updatedNotificacion are not directly saved in db
        em.detach(updatedNotificacion);
        updatedNotificacion
            .fecha(UPDATED_FECHA)
            .detalles(UPDATED_DETALLES)
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .tipo(UPDATED_TIPO)
            .clienteId(UPDATED_CLIENTE_ID)
            .productoId(UPDATED_PRODUCTO_ID);

        restNotificacionMockMvc.perform(put("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotificacion)))
            .andExpect(status().isOk());

        // Validate the Notificacion in the database
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeUpdate);
        Notificacion testNotificacion = notificacionList.get(notificacionList.size() - 1);
        assertThat(testNotificacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testNotificacion.getDetalles()).isEqualTo(UPDATED_DETALLES);
        assertThat(testNotificacion.getFechaEnvio()).isEqualTo(UPDATED_FECHA_ENVIO);
        assertThat(testNotificacion.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testNotificacion.getClienteId()).isEqualTo(UPDATED_CLIENTE_ID);
        assertThat(testNotificacion.getProductoId()).isEqualTo(UPDATED_PRODUCTO_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingNotificacion() throws Exception {
        int databaseSizeBeforeUpdate = notificacionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificacionMockMvc.perform(put("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacion)))
            .andExpect(status().isBadRequest());

        // Validate the Notificacion in the database
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotificacion() throws Exception {
        // Initialize the database
        notificacionService.save(notificacion);

        int databaseSizeBeforeDelete = notificacionRepository.findAll().size();

        // Delete the notificacion
        restNotificacionMockMvc.perform(delete("/api/notificacions/{id}", notificacion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
