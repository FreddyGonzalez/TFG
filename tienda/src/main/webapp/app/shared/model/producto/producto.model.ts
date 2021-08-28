import { ICategoria } from 'app/shared/model/producto/categoria.model';
import { Talla } from 'app/shared/model/enumerations/talla.model';

export interface IProducto {
  id?: number;
  nombre?: string;
  descripcion?: string;
  precio?: number;
  talla?: Talla;
  imagenContentType?: string;
  imagen?: any;
  categoria?: ICategoria;
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public precio?: number,
    public talla?: Talla,
    public imagenContentType?: string,
    public imagen?: any,
    public categoria?: ICategoria
  ) {}
}
