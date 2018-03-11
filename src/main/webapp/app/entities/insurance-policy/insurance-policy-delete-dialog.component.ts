import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InsurancePolicy } from './insurance-policy.model';
import { InsurancePolicyPopupService } from './insurance-policy-popup.service';
import { InsurancePolicyService } from './insurance-policy.service';

@Component({
    selector: 'jhi-insurance-policy-delete-dialog',
    templateUrl: './insurance-policy-delete-dialog.component.html'
})
export class InsurancePolicyDeleteDialogComponent {

    insurancePolicy: InsurancePolicy;

    constructor(
        private insurancePolicyService: InsurancePolicyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.insurancePolicyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'insurancePolicyListModification',
                content: 'Deleted an insurancePolicy'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-insurance-policy-delete-popup',
    template: ''
})
export class InsurancePolicyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private insurancePolicyPopupService: InsurancePolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.insurancePolicyPopupService
                .open(InsurancePolicyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
