import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TiendaTestModule } from '../../../../test.module';
import { ArticuloPedidoDetailComponent } from 'app/entities/producto/articulo-pedido/articulo-pedido-detail.component';
import { ArticuloPedido } from 'app/shared/model/producto/articulo-pedido.model';

describe('Component Tests', () => {
  describe('ArticuloPedido Management Detail Component', () => {
    let comp: ArticuloPedidoDetailComponent;
    let fixture: ComponentFixture<ArticuloPedidoDetailComponent>;
    const route = ({ data: of({ articuloPedido: new ArticuloPedido(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaTestModule],
        declarations: [ArticuloPedidoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ArticuloPedidoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArticuloPedidoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load articuloPedido on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.articuloPedido).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
