import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TiendaSharedModule } from 'app/shared/shared.module';
import { NotificacionComponent } from './notificacion.component';
import { NotificacionDetailComponent } from './notificacion-detail.component';
import { NotificacionUpdateComponent } from './notificacion-update.component';
import { NotificacionDeleteDialogComponent } from './notificacion-delete-dialog.component';
import { notificacionRoute } from './notificacion.route';

@NgModule({
  imports: [TiendaSharedModule, RouterModule.forChild(notificacionRoute)],
  declarations: [NotificacionComponent, NotificacionDetailComponent, NotificacionUpdateComponent, NotificacionDeleteDialogComponent],
  entryComponents: [NotificacionDeleteDialogComponent],
})
export class NotificacionNotificacionModule {}
