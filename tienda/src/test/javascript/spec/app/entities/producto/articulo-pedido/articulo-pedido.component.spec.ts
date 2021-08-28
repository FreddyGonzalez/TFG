import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { TiendaTestModule } from '../../../../test.module';
import { ArticuloPedidoComponent } from 'app/entities/producto/articulo-pedido/articulo-pedido.component';
import { ArticuloPedidoService } from 'app/entities/producto/articulo-pedido/articulo-pedido.service';
import { ArticuloPedido } from 'app/shared/model/producto/articulo-pedido.model';

describe('Component Tests', () => {
  describe('ArticuloPedido Management Component', () => {
    let comp: ArticuloPedidoComponent;
    let fixture: ComponentFixture<ArticuloPedidoComponent>;
    let service: ArticuloPedidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaTestModule],
        declarations: [ArticuloPedidoComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(ArticuloPedidoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArticuloPedidoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArticuloPedidoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArticuloPedido(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.articuloPedidos && comp.articuloPedidos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArticuloPedido(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.articuloPedidos && comp.articuloPedidos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
