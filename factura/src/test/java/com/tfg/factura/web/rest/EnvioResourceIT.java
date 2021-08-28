package com.tfg.factura.web.rest;

import com.tfg.factura.FacturaApp;
import com.tfg.factura.domain.Envio;
import com.tfg.factura.domain.Factura;
import com.tfg.factura.repository.EnvioRepository;
import com.tfg.factura.service.EnvioService;

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

/**
 * Integration tests for the {@link EnvioResource} REST controller.
 */
@SpringBootTest(classes = FacturaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnvioResourceIT {

    private static final String DEFAULT_ID_ENVIO = "AAAAAAAAAA";
    private static final String UPDATED_ID_ENVIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DETALLES = "AAAAAAAAAA";
    private static final String UPDATED_DETALLES = "BBBBBBBBBB";

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private EnvioService envioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnvioMockMvc;

    private Envio envio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Envio createEntity(EntityManager em) {
        Envio envio = new Envio()
            .idEnvio(DEFAULT_ID_ENVIO)
            .fecha(DEFAULT_FECHA)
            .detalles(DEFAULT_DETALLES);
        // Add required entity
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            factura = FacturaResourceIT.createEntity(em);
            em.persist(factura);
            em.flush();
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        envio.setFactura(factura);
        return envio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Envio createUpdatedEntity(EntityManager em) {
        Envio envio = new Envio()
            .idEnvio(UPDATED_ID_ENVIO)
            .fecha(UPDATED_FECHA)
            .detalles(UPDATED_DETALLES);
        // Add required entity
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            factura = FacturaResourceIT.createUpdatedEntity(em);
            em.persist(factura);
            em.flush();
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        envio.setFactura(factura);
        return envio;
    }

    @BeforeEach
    public void initTest() {
        envio = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnvio() throws Exception {
        int databaseSizeBeforeCreate = envioRepository.findAll().size();
        // Create the Envio
        restEnvioMockMvc.perform(post("/api/envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envio)))
            .andExpect(status().isCreated());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeCreate + 1);
        Envio testEnvio = envioList.get(envioList.size() - 1);
        assertThat(testEnvio.getIdEnvio()).isEqualTo(DEFAULT_ID_ENVIO);
        assertThat(testEnvio.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEnvio.getDetalles()).isEqualTo(DEFAULT_DETALLES);
    }

    @Test
    @Transactional
    public void createEnvioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = envioRepository.findAll().size();

        // Create the Envio with an existing ID
        envio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvioMockMvc.perform(post("/api/envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envio)))
            .andExpect(status().isBadRequest());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = envioRepository.findAll().size();
        // set the field null
        envio.setFecha(null);

        // Create the Envio, which fails.


        restEnvioMockMvc.perform(post("/api/envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envio)))
            .andExpect(status().isBadRequest());

        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnvios() throws Exception {
        // Initialize the database
        envioRepository.saveAndFlush(envio);

        // Get all the envioList
        restEnvioMockMvc.perform(get("/api/envios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envio.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEnvio").value(hasItem(DEFAULT_ID_ENVIO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].detalles").value(hasItem(DEFAULT_DETALLES)));
    }
    
    @Test
    @Transactional
    public void getEnvio() throws Exception {
        // Initialize the database
        envioRepository.saveAndFlush(envio);

        // Get the envio
        restEnvioMockMvc.perform(get("/api/envios/{id}", envio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(envio.getId().intValue()))
            .andExpect(jsonPath("$.idEnvio").value(DEFAULT_ID_ENVIO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.detalles").value(DEFAULT_DETALLES));
    }
    @Test
    @Transactional
    public void getNonExistingEnvio() throws Exception {
        // Get the envio
        restEnvioMockMvc.perform(get("/api/envios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvio() throws Exception {
        // Initialize the database
        envioService.save(envio);

        int databaseSizeBeforeUpdate = envioRepository.findAll().size();

        // Update the envio
        Envio updatedEnvio = envioRepository.findById(envio.getId()).get();
        // Disconnect from session so that the updates on updatedEnvio are not directly saved in db
        em.detach(updatedEnvio);
        updatedEnvio
            .idEnvio(UPDATED_ID_ENVIO)
            .fecha(UPDATED_FECHA)
            .detalles(UPDATED_DETALLES);

        restEnvioMockMvc.perform(put("/api/envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnvio)))
            .andExpect(status().isOk());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeUpdate);
        Envio testEnvio = envioList.get(envioList.size() - 1);
        assertThat(testEnvio.getIdEnvio()).isEqualTo(UPDATED_ID_ENVIO);
        assertThat(testEnvio.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEnvio.getDetalles()).isEqualTo(UPDATED_DETALLES);
    }

    @Test
    @Transactional
    public void updateNonExistingEnvio() throws Exception {
        int databaseSizeBeforeUpdate = envioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvioMockMvc.perform(put("/api/envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envio)))
            .andExpect(status().isBadRequest());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnvio() throws Exception {
        // Initialize the database
        envioService.save(envio);

        int databaseSizeBeforeDelete = envioRepository.findAll().size();

        // Delete the envio
        restEnvioMockMvc.perform(delete("/api/envios/{id}", envio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
