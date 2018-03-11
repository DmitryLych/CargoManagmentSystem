import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DriverLicense } from './driver-license.model';
import { DriverLicensePopupService } from './driver-license-popup.service';
import { DriverLicenseService } from './driver-license.service';
import { Driver, DriverService } from '../driver';

@Component({
    selector: 'jhi-driver-license-dialog',
    templateUrl: './driver-license-dialog.component.html'
})
export class DriverLicenseDialogComponent implements OnInit {

    driverLicense: DriverLicense;
    isSaving: boolean;

    drivers: Driver[];
    validateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private driverLicenseService: DriverLicenseService,
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
        if (this.driverLicense.id !== undefined) {
            this.subscribeToSaveResponse(
                this.driverLicenseService.update(this.driverLicense));
        } else {
            this.subscribeToSaveResponse(
                this.driverLicenseService.create(this.driverLicense));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DriverLicense>>) {
        result.subscribe((res: HttpResponse<DriverLicense>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DriverLicense) {
        this.eventManager.broadcast({ name: 'driverLicenseListModification', content: 'OK'});
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
    selector: 'jhi-driver-license-popup',
    template: ''
})
export class DriverLicensePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private driverLicensePopupService: DriverLicensePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.driverLicensePopupService
                    .open(DriverLicenseDialogComponent as Component, params['id']);
            } else {
                this.driverLicensePopupService
                    .open(DriverLicenseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
