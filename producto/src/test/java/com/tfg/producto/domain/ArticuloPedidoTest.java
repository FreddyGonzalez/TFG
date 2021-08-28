package com.tfg.producto.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tfg.producto.web.rest.TestUtil;

public class ArticuloPedidoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticuloPedido.class);
        ArticuloPedido articuloPedido1 = new ArticuloPedido();
        articuloPedido1.setId(1L);
        ArticuloPedido articuloPedido2 = new ArticuloPedido();
        articuloPedido2.setId(articuloPedido1.getId());
        assertThat(articuloPedido1).isEqualTo(articuloPedido2);
        articuloPedido2.setId(2L);
        assertThat(articuloPedido1).isNotEqualTo(articuloPedido2);
        articuloPedido1.setId(null);
        assertThat(articuloPedido1).isNotEqualTo(articuloPedido2);
    }
}
