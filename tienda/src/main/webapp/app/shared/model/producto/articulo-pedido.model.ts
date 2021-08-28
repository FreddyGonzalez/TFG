import { IProducto } from 'app/shared/model/producto/producto.model';
import { IPedido } from 'app/shared/model/producto/pedido.model';
import { EstadoArticuloPedido } from 'app/shared/model/enumerations/estado-articulo-pedido.model';

export interface IArticuloPedido {
  id?: number;
  cantidad?: number;
  total?: number;
  estado?: EstadoArticuloPedido;
  producto?: IProducto;
  pedido?: IPedido;
}

export class ArticuloPedido implements IArticuloPedido {
  constructor(
    public id?: number,
    public cantidad?: number,
    public total?: number,
    public estado?: EstadoArticuloPedido,
    public producto?: IProducto,
    public pedido?: IPedido
  ) {}
}
