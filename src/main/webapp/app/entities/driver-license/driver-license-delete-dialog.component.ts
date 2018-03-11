import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DriverLicense } from './driver-license.model';
import { DriverLicensePopupService } from './driver-license-popup.service';
import { DriverLicenseService } from './driver-license.service';

@Component({
    selector: 'jhi-driver-license-delete-dialog',
    templateUrl: './driver-license-delete-dialog.component.html'
})
export class DriverLicenseDeleteDialogComponent {

    driverLicense: DriverLicense;

    constructor(
        private driverLicenseService: DriverLicenseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.driverLicenseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'driverLicenseListModification',
                content: 'Deleted an driverLicense'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-driver-license-delete-popup',
    template: ''
})
export class DriverLicenseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private driverLicensePopupService: DriverLicensePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.driverLicensePopupService
                .open(DriverLicenseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
