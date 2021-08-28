import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArticuloPedido } from 'app/shared/model/producto/articulo-pedido.model';

@Component({
  selector: 'jhi-articulo-pedido-detail',
  templateUrl: './articulo-pedido-detail.component.html',
})
export class ArticuloPedidoDetailComponent implements OnInit {
  articuloPedido: IArticuloPedido | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ articuloPedido }) => (this.articuloPedido = articuloPedido));
  }

  previousState(): void {
    window.history.back();
  }
}
