import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INotificacion } from 'app/shared/model/notificacion/notificacion.model';
import { NotificacionService } from './notificacion.service';
import { NotificacionDeleteDialogComponent } from './notificacion-delete-dialog.component';

@Component({
  selector: 'jhi-notificacion',
  templateUrl: './notificacion.component.html',
})
export class NotificacionComponent implements OnInit, OnDestroy {
  notificacions?: INotificacion[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected notificacionService: NotificacionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.notificacionService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<INotificacion[]>) => (this.notificacions = res.body || []));
      return;
    }

    this.notificacionService.query().subscribe((res: HttpResponse<INotificacion[]>) => (this.notificacions = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNotificacions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INotificacion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNotificacions(): void {
    this.eventSubscriber = this.eventManager.subscribe('notificacionListModification', () => this.loadAll());
  }

  delete(notificacion: INotificacion): void {
    const modalRef = this.modalService.open(NotificacionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.notificacion = notificacion;
  }
}
