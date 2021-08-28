import { IProducto } from 'app/shared/model/producto/producto.model';

export interface ICategoria {
  id?: number;
  nombre?: string;
  descripcion?: string;
  productos?: IProducto[];
}

export class Categoria implements ICategoria {
  constructor(public id?: number, public nombre?: string, public descripcion?: string, public productos?: IProducto[]) {}
}
