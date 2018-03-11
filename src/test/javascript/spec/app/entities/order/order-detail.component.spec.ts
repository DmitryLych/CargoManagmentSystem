/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { OrderDetailComponent } from '../../../../../../main/webapp/app/entities/order/order-detail.component';
import { OrderService } from '../../../../../../main/webapp/app/entities/order/order.service';
import { Order } from '../../../../../../main/webapp/app/entities/order/order.model';

describe('Component Tests', () => {

    describe('Order Management Detail Component', () => {
        let comp: OrderDetailComponent;
        let fixture: ComponentFixture<OrderDetailComponent>;
        let service: OrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [OrderDetailComponent],
                providers: [
                    OrderService
                ]
            })
            .overrideTemplate(OrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Order(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.order).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
