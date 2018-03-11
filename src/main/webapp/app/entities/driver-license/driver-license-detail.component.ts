import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DriverLicense } from './driver-license.model';
import { DriverLicenseService } from './driver-license.service';

@Component({
    selector: 'jhi-driver-license-detail',
    templateUrl: './driver-license-detail.component.html'
})
export class DriverLicenseDetailComponent implements OnInit, OnDestroy {

    driverLicense: DriverLicense;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private driverLicenseService: DriverLicenseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDriverLicenses();
    }

    load(id) {
        this.driverLicenseService.find(id)
            .subscribe((driverLicenseResponse: HttpResponse<DriverLicense>) => {
                this.driverLicense = driverLicenseResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDriverLicenses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'driverLicenseListModification',
            (response) => this.load(this.driverLicense.id)
        );
    }
}
