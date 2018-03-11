/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { InsurancePolicyDetailComponent } from '../../../../../../main/webapp/app/entities/insurance-policy/insurance-policy-detail.component';
import { InsurancePolicyService } from '../../../../../../main/webapp/app/entities/insurance-policy/insurance-policy.service';
import { InsurancePolicy } from '../../../../../../main/webapp/app/entities/insurance-policy/insurance-policy.model';

describe('Component Tests', () => {

    describe('InsurancePolicy Management Detail Component', () => {
        let comp: InsurancePolicyDetailComponent;
        let fixture: ComponentFixture<InsurancePolicyDetailComponent>;
        let service: InsurancePolicyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [InsurancePolicyDetailComponent],
                providers: [
                    InsurancePolicyService
                ]
            })
            .overrideTemplate(InsurancePolicyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InsurancePolicyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InsurancePolicyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new InsurancePolicy(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.insurancePolicy).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
