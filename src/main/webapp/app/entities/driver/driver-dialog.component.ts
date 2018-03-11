import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Driver } from './driver.model';
import { DriverPopupService } from './driver-popup.service';
import { DriverService } from './driver.service';
import { DriverLicense, DriverLicenseService } from '../driver-license';
import { MedicalExamination, MedicalExaminationService } from '../medical-examination';
import { Truck, TruckService } from '../truck';
import { Company, CompanyService } from '../company';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-driver-dialog',
    templateUrl: './driver-dialog.component.html'
})
export class DriverDialogComponent implements OnInit {

    driver: Driver;
    isSaving: boolean;

    driverlicenses: DriverLicense[];

    medicalexaminations: MedicalExamination[];

    trucks: Truck[];

    companies: Company[];

    users: User[];
    yearOfIssueDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private driverService: DriverService,
        private driverLicenseService: DriverLicenseService,
        private medicalExaminationService: MedicalExaminationService,
        private truckService: TruckService,
        private companyService: CompanyService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.driverLicenseService
            .query({filter: 'driver-is-null'})
            .subscribe((res: HttpResponse<DriverLicense[]>) => {
                if (!this.driver.driverLicenseId) {
                    this.driverlicenses = res.body;
                } else {
                    this.driverLicenseService
                        .find(this.driver.driverLicenseId)
                        .subscribe((subRes: HttpResponse<DriverLicense>) => {
                            this.driverlicenses = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.medicalExaminationService
            .query({filter: 'driver-is-null'})
            .subscribe((res: HttpResponse<MedicalExamination[]>) => {
                if (!this.driver.medicalExaminationId) {
                    this.medicalexaminations = res.body;
                } else {
                    this.medicalExaminationService
                        .find(this.driver.medicalExaminationId)
                        .subscribe((subRes: HttpResponse<MedicalExamination>) => {
                            this.medicalexaminations = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.truckService
            .query({filter: 'driver-is-null'})
            .subscribe((res: HttpResponse<Truck[]>) => {
                if (!this.driver.truckId) {
                    this.trucks = res.body;
                } else {
                    this.truckService
                        .find(this.driver.truckId)
                        .subscribe((subRes: HttpResponse<Truck>) => {
                            this.trucks = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.companyService.query()
            .subscribe((res: HttpResponse<Company[]>) => { this.companies = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.driver.id !== undefined) {
            this.subscribeToSaveResponse(
                this.driverService.update(this.driver));
        } else {
            this.subscribeToSaveResponse(
                this.driverService.create(this.driver));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Driver>>) {
        result.subscribe((res: HttpResponse<Driver>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Driver) {
        this.eventManager.broadcast({ name: 'driverListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDriverLicenseById(index: number, item: DriverLicense) {
        return item.id;
    }

    trackMedicalExaminationById(index: number, item: MedicalExamination) {
        return item.id;
    }

    trackTruckById(index: number, item: Truck) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-driver-popup',
    template: ''
})
export class DriverPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private driverPopupService: DriverPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.driverPopupService
                    .open(DriverDialogComponent as Component, params['id']);
            } else {
                this.driverPopupService
                    .open(DriverDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
