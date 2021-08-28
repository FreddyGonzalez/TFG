package com.tfg.comentario.web.rest;

import com.tfg.comentario.ComentarioApp;
import com.tfg.comentario.domain.Comentario;
import com.tfg.comentario.repository.ComentarioRepository;
import com.tfg.comentario.service.ComentarioService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ComentarioResource} REST controller.
 */
@SpringBootTest(classes = ComentarioApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ComentarioResourceIT {

    private static final String DEFAULT_ID_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_ID_COMENTARIO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCTO_ID = 1L;
    private static final Long UPDATED_PRODUCTO_ID = 2L;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComentarioMockMvc;

    private Comentario comentario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comentario createEntity(EntityManager em) {
        Comentario comentario = new Comentario()
            .idComentario(DEFAULT_ID_COMENTARIO)
            .descripcion(DEFAULT_DESCRIPCION)
            .productoId(DEFAULT_PRODUCTO_ID);
        return comentario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comentario createUpdatedEntity(EntityManager em) {
        Comentario comentario = new Comentario()
            .idComentario(UPDATED_ID_COMENTARIO)
            .descripcion(UPDATED_DESCRIPCION)
            .productoId(UPDATED_PRODUCTO_ID);
        return comentario;
    }

    @BeforeEach
    public void initTest() {
        comentario = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentario() throws Exception {
        int databaseSizeBeforeCreate = comentarioRepository.findAll().size();
        // Create the Comentario
        restComentarioMockMvc.perform(post("/api/comentarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentario)))
            .andExpect(status().isCreated());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeCreate + 1);
        Comentario testComentario = comentarioList.get(comentarioList.size() - 1);
        assertThat(testComentario.getIdComentario()).isEqualTo(DEFAULT_ID_COMENTARIO);
        assertThat(testComentario.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testComentario.getProductoId()).isEqualTo(DEFAULT_PRODUCTO_ID);
    }

    @Test
    @Transactional
    public void createComentarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentarioRepository.findAll().size();

        // Create the Comentario with an existing ID
        comentario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentarioMockMvc.perform(post("/api/comentarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentario)))
            .andExpect(status().isBadRequest());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdComentarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = comentarioRepository.findAll().size();
        // set the field null
        comentario.setIdComentario(null);

        // Create the Comentario, which fails.


        restComentarioMockMvc.perform(post("/api/comentarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentario)))
            .andExpect(status().isBadRequest());

        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = comentarioRepository.findAll().size();
        // set the field null
        comentario.setDescripcion(null);

        // Create the Comentario, which fails.


        restComentarioMockMvc.perform(post("/api/comentarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentario)))
            .andExpect(status().isBadRequest());

        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductoIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = comentarioRepository.findAll().size();
        // set the field null
        comentario.setProductoId(null);

        // Create the Comentario, which fails.


        restComentarioMockMvc.perform(post("/api/comentarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentario)))
            .andExpect(status().isBadRequest());

        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComentarios() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        // Get all the comentarioList
        restComentarioMockMvc.perform(get("/api/comentarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentario.getId().intValue())))
            .andExpect(jsonPath("$.[*].idComentario").value(hasItem(DEFAULT_ID_COMENTARIO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].productoId").value(hasItem(DEFAULT_PRODUCTO_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getComentario() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        // Get the comentario
        restComentarioMockMvc.perform(get("/api/comentarios/{id}", comentario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comentario.getId().intValue()))
            .andExpect(jsonPath("$.idComentario").value(DEFAULT_ID_COMENTARIO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.productoId").value(DEFAULT_PRODUCTO_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingComentario() throws Exception {
        // Get the comentario
        restComentarioMockMvc.perform(get("/api/comentarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentario() throws Exception {
        // Initialize the database
        comentarioService.save(comentario);

        int databaseSizeBeforeUpdate = comentarioRepository.findAll().size();

        // Update the comentario
        Comentario updatedComentario = comentarioRepository.findById(comentario.getId()).get();
        // Disconnect from session so that the updates on updatedComentario are not directly saved in db
        em.detach(updatedComentario);
        updatedComentario
            .idComentario(UPDATED_ID_COMENTARIO)
            .descripcion(UPDATED_DESCRIPCION)
            .productoId(UPDATED_PRODUCTO_ID);

        restComentarioMockMvc.perform(put("/api/comentarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedComentario)))
            .andExpect(status().isOk());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeUpdate);
        Comentario testComentario = comentarioList.get(comentarioList.size() - 1);
        assertThat(testComentario.getIdComentario()).isEqualTo(UPDATED_ID_COMENTARIO);
        assertThat(testComentario.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testComentario.getProductoId()).isEqualTo(UPDATED_PRODUCTO_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingComentario() throws Exception {
        int databaseSizeBeforeUpdate = comentarioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComentarioMockMvc.perform(put("/api/comentarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentario)))
            .andExpect(status().isBadRequest());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComentario() throws Exception {
        // Initialize the database
        comentarioService.save(comentario);

        int databaseSizeBeforeDelete = comentarioRepository.findAll().size();

        // Delete the comentario
        restComentarioMockMvc.perform(delete("/api/comentarios/{id}", comentario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
