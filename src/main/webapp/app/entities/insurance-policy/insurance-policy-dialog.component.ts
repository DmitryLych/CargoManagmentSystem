import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InsurancePolicy } from './insurance-policy.model';
import { InsurancePolicyPopupService } from './insurance-policy-popup.service';
import { InsurancePolicyService } from './insurance-policy.service';
import { Driver, DriverService } from '../driver';

@Component({
    selector: 'jhi-insurance-policy-dialog',
    templateUrl: './insurance-policy-dialog.component.html'
})
export class InsurancePolicyDialogComponent implements OnInit {

    insurancePolicy: InsurancePolicy;
    isSaving: boolean;

    drivers: Driver[];
    validateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private insurancePolicyService: InsurancePolicyService,
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
        if (this.insurancePolicy.id !== undefined) {
            this.subscribeToSaveResponse(
                this.insurancePolicyService.update(this.insurancePolicy));
        } else {
            this.subscribeToSaveResponse(
                this.insurancePolicyService.create(this.insurancePolicy));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<InsurancePolicy>>) {
        result.subscribe((res: HttpResponse<InsurancePolicy>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: InsurancePolicy) {
        this.eventManager.broadcast({ name: 'insurancePolicyListModification', content: 'OK'});
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
    selector: 'jhi-insurance-policy-popup',
    template: ''
})
export class InsurancePolicyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private insurancePolicyPopupService: InsurancePolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.insurancePolicyPopupService
                    .open(InsurancePolicyDialogComponent as Component, params['id']);
            } else {
                this.insurancePolicyPopupService
                    .open(InsurancePolicyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
