import { Moment } from 'moment';
import { TipoNotificacion } from 'app/shared/model/enumerations/tipo-notificacion.model';

export interface INotificacion {
  id?: number;
  fecha?: Moment;
  detalles?: string;
  fechaEnvio?: Moment;
  tipo?: TipoNotificacion;
  clienteId?: number;
  productoId?: number;
}

export class Notificacion implements INotificacion {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public detalles?: string,
    public fechaEnvio?: Moment,
    public tipo?: TipoNotificacion,
    public clienteId?: number,
    public productoId?: number
  ) {}
}
