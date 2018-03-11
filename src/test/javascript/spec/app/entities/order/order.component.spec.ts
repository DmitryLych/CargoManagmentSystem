/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { OrderComponent } from '../../../../../../main/webapp/app/entities/order/order.component';
import { OrderService } from '../../../../../../main/webapp/app/entities/order/order.service';
import { Order } from '../../../../../../main/webapp/app/entities/order/order.model';

describe('Component Tests', () => {

    describe('Order Management Component', () => {
        let comp: OrderComponent;
        let fixture: ComponentFixture<OrderComponent>;
        let service: OrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [OrderComponent],
                providers: [
                    OrderService
                ]
            })
            .overrideTemplate(OrderComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Order(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.orders[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
