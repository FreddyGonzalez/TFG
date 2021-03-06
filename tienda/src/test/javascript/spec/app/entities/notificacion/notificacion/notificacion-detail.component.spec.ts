import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TiendaTestModule } from '../../../../test.module';
import { NotificacionDetailComponent } from 'app/entities/notificacion/notificacion/notificacion-detail.component';
import { Notificacion } from 'app/shared/model/notificacion/notificacion.model';

describe('Component Tests', () => {
  describe('Notificacion Management Detail Component', () => {
    let comp: NotificacionDetailComponent;
    let fixture: ComponentFixture<NotificacionDetailComponent>;
    const route = ({ data: of({ notificacion: new Notificacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaTestModule],
        declarations: [NotificacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NotificacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotificacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load notificacion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.notificacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
