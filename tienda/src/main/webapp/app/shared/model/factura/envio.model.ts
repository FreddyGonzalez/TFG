import { Moment } from 'moment';
import { IFactura } from 'app/shared/model/factura/factura.model';

export interface IEnvio {
  id?: number;
  idEnvio?: string;
  fecha?: Moment;
  detalles?: string;
  factura?: IFactura;
}

export class Envio implements IEnvio {
  constructor(public id?: number, public idEnvio?: string, public fecha?: Moment, public detalles?: string, public factura?: IFactura) {}
}
