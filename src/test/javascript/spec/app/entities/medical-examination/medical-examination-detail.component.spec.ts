/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { MedicalExaminationDetailComponent } from '../../../../../../main/webapp/app/entities/medical-examination/medical-examination-detail.component';
import { MedicalExaminationService } from '../../../../../../main/webapp/app/entities/medical-examination/medical-examination.service';
import { MedicalExamination } from '../../../../../../main/webapp/app/entities/medical-examination/medical-examination.model';

describe('Component Tests', () => {

    describe('MedicalExamination Management Detail Component', () => {
        let comp: MedicalExaminationDetailComponent;
        let fixture: ComponentFixture<MedicalExaminationDetailComponent>;
        let service: MedicalExaminationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [MedicalExaminationDetailComponent],
                providers: [
                    MedicalExaminationService
                ]
            })
            .overrideTemplate(MedicalExaminationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedicalExaminationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicalExaminationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MedicalExamination(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.medicalExamination).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
