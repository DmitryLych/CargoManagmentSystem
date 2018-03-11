/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { TruckDetailComponent } from '../../../../../../main/webapp/app/entities/truck/truck-detail.component';
import { TruckService } from '../../../../../../main/webapp/app/entities/truck/truck.service';
import { Truck } from '../../../../../../main/webapp/app/entities/truck/truck.model';

describe('Component Tests', () => {

    describe('Truck Management Detail Component', () => {
        let comp: TruckDetailComponent;
        let fixture: ComponentFixture<TruckDetailComponent>;
        let service: TruckService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [TruckDetailComponent],
                providers: [
                    TruckService
                ]
            })
            .overrideTemplate(TruckDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TruckDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TruckService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Truck(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.truck).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
