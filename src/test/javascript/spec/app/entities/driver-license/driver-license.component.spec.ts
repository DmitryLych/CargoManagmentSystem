/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { DriverLicenseComponent } from '../../../../../../main/webapp/app/entities/driver-license/driver-license.component';
import { DriverLicenseService } from '../../../../../../main/webapp/app/entities/driver-license/driver-license.service';
import { DriverLicense } from '../../../../../../main/webapp/app/entities/driver-license/driver-license.model';

describe('Component Tests', () => {

    describe('DriverLicense Management Component', () => {
        let comp: DriverLicenseComponent;
        let fixture: ComponentFixture<DriverLicenseComponent>;
        let service: DriverLicenseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [DriverLicenseComponent],
                providers: [
                    DriverLicenseService
                ]
            })
            .overrideTemplate(DriverLicenseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DriverLicenseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DriverLicenseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DriverLicense(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.driverLicenses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
