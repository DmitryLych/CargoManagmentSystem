/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { GoodsComponent } from '../../../../../../main/webapp/app/entities/goods/goods.component';
import { GoodsService } from '../../../../../../main/webapp/app/entities/goods/goods.service';
import { Goods } from '../../../../../../main/webapp/app/entities/goods/goods.model';

describe('Component Tests', () => {

    describe('Goods Management Component', () => {
        let comp: GoodsComponent;
        let fixture: ComponentFixture<GoodsComponent>;
        let service: GoodsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [GoodsComponent],
                providers: [
                    GoodsService
                ]
            })
            .overrideTemplate(GoodsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GoodsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GoodsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Goods(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.goods[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
