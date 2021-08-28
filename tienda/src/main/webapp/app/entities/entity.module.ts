import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.TiendaClienteModule),
      },
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto/producto.module').then(m => m.ProductoProductoModule),
      },
      {
        path: 'categoria',
        loadChildren: () => import('./producto/categoria/categoria.module').then(m => m.ProductoCategoriaModule),
      },
      {
        path: 'pedido',
        loadChildren: () => import('./producto/pedido/pedido.module').then(m => m.ProductoPedidoModule),
      },
      {
        path: 'articulo-pedido',
        loadChildren: () => import('./producto/articulo-pedido/articulo-pedido.module').then(m => m.ProductoArticuloPedidoModule),
      },
      {
        path: 'factura',
        loadChildren: () => import('./factura/factura/factura.module').then(m => m.FacturaFacturaModule),
      },
      {
        path: 'envio',
        loadChildren: () => import('./factura/envio/envio.module').then(m => m.FacturaEnvioModule),
      },
      {
        path: 'notificacion',
        loadChildren: () => import('./notificacion/notificacion/notificacion.module').then(m => m.NotificacionNotificacionModule),
      },
      {
        path: 'comentario',
        loadChildren: () => import('./comentario/comentario/comentario.module').then(m => m.ComentarioComentarioModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TiendaEntityModule {}
