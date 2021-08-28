package com.tfg.factura.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Envio.
 */
@Entity
@Table(name = "envio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Envio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_envio")
    private String idEnvio;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @Column(name = "detalles")
    private String detalles;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "envios", allowSetters = true)
    private Factura factura;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public Envio idEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
        return this;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Envio fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getDetalles() {
        return detalles;
    }

    public Envio detalles(String detalles) {
        this.detalles = detalles;
        return this;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Factura getFactura() {
        return factura;
    }

    public Envio factura(Factura factura) {
        this.factura = factura;
        return this;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Envio)) {
            return false;
        }
        return id != null && id.equals(((Envio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Envio{" +
            "id=" + getId() +
            ", idEnvio='" + getIdEnvio() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", detalles='" + getDetalles() + "'" +
            "}";
    }
}
