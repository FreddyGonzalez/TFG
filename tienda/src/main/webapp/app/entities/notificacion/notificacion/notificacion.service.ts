import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { INotificacion } from 'app/shared/model/notificacion/notificacion.model';

type EntityResponseType = HttpResponse<INotificacion>;
type EntityArrayResponseType = HttpResponse<INotificacion[]>;

@Injectable({ providedIn: 'root' })
export class NotificacionService {
  public resourceUrl = SERVER_API_URL + 'services/notificacion/api/notificacions';
  public resourceSearchUrl = SERVER_API_URL + 'services/notificacion/api/_search/notificacions';

  constructor(protected http: HttpClient) {}

  create(notificacion: INotificacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notificacion);
    return this.http
      .post<INotificacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(notificacion: INotificacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notificacion);
    return this.http
      .put<INotificacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INotificacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INotificacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INotificacion[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(notificacion: INotificacion): INotificacion {
    const copy: INotificacion = Object.assign({}, notificacion, {
      fecha: notificacion.fecha && notificacion.fecha.isValid() ? notificacion.fecha.toJSON() : undefined,
      fechaEnvio: notificacion.fechaEnvio && notificacion.fechaEnvio.isValid() ? notificacion.fechaEnvio.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
      res.body.fechaEnvio = res.body.fechaEnvio ? moment(res.body.fechaEnvio) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((notificacion: INotificacion) => {
        notificacion.fecha = notificacion.fecha ? moment(notificacion.fecha) : undefined;
        notificacion.fechaEnvio = notificacion.fechaEnvio ? moment(notificacion.fechaEnvio) : undefined;
      });
    }
    return res;
  }
}
