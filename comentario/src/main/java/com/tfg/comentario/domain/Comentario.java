package com.tfg.comentario.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Comentario.
 */
@Entity
@Table(name = "comentario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_comentario", nullable = false)
    private String idComentario;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdComentario() {
        return idComentario;
    }

    public Comentario idComentario(String idComentario) {
        this.idComentario = idComentario;
        return this;
    }

    public void setIdComentario(String idComentario) {
        this.idComentario = idComentario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Comentario descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getProductoId() {
        return productoId;
    }

    public Comentario productoId(Long productoId) {
        this.productoId = productoId;
        return this;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comentario)) {
            return false;
        }
        return id != null && id.equals(((Comentario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comentario{" +
            "id=" + getId() +
            ", idComentario='" + getIdComentario() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", productoId=" + getProductoId() +
            "}";
    }
}
