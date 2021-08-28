import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IArticuloPedido } from 'app/shared/model/producto/articulo-pedido.model';

type EntityResponseType = HttpResponse<IArticuloPedido>;
type EntityArrayResponseType = HttpResponse<IArticuloPedido[]>;

@Injectable({ providedIn: 'root' })
export class ArticuloPedidoService {
  public resourceUrl = SERVER_API_URL + 'services/producto/api/articulo-pedidos';
  public resourceSearchUrl = SERVER_API_URL + 'services/producto/api/_search/articulo-pedidos';

  constructor(protected http: HttpClient) {}

  create(articuloPedido: IArticuloPedido): Observable<EntityResponseType> {
    return this.http.post<IArticuloPedido>(this.resourceUrl, articuloPedido, { observe: 'response' });
  }

  update(articuloPedido: IArticuloPedido): Observable<EntityResponseType> {
    return this.http.put<IArticuloPedido>(this.resourceUrl, articuloPedido, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArticuloPedido>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArticuloPedido[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArticuloPedido[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
