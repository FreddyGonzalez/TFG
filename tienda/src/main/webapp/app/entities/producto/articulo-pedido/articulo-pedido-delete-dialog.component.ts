import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArticuloPedido } from 'app/shared/model/producto/articulo-pedido.model';
import { ArticuloPedidoService } from './articulo-pedido.service';

@Component({
  templateUrl: './articulo-pedido-delete-dialog.component.html',
})
export class ArticuloPedidoDeleteDialogComponent {
  articuloPedido?: IArticuloPedido;

  constructor(
    protected articuloPedidoService: ArticuloPedidoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.articuloPedidoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('articuloPedidoListModification');
      this.activeModal.close();
    });
  }
}
