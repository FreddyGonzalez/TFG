import { IUser } from 'app/core/user/user.model';
import { Genero } from 'app/shared/model/enumerations/genero.model';

export interface ICliente {
  id?: number;
  idCliente?: string;
  nombre?: string;
  apellido?: string;
  genero?: Genero;
  email?: string;
  telefono?: string;
  direccion?: string;
  ciudad?: string;
  pais?: string;
  user?: IUser;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public idCliente?: string,
    public nombre?: string,
    public apellido?: string,
    public genero?: Genero,
    public email?: string,
    public telefono?: string,
    public direccion?: string,
    public ciudad?: string,
    public pais?: string,
    public user?: IUser
  ) {}
}
