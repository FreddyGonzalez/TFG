import { Moment } from 'moment';
import { IEnvio } from 'app/shared/model/factura/envio.model';
import { EstadoFactura } from 'app/shared/model/enumerations/estado-factura.model';
import { MetodoPago } from 'app/shared/model/enumerations/metodo-pago.model';

export interface IFactura {
  id?: number;
  idFactura?: string;
  fecha?: Moment;
  detalles?: string;
  estado?: EstadoFactura;
  metodoPago?: MetodoPago;
  fechaPago?: Moment;
  total?: number;
  idPedidoProducto?: number;
  envios?: IEnvio[];
}

export class Factura implements IFactura {
  constructor(
    public id?: number,
    public idFactura?: string,
    public fecha?: Moment,
    public detalles?: string,
    public estado?: EstadoFactura,
    public metodoPago?: MetodoPago,
    public fechaPago?: Moment,
    public total?: number,
    public idPedidoProducto?: number,
    public envios?: IEnvio[]
  ) {}
}
