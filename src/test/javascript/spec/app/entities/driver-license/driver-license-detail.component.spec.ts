/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { DriverLicenseDetailComponent } from '../../../../../../main/webapp/app/entities/driver-license/driver-license-detail.component';
import { DriverLicenseService } from '../../../../../../main/webapp/app/entities/driver-license/driver-license.service';
import { DriverLicense } from '../../../../../../main/webapp/app/entities/driver-license/driver-license.model';

describe('Component Tests', () => {

    describe('DriverLicense Management Detail Component', () => {
        let comp: DriverLicenseDetailComponent;
        let fixture: ComponentFixture<DriverLicenseDetailComponent>;
        let service: DriverLicenseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [DriverLicenseDetailComponent],
                providers: [
                    DriverLicenseService
                ]
            })
            .overrideTemplate(DriverLicenseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DriverLicenseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DriverLicenseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DriverLicense(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.driverLicense).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
