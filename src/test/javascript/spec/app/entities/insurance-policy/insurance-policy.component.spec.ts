/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { InsurancePolicyComponent } from '../../../../../../main/webapp/app/entities/insurance-policy/insurance-policy.component';
import { InsurancePolicyService } from '../../../../../../main/webapp/app/entities/insurance-policy/insurance-policy.service';
import { InsurancePolicy } from '../../../../../../main/webapp/app/entities/insurance-policy/insurance-policy.model';

describe('Component Tests', () => {

    describe('InsurancePolicy Management Component', () => {
        let comp: InsurancePolicyComponent;
        let fixture: ComponentFixture<InsurancePolicyComponent>;
        let service: InsurancePolicyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [InsurancePolicyComponent],
                providers: [
                    InsurancePolicyService
                ]
            })
            .overrideTemplate(InsurancePolicyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InsurancePolicyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InsurancePolicyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new InsurancePolicy(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.insurancePolicies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
