import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TiendaSharedModule } from 'app/shared/shared.module';
import { FacturaComponent } from './factura.component';
import { FacturaDetailComponent } from './factura-detail.component';
import { FacturaUpdateComponent } from './factura-update.component';
import { FacturaDeleteDialogComponent } from './factura-delete-dialog.component';
import { facturaRoute } from './factura.route';

@NgModule({
  imports: [TiendaSharedModule, RouterModule.forChild(facturaRoute)],
  declarations: [FacturaComponent, FacturaDetailComponent, FacturaUpdateComponent, FacturaDeleteDialogComponent],
  entryComponents: [FacturaDeleteDialogComponent],
})
export class FacturaFacturaModule {}
