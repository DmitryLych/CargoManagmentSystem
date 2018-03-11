/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { TruckComponent } from '../../../../../../main/webapp/app/entities/truck/truck.component';
import { TruckService } from '../../../../../../main/webapp/app/entities/truck/truck.service';
import { Truck } from '../../../../../../main/webapp/app/entities/truck/truck.model';

describe('Component Tests', () => {

    describe('Truck Management Component', () => {
        let comp: TruckComponent;
        let fixture: ComponentFixture<TruckComponent>;
        let service: TruckService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [TruckComponent],
                providers: [
                    TruckService
                ]
            })
            .overrideTemplate(TruckComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TruckComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TruckService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Truck(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.trucks[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
