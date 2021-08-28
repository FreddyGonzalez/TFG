import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TiendaTestModule } from '../../../../test.module';
import { ArticuloPedidoUpdateComponent } from 'app/entities/producto/articulo-pedido/articulo-pedido-update.component';
import { ArticuloPedidoService } from 'app/entities/producto/articulo-pedido/articulo-pedido.service';
import { ArticuloPedido } from 'app/shared/model/producto/articulo-pedido.model';

describe('Component Tests', () => {
  describe('ArticuloPedido Management Update Component', () => {
    let comp: ArticuloPedidoUpdateComponent;
    let fixture: ComponentFixture<ArticuloPedidoUpdateComponent>;
    let service: ArticuloPedidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaTestModule],
        declarations: [ArticuloPedidoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ArticuloPedidoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArticuloPedidoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArticuloPedidoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArticuloPedido(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArticuloPedido();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
