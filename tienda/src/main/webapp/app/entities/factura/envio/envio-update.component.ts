import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEnvio, Envio } from 'app/shared/model/factura/envio.model';
import { EnvioService } from './envio.service';
import { IFactura } from 'app/shared/model/factura/factura.model';
import { FacturaService } from 'app/entities/factura/factura/factura.service';

@Component({
  selector: 'jhi-envio-update',
  templateUrl: './envio-update.component.html',
})
export class EnvioUpdateComponent implements OnInit {
  isSaving = false;
  facturas: IFactura[] = [];

  editForm = this.fb.group({
    id: [],
    idEnvio: [],
    fecha: [null, [Validators.required]],
    detalles: [],
    factura: [null, Validators.required],
  });

  constructor(
    protected envioService: EnvioService,
    protected facturaService: FacturaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ envio }) => {
      if (!envio.id) {
        const today = moment().startOf('day');
        envio.fecha = today;
      }

      this.updateForm(envio);

      this.facturaService.query().subscribe((res: HttpResponse<IFactura[]>) => (this.facturas = res.body || []));
    });
  }

  updateForm(envio: IEnvio): void {
    this.editForm.patchValue({
      id: envio.id,
      idEnvio: envio.idEnvio,
      fecha: envio.fecha ? envio.fecha.format(DATE_TIME_FORMAT) : null,
      detalles: envio.detalles,
      factura: envio.factura,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const envio = this.createFromForm();
    if (envio.id !== undefined) {
      this.subscribeToSaveResponse(this.envioService.update(envio));
    } else {
      this.subscribeToSaveResponse(this.envioService.create(envio));
    }
  }

  private createFromForm(): IEnvio {
    return {
      ...new Envio(),
      id: this.editForm.get(['id'])!.value,
      idEnvio: this.editForm.get(['idEnvio'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      detalles: this.editForm.get(['detalles'])!.value,
      factura: this.editForm.get(['factura'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnvio>>): void {
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

  trackById(index: number, item: IFactura): any {
    return item.id;
  }
}
