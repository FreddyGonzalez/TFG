import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TiendaSharedModule } from 'app/shared/shared.module';
import { EnvioComponent } from './envio.component';
import { EnvioDetailComponent } from './envio-detail.component';
import { EnvioUpdateComponent } from './envio-update.component';
import { EnvioDeleteDialogComponent } from './envio-delete-dialog.component';
import { envioRoute } from './envio.route';

@NgModule({
  imports: [TiendaSharedModule, RouterModule.forChild(envioRoute)],
  declarations: [EnvioComponent, EnvioDetailComponent, EnvioUpdateComponent, EnvioDeleteDialogComponent],
  entryComponents: [EnvioDeleteDialogComponent],
})
export class FacturaEnvioModule {}
