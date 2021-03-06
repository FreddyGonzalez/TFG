import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TiendaTestModule } from '../../../../test.module';
import { EnvioDetailComponent } from 'app/entities/factura/envio/envio-detail.component';
import { Envio } from 'app/shared/model/factura/envio.model';

describe('Component Tests', () => {
  describe('Envio Management Detail Component', () => {
    let comp: EnvioDetailComponent;
    let fixture: ComponentFixture<EnvioDetailComponent>;
    const route = ({ data: of({ envio: new Envio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaTestModule],
        declarations: [EnvioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EnvioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EnvioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load envio on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.envio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
