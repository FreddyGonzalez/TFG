export interface IComentario {
  id?: number;
  idComentario?: string;
  descripcion?: string;
  productoId?: number;
}

export class Comentario implements IComentario {
  constructor(public id?: number, public idComentario?: string, public descripcion?: string, public productoId?: number) {}
}
