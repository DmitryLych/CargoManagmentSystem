/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { InsurancePolicyDialogComponent } from '../../../../../../main/webapp/app/entities/insurance-policy/insurance-policy-dialog.component';
import { InsurancePolicyService } from '../../../../../../main/webapp/app/entities/insurance-policy/insurance-policy.service';
import { InsurancePolicy } from '../../../../../../main/webapp/app/entities/insurance-policy/insurance-policy.model';
import { DriverService } from '../../../../../../main/webapp/app/entities/driver';

describe('Component Tests', () => {

    describe('InsurancePolicy Management Dialog Component', () => {
        let comp: InsurancePolicyDialogComponent;
        let fixture: ComponentFixture<InsurancePolicyDialogComponent>;
        let service: InsurancePolicyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [InsurancePolicyDialogComponent],
                providers: [
                    DriverService,
                    InsurancePolicyService
                ]
            })
            .overrideTemplate(InsurancePolicyDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InsurancePolicyDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InsurancePolicyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InsurancePolicy(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.insurancePolicy = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'insurancePolicyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InsurancePolicy();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.insurancePolicy = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'insurancePolicyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
