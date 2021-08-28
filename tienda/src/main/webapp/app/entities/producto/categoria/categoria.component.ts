import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoria } from 'app/shared/model/producto/categoria.model';
import { CategoriaService } from './categoria.service';
import { CategoriaDeleteDialogComponent } from './categoria-delete-dialog.component';

@Component({
  selector: 'jhi-categoria',
  templateUrl: './categoria.component.html',
})
export class CategoriaComponent implements OnInit, OnDestroy {
  categorias?: ICategoria[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected categoriaService: CategoriaService,
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
      this.categoriaService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICategoria[]>) => (this.categorias = res.body || []));
      return;
    }

    this.categoriaService.query().subscribe((res: HttpResponse<ICategoria[]>) => (this.categorias = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCategorias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICategoria): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCategorias(): void {
    this.eventSubscriber = this.eventManager.subscribe('categoriaListModification', () => this.loadAll());
  }

  delete(categoria: ICategoria): void {
    const modalRef = this.modalService.open(CategoriaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.categoria = categoria;
  }
}
