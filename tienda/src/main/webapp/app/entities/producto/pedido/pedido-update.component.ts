import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPedido, Pedido } from 'app/shared/model/producto/pedido.model';
import { PedidoService } from './pedido.service';

@Component({
  selector: 'jhi-pedido-update',
  templateUrl: './pedido-update.component.html',
})
export class PedidoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idPedido: [null, [Validators.required]],
    fechaPedido: [null, [Validators.required]],
    estado: [null, [Validators.required]],
  });

  constructor(protected pedidoService: PedidoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      if (!pedido.id) {
        const today = moment().startOf('day');
        pedido.fechaPedido = today;
      }

      this.updateForm(pedido);
    });
  }

  updateForm(pedido: IPedido): void {
    this.editForm.patchValue({
      id: pedido.id,
      idPedido: pedido.idPedido,
      fechaPedido: pedido.fechaPedido ? pedido.fechaPedido.format(DATE_TIME_FORMAT) : null,
      estado: pedido.estado,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pedido = this.createFromForm();
    if (pedido.id !== undefined) {
      this.subscribeToSaveResponse(this.pedidoService.update(pedido));
    } else {
      this.subscribeToSaveResponse(this.pedidoService.create(pedido));
    }
  }

  private createFromForm(): IPedido {
    return {
      ...new Pedido(),
      id: this.editForm.get(['id'])!.value,
      idPedido: this.editForm.get(['idPedido'])!.value,
      fechaPedido: this.editForm.get(['fechaPedido'])!.value
        ? moment(this.editForm.get(['fechaPedido'])!.value, DATE_TIME_FORMAT)
        : undefined,
      estado: this.editForm.get(['estado'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPedido>>): void {
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
}
