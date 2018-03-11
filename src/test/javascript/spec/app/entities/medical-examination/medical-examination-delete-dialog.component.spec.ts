/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { MedicalExaminationDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/medical-examination/medical-examination-delete-dialog.component';
import { MedicalExaminationService } from '../../../../../../main/webapp/app/entities/medical-examination/medical-examination.service';

describe('Component Tests', () => {

    describe('MedicalExamination Management Delete Component', () => {
        let comp: MedicalExaminationDeleteDialogComponent;
        let fixture: ComponentFixture<MedicalExaminationDeleteDialogComponent>;
        let service: MedicalExaminationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [MedicalExaminationDeleteDialogComponent],
                providers: [
                    MedicalExaminationService
                ]
            })
            .overrideTemplate(MedicalExaminationDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedicalExaminationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicalExaminationService);
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
