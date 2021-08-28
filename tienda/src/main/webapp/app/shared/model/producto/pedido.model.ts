import { Moment } from 'moment';
import { IArticuloPedido } from 'app/shared/model/producto/articulo-pedido.model';
import { EstadoPedido } from 'app/shared/model/enumerations/estado-pedido.model';

export interface IPedido {
  id?: number;
  idPedido?: string;
  fechaPedido?: Moment;
  estado?: EstadoPedido;
  articuloPedidos?: IArticuloPedido[];
}

export class Pedido implements IPedido {
  constructor(
    public id?: number,
    public idPedido?: string,
    public fechaPedido?: Moment,
    public estado?: EstadoPedido,
    public articuloPedidos?: IArticuloPedido[]
  ) {}
}
