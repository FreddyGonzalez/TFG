import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IArticuloPedido, ArticuloPedido } from 'app/shared/model/producto/articulo-pedido.model';
import { ArticuloPedidoService } from './articulo-pedido.service';
import { IProducto } from 'app/shared/model/producto/producto.model';
import { ProductoService } from 'app/entities/producto/producto/producto.service';
import { IPedido } from 'app/shared/model/producto/pedido.model';
import { PedidoService } from 'app/entities/producto/pedido/pedido.service';

type SelectableEntity = IProducto | IPedido;

@Component({
  selector: 'jhi-articulo-pedido-update',
  templateUrl: './articulo-pedido-update.component.html',
})
export class ArticuloPedidoUpdateComponent implements OnInit {
  isSaving = false;
  productos: IProducto[] = [];
  pedidos: IPedido[] = [];

  editForm = this.fb.group({
    id: [],
    cantidad: [null, [Validators.required, Validators.min(0)]],
    total: [null, [Validators.required, Validators.min(0)]],
    estado: [null, [Validators.required]],
    producto: [null, Validators.required],
    pedido: [null, Validators.required],
  });

  constructor(
    protected articuloPedidoService: ArticuloPedidoService,
    protected productoService: ProductoService,
    protected pedidoService: PedidoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ articuloPedido }) => {
      this.updateForm(articuloPedido);

      this.productoService.query().subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body || []));

      this.pedidoService.query().subscribe((res: HttpResponse<IPedido[]>) => (this.pedidos = res.body || []));
    });
  }

  updateForm(articuloPedido: IArticuloPedido): void {
    this.editForm.patchValue({
      id: articuloPedido.id,
      cantidad: articuloPedido.cantidad,
      total: articuloPedido.total,
      estado: articuloPedido.estado,
      producto: articuloPedido.producto,
      pedido: articuloPedido.pedido,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const articuloPedido = this.createFromForm();
    if (articuloPedido.id !== undefined) {
      this.subscribeToSaveResponse(this.articuloPedidoService.update(articuloPedido));
    } else {
      this.subscribeToSaveResponse(this.articuloPedidoService.create(articuloPedido));
    }
  }

  private createFromForm(): IArticuloPedido {
    return {
      ...new ArticuloPedido(),
      id: this.editForm.get(['id'])!.value,
      cantidad: this.editForm.get(['cantidad'])!.value,
      total: this.editForm.get(['total'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      producto: this.editForm.get(['producto'])!.value,
      pedido: this.editForm.get(['pedido'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticuloPedido>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
