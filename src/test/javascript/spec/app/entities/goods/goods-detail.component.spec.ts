/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { GoodsDetailComponent } from '../../../../../../main/webapp/app/entities/goods/goods-detail.component';
import { GoodsService } from '../../../../../../main/webapp/app/entities/goods/goods.service';
import { Goods } from '../../../../../../main/webapp/app/entities/goods/goods.model';

describe('Component Tests', () => {

    describe('Goods Management Detail Component', () => {
        let comp: GoodsDetailComponent;
        let fixture: ComponentFixture<GoodsDetailComponent>;
        let service: GoodsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [GoodsDetailComponent],
                providers: [
                    GoodsService
                ]
            })
            .overrideTemplate(GoodsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GoodsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GoodsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Goods(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.goods).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
