import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComentario } from 'app/shared/model/comentario/comentario.model';
import { ComentarioService } from './comentario.service';

@Component({
  templateUrl: './comentario-delete-dialog.component.html',
})
export class ComentarioDeleteDialogComponent {
  comentario?: IComentario;

  constructor(
    protected comentarioService: ComentarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.comentarioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('comentarioListModification');
      this.activeModal.close();
    });
  }
}
