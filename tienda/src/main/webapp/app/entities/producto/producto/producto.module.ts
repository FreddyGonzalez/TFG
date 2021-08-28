import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TiendaSharedModule } from 'app/shared/shared.module';
import { ProductoComponent } from './producto.component';
import { ProductoDetailComponent } from './producto-detail.component';
import { ProductoUpdateComponent } from './producto-update.component';
import { ProductoDeleteDialogComponent } from './producto-delete-dialog.component';
import { productoRoute } from './producto.route';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [TiendaSharedModule, RouterModule.forChild(productoRoute), NgbModule],
  declarations: [ProductoComponent, ProductoDetailComponent, ProductoUpdateComponent, ProductoDeleteDialogComponent],
  entryComponents: [ProductoDeleteDialogComponent],
})
export class ProductoProductoModule {}
