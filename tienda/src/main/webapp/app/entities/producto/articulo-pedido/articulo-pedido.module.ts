import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TiendaSharedModule } from 'app/shared/shared.module';
import { ArticuloPedidoComponent } from './articulo-pedido.component';
import { ArticuloPedidoDetailComponent } from './articulo-pedido-detail.component';
import { ArticuloPedidoUpdateComponent } from './articulo-pedido-update.component';
import { ArticuloPedidoDeleteDialogComponent } from './articulo-pedido-delete-dialog.component';
import { articuloPedidoRoute } from './articulo-pedido.route';

@NgModule({
  imports: [TiendaSharedModule, RouterModule.forChild(articuloPedidoRoute)],
  declarations: [
    ArticuloPedidoComponent,
    ArticuloPedidoDetailComponent,
    ArticuloPedidoUpdateComponent,
    ArticuloPedidoDeleteDialogComponent,
  ],
  entryComponents: [ArticuloPedidoDeleteDialogComponent],
})
export class ProductoArticuloPedidoModule {}
