package com.tfg.factura.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.tfg.factura.domain.enumeration.EstadoFactura;

import com.tfg.factura.domain.enumeration.MetodoPago;

/**
 * A Factura.
 */
@Entity
@Table(name = "factura")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_factura", nullable = false)
    private String idFactura;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @Column(name = "detalles")
    private String detalles;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoFactura estado;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @NotNull
    @Column(name = "fecha_pago", nullable = false)
    private Instant fechaPago;

    @NotNull
    @Column(name = "total", precision = 21, scale = 2, nullable = false)
    private BigDecimal total;

    @NotNull
    @Column(name = "id_pedido_producto", nullable = false)
    private Long idPedidoProducto;

    @OneToMany(mappedBy = "factura")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Envio> envios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdFactura() {
        return idFactura;
    }

    public Factura idFactura(String idFactura) {
        this.idFactura = idFactura;
        return this;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Factura fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getDetalles() {
        return detalles;
    }

    public Factura detalles(String detalles) {
        this.detalles = detalles;
        return this;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public EstadoFactura getEstado() {
        return estado;
    }

    public Factura estado(EstadoFactura estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public Factura metodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
        return this;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Instant getFechaPago() {
        return fechaPago;
    }

    public Factura fechaPago(Instant fechaPago) {
        this.fechaPago = fechaPago;
        return this;
    }

    public void setFechaPago(Instant fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Factura total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getIdPedidoProducto() {
        return idPedidoProducto;
    }

    public Factura idPedidoProducto(Long idPedidoProducto) {
        this.idPedidoProducto = idPedidoProducto;
        return this;
    }

    public void setIdPedidoProducto(Long idPedidoProducto) {
        this.idPedidoProducto = idPedidoProducto;
    }

    public Set<Envio> getEnvios() {
        return envios;
    }

    public Factura envios(Set<Envio> envios) {
        this.envios = envios;
        return this;
    }

    public Factura addEnvio(Envio envio) {
        this.envios.add(envio);
        envio.setFactura(this);
        return this;
    }

    public Factura removeEnvio(Envio envio) {
        this.envios.remove(envio);
        envio.setFactura(null);
        return this;
    }

    public void setEnvios(Set<Envio> envios) {
        this.envios = envios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Factura)) {
            return false;
        }
        return id != null && id.equals(((Factura) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Factura{" +
            "id=" + getId() +
            ", idFactura='" + getIdFactura() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", detalles='" + getDetalles() + "'" +
            ", estado='" + getEstado() + "'" +
            ", metodoPago='" + getMetodoPago() + "'" +
            ", fechaPago='" + getFechaPago() + "'" +
            ", total=" + getTotal() +
            ", idPedidoProducto=" + getIdPedidoProducto() +
            "}";
    }
}
