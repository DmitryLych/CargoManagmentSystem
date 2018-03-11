/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { MedicalExaminationComponent } from '../../../../../../main/webapp/app/entities/medical-examination/medical-examination.component';
import { MedicalExaminationService } from '../../../../../../main/webapp/app/entities/medical-examination/medical-examination.service';
import { MedicalExamination } from '../../../../../../main/webapp/app/entities/medical-examination/medical-examination.model';

describe('Component Tests', () => {

    describe('MedicalExamination Management Component', () => {
        let comp: MedicalExaminationComponent;
        let fixture: ComponentFixture<MedicalExaminationComponent>;
        let service: MedicalExaminationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [MedicalExaminationComponent],
                providers: [
                    MedicalExaminationService
                ]
            })
            .overrideTemplate(MedicalExaminationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedicalExaminationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicalExaminationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MedicalExamination(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.medicalExaminations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
