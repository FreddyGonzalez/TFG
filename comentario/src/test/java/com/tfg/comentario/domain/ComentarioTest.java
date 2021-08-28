package com.tfg.comentario.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tfg.comentario.web.rest.TestUtil;

public class ComentarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comentario.class);
        Comentario comentario1 = new Comentario();
        comentario1.setId(1L);
        Comentario comentario2 = new Comentario();
        comentario2.setId(comentario1.getId());
        assertThat(comentario1).isEqualTo(comentario2);
        comentario2.setId(2L);
        assertThat(comentario1).isNotEqualTo(comentario2);
        comentario1.setId(null);
        assertThat(comentario1).isNotEqualTo(comentario2);
    }
}
