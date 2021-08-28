package com.tfg.producto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tfg.producto.domain.enumeration.EstadoArticuloPedido;

/**
 * A ArticuloPedido.
 */
@Entity
@Table(name = "articulo_pedido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ArticuloPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total", precision = 21, scale = 2, nullable = false)
    private BigDecimal total;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoArticuloPedido estado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "articuloPedidos", allowSetters = true)
    private Producto producto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "articuloPedidos", allowSetters = true)
    private Pedido pedido;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public ArticuloPedido cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public ArticuloPedido total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public EstadoArticuloPedido getEstado() {
        return estado;
    }

    public ArticuloPedido estado(EstadoArticuloPedido estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoArticuloPedido estado) {
        this.estado = estado;
    }

    public Producto getProducto() {
        return producto;
    }

    public ArticuloPedido producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public ArticuloPedido pedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticuloPedido)) {
            return false;
        }
        return id != null && id.equals(((ArticuloPedido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticuloPedido{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", total=" + getTotal() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
