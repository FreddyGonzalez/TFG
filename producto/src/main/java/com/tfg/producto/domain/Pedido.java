package com.tfg.producto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.tfg.producto.domain.enumeration.EstadoPedido;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_pedido", nullable = false)
    private String idPedido;

    @NotNull
    @Column(name = "fecha_pedido", nullable = false)
    private Instant fechaPedido;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPedido estado;

    @OneToMany(mappedBy = "pedido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ArticuloPedido> articuloPedidos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public Pedido idPedido(String idPedido) {
        this.idPedido = idPedido;
        return this;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public Instant getFechaPedido() {
        return fechaPedido;
    }

    public Pedido fechaPedido(Instant fechaPedido) {
        this.fechaPedido = fechaPedido;
        return this;
    }

    public void setFechaPedido(Instant fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public Pedido estado(EstadoPedido estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Set<ArticuloPedido> getArticuloPedidos() {
        return articuloPedidos;
    }

    public Pedido articuloPedidos(Set<ArticuloPedido> articuloPedidos) {
        this.articuloPedidos = articuloPedidos;
        return this;
    }

    public Pedido addArticuloPedido(ArticuloPedido articuloPedido) {
        this.articuloPedidos.add(articuloPedido);
        articuloPedido.setPedido(this);
        return this;
    }

    public Pedido removeArticuloPedido(ArticuloPedido articuloPedido) {
        this.articuloPedidos.remove(articuloPedido);
        articuloPedido.setPedido(null);
        return this;
    }

    public void setArticuloPedidos(Set<ArticuloPedido> articuloPedidos) {
        this.articuloPedidos = articuloPedidos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        return id != null && id.equals(((Pedido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", idPedido='" + getIdPedido() + "'" +
            ", fechaPedido='" + getFechaPedido() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
