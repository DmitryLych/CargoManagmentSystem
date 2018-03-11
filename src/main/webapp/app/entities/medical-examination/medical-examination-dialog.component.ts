import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MedicalExamination } from './medical-examination.model';
import { MedicalExaminationPopupService } from './medical-examination-popup.service';
import { MedicalExaminationService } from './medical-examination.service';
import { Driver, DriverService } from '../driver';

@Component({
    selector: 'jhi-medical-examination-dialog',
    templateUrl: './medical-examination-dialog.component.html'
})
export class MedicalExaminationDialogComponent implements OnInit {

    medicalExamination: MedicalExamination;
    isSaving: boolean;

    drivers: Driver[];
    validateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private medicalExaminationService: MedicalExaminationService,
        private driverService: DriverService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.driverService.query()
            .subscribe((res: HttpResponse<Driver[]>) => { this.drivers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.medicalExamination.id !== undefined) {
            this.subscribeToSaveResponse(
                this.medicalExaminationService.update(this.medicalExamination));
        } else {
            this.subscribeToSaveResponse(
                this.medicalExaminationService.create(this.medicalExamination));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MedicalExamination>>) {
        result.subscribe((res: HttpResponse<MedicalExamination>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MedicalExamination) {
        this.eventManager.broadcast({ name: 'medicalExaminationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDriverById(index: number, item: Driver) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-medical-examination-popup',
    template: ''
})
export class MedicalExaminationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private medicalExaminationPopupService: MedicalExaminationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.medicalExaminationPopupService
                    .open(MedicalExaminationDialogComponent as Component, params['id']);
            } else {
                this.medicalExaminationPopupService
                    .open(MedicalExaminationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
