import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ArticuloPedidoService } from 'app/entities/producto/articulo-pedido/articulo-pedido.service';
import { IArticuloPedido, ArticuloPedido } from 'app/shared/model/producto/articulo-pedido.model';
import { EstadoArticuloPedido } from 'app/shared/model/enumerations/estado-articulo-pedido.model';

describe('Service Tests', () => {
  describe('ArticuloPedido Service', () => {
    let injector: TestBed;
    let service: ArticuloPedidoService;
    let httpMock: HttpTestingController;
    let elemDefault: IArticuloPedido;
    let expectedResult: IArticuloPedido | IArticuloPedido[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ArticuloPedidoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ArticuloPedido(0, 0, 0, EstadoArticuloPedido.DISPONIBLE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ArticuloPedido', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ArticuloPedido()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ArticuloPedido', () => {
        const returnedFromService = Object.assign(
          {
            cantidad: 1,
            total: 1,
            estado: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ArticuloPedido', () => {
        const returnedFromService = Object.assign(
          {
            cantidad: 1,
            total: 1,
            estado: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ArticuloPedido', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
