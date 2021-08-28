import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFactura, Factura } from 'app/shared/model/factura/factura.model';
import { FacturaService } from './factura.service';

@Component({
  selector: 'jhi-factura-update',
  templateUrl: './factura-update.component.html',
})
export class FacturaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idFactura: [null, [Validators.required]],
    fecha: [null, [Validators.required]],
    detalles: [],
    estado: [null, [Validators.required]],
    metodoPago: [null, [Validators.required]],
    fechaPago: [null, [Validators.required]],
    total: [null, [Validators.required]],
    idPedidoProducto: [null, [Validators.required]],
  });

  constructor(protected facturaService: FacturaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factura }) => {
      if (!factura.id) {
        const today = moment().startOf('day');
        factura.fecha = today;
        factura.fechaPago = today;
      }

      this.updateForm(factura);
    });
  }

  updateForm(factura: IFactura): void {
    this.editForm.patchValue({
      id: factura.id,
      idFactura: factura.idFactura,
      fecha: factura.fecha ? factura.fecha.format(DATE_TIME_FORMAT) : null,
      detalles: factura.detalles,
      estado: factura.estado,
      metodoPago: factura.metodoPago,
      fechaPago: factura.fechaPago ? factura.fechaPago.format(DATE_TIME_FORMAT) : null,
      total: factura.total,
      idPedidoProducto: factura.idPedidoProducto,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const factura = this.createFromForm();
    if (factura.id !== undefined) {
      this.subscribeToSaveResponse(this.facturaService.update(factura));
    } else {
      this.subscribeToSaveResponse(this.facturaService.create(factura));
    }
  }

  private createFromForm(): IFactura {
    return {
      ...new Factura(),
      id: this.editForm.get(['id'])!.value,
      idFactura: this.editForm.get(['idFactura'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      detalles: this.editForm.get(['detalles'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      metodoPago: this.editForm.get(['metodoPago'])!.value,
      fechaPago: this.editForm.get(['fechaPago'])!.value ? moment(this.editForm.get(['fechaPago'])!.value, DATE_TIME_FORMAT) : undefined,
      total: this.editForm.get(['total'])!.value,
      idPedidoProducto: this.editForm.get(['idPedidoProducto'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFactura>>): void {
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
