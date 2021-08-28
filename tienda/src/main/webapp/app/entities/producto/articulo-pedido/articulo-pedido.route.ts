import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArticuloPedido, ArticuloPedido } from 'app/shared/model/producto/articulo-pedido.model';
import { ArticuloPedidoService } from './articulo-pedido.service';
import { ArticuloPedidoComponent } from './articulo-pedido.component';
import { ArticuloPedidoDetailComponent } from './articulo-pedido-detail.component';
import { ArticuloPedidoUpdateComponent } from './articulo-pedido-update.component';

@Injectable({ providedIn: 'root' })
export class ArticuloPedidoResolve implements Resolve<IArticuloPedido> {
  constructor(private service: ArticuloPedidoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArticuloPedido> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((articuloPedido: HttpResponse<ArticuloPedido>) => {
          if (articuloPedido.body) {
            return of(articuloPedido.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ArticuloPedido());
  }
}

export const articuloPedidoRoute: Routes = [
  {
    path: '',
    component: ArticuloPedidoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tiendaApp.productoArticuloPedido.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArticuloPedidoDetailComponent,
    resolve: {
      articuloPedido: ArticuloPedidoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tiendaApp.productoArticuloPedido.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArticuloPedidoUpdateComponent,
    resolve: {
      articuloPedido: ArticuloPedidoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tiendaApp.productoArticuloPedido.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArticuloPedidoUpdateComponent,
    resolve: {
      articuloPedido: ArticuloPedidoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tiendaApp.productoArticuloPedido.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
