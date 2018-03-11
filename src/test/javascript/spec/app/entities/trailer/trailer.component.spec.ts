/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CargoManagmentSystemTestModule } from '../../../test.module';
import { TrailerComponent } from '../../../../../../main/webapp/app/entities/trailer/trailer.component';
import { TrailerService } from '../../../../../../main/webapp/app/entities/trailer/trailer.service';
import { Trailer } from '../../../../../../main/webapp/app/entities/trailer/trailer.model';

describe('Component Tests', () => {

    describe('Trailer Management Component', () => {
        let comp: TrailerComponent;
        let fixture: ComponentFixture<TrailerComponent>;
        let service: TrailerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CargoManagmentSystemTestModule],
                declarations: [TrailerComponent],
                providers: [
                    TrailerService
                ]
            })
            .overrideTemplate(TrailerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TrailerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrailerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Trailer(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.trailers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
