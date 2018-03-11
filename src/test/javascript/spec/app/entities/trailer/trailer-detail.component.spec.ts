/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { TrailerDetailComponent } from '../../../../../../main/webapp/app/entities/trailer/trailer-detail.component';
import { TrailerService } from '../../../../../../main/webapp/app/entities/trailer/trailer.service';
import { Trailer } from '../../../../../../main/webapp/app/entities/trailer/trailer.model';

describe('Component Tests', () => {

    describe('Trailer Management Detail Component', () => {
        let comp: TrailerDetailComponent;
        let fixture: ComponentFixture<TrailerDetailComponent>;
        let service: TrailerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [TrailerDetailComponent],
                providers: [
                    TrailerService
                ]
            })
            .overrideTemplate(TrailerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TrailerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrailerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Trailer(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.trailer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
