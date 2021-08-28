package com.tfg.notificacion.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.tfg.notificacion.domain.enumeration.TipoNotificacion;

/**
 * A Notificacion.
 */
@Entity
@Table(name = "notificacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @Column(name = "detalles")
    private String detalles;

    @NotNull
    @Column(name = "fecha_envio", nullable = false)
    private Instant fechaEnvio;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoNotificacion tipo;

    @NotNull
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

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

    public Instant getFecha() {
        return fecha;
    }

    public Notificacion fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getDetalles() {
        return detalles;
    }

    public Notificacion detalles(String detalles) {
        this.detalles = detalles;
        return this;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Instant getFechaEnvio() {
        return fechaEnvio;
    }

    public Notificacion fechaEnvio(Instant fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
        return this;
    }

    public void setFechaEnvio(Instant fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public Notificacion tipo(TipoNotificacion tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoNotificacion tipo) {
        this.tipo = tipo;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public Notificacion clienteId(Long clienteId) {
        this.clienteId = clienteId;
        return this;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public Notificacion productoId(Long productoId) {
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
        if (!(o instanceof Notificacion)) {
            return false;
        }
        return id != null && id.equals(((Notificacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notificacion{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", detalles='" + getDetalles() + "'" +
            ", fechaEnvio='" + getFechaEnvio() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", clienteId=" + getClienteId() +
            ", productoId=" + getProductoId() +
            "}";
    }
}
