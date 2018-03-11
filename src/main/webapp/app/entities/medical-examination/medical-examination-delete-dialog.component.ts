import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MedicalExamination } from './medical-examination.model';
import { MedicalExaminationPopupService } from './medical-examination-popup.service';
import { MedicalExaminationService } from './medical-examination.service';

@Component({
    selector: 'jhi-medical-examination-delete-dialog',
    templateUrl: './medical-examination-delete-dialog.component.html'
})
export class MedicalExaminationDeleteDialogComponent {

    medicalExamination: MedicalExamination;

    constructor(
        private medicalExaminationService: MedicalExaminationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.medicalExaminationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'medicalExaminationListModification',
                content: 'Deleted an medicalExamination'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-medical-examination-delete-popup',
    template: ''
})
export class MedicalExaminationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private medicalExaminationPopupService: MedicalExaminationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.medicalExaminationPopupService
                .open(MedicalExaminationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
