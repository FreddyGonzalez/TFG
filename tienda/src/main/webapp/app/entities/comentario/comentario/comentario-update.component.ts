import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IComentario, Comentario } from 'app/shared/model/comentario/comentario.model';
import { ComentarioService } from './comentario.service';

@Component({
  selector: 'jhi-comentario-update',
  templateUrl: './comentario-update.component.html',
})
export class ComentarioUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idComentario: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    productoId: [null, [Validators.required]],
  });

  constructor(protected comentarioService: ComentarioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comentario }) => {
      this.updateForm(comentario);
    });
  }

  updateForm(comentario: IComentario): void {
    this.editForm.patchValue({
      id: comentario.id,
      idComentario: comentario.idComentario,
      descripcion: comentario.descripcion,
      productoId: comentario.productoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comentario = this.createFromForm();
    if (comentario.id !== undefined) {
      this.subscribeToSaveResponse(this.comentarioService.update(comentario));
    } else {
      this.subscribeToSaveResponse(this.comentarioService.create(comentario));
    }
  }

  private createFromForm(): IComentario {
    return {
      ...new Comentario(),
      id: this.editForm.get(['id'])!.value,
      idComentario: this.editForm.get(['idComentario'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      productoId: this.editForm.get(['productoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComentario>>): void {
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
