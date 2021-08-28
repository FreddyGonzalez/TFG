import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IEnvio } from 'app/shared/model/factura/envio.model';

type EntityResponseType = HttpResponse<IEnvio>;
type EntityArrayResponseType = HttpResponse<IEnvio[]>;

@Injectable({ providedIn: 'root' })
export class EnvioService {
  public resourceUrl = SERVER_API_URL + 'services/factura/api/envios';
  public resourceSearchUrl = SERVER_API_URL + 'services/factura/api/_search/envios';

  constructor(protected http: HttpClient) {}

  create(envio: IEnvio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(envio);
    return this.http
      .post<IEnvio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(envio: IEnvio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(envio);
    return this.http
      .put<IEnvio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEnvio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEnvio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEnvio[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(envio: IEnvio): IEnvio {
    const copy: IEnvio = Object.assign({}, envio, {
      fecha: envio.fecha && envio.fecha.isValid() ? envio.fecha.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((envio: IEnvio) => {
        envio.fecha = envio.fecha ? moment(envio.fecha) : undefined;
      });
    }
    return res;
  }
}
