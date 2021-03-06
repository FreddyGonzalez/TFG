import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TiendaTestModule } from '../../../../test.module';
import { CategoriaDetailComponent } from 'app/entities/producto/categoria/categoria-detail.component';
import { Categoria } from 'app/shared/model/producto/categoria.model';

describe('Component Tests', () => {
  describe('Categoria Management Detail Component', () => {
    let comp: CategoriaDetailComponent;
    let fixture: ComponentFixture<CategoriaDetailComponent>;
    const route = ({ data: of({ categoria: new Categoria(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaTestModule],
        declarations: [CategoriaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategoriaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoria on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoria).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
