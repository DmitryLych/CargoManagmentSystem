/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { InsurancePolicyDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/insurance-policy/insurance-policy-delete-dialog.component';
import { InsurancePolicyService } from '../../../../../../main/webapp/app/entities/insurance-policy/insurance-policy.service';

describe('Component Tests', () => {

    describe('InsurancePolicy Management Delete Component', () => {
        let comp: InsurancePolicyDeleteDialogComponent;
        let fixture: ComponentFixture<InsurancePolicyDeleteDialogComponent>;
        let service: InsurancePolicyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [InsurancePolicyDeleteDialogComponent],
                providers: [
                    InsurancePolicyService
                ]
            })
            .overrideTemplate(InsurancePolicyDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InsurancePolicyDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InsurancePolicyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
