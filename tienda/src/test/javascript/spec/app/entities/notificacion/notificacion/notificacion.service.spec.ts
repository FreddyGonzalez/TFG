import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { NotificacionService } from 'app/entities/notificacion/notificacion/notificacion.service';
import { INotificacion, Notificacion } from 'app/shared/model/notificacion/notificacion.model';
import { TipoNotificacion } from 'app/shared/model/enumerations/tipo-notificacion.model';

describe('Service Tests', () => {
  describe('Notificacion Service', () => {
    let injector: TestBed;
    let service: NotificacionService;
    let httpMock: HttpTestingController;
    let elemDefault: INotificacion;
    let expectedResult: INotificacion | INotificacion[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(NotificacionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Notificacion(0, currentDate, 'AAAAAAA', currentDate, TipoNotificacion.EMAIL, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            fechaEnvio: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Notificacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fecha: currentDate.format(DATE_TIME_FORMAT),
            fechaEnvio: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaEnvio: currentDate,
          },
          returnedFromService
        );

        service.create(new Notificacion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Notificacion', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            detalles: 'BBBBBB',
            fechaEnvio: currentDate.format(DATE_TIME_FORMAT),
            tipo: 'BBBBBB',
            clienteId: 1,
            productoId: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaEnvio: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Notificacion', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            detalles: 'BBBBBB',
            fechaEnvio: currentDate.format(DATE_TIME_FORMAT),
            tipo: 'BBBBBB',
            clienteId: 1,
            productoId: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaEnvio: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Notificacion', () => {
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
