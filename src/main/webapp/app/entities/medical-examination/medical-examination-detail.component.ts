import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MedicalExamination } from './medical-examination.model';
import { MedicalExaminationService } from './medical-examination.service';

@Component({
    selector: 'jhi-medical-examination-detail',
    templateUrl: './medical-examination-detail.component.html'
})
export class MedicalExaminationDetailComponent implements OnInit, OnDestroy {

    medicalExamination: MedicalExamination;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private medicalExaminationService: MedicalExaminationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMedicalExaminations();
    }

    load(id) {
        this.medicalExaminationService.find(id)
            .subscribe((medicalExaminationResponse: HttpResponse<MedicalExamination>) => {
                this.medicalExamination = medicalExaminationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMedicalExaminations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'medicalExaminationListModification',
            (response) => this.load(this.medicalExamination.id)
        );
    }
}
