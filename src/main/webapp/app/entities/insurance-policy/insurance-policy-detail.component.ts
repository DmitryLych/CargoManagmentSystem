import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { InsurancePolicy } from './insurance-policy.model';
import { InsurancePolicyService } from './insurance-policy.service';

@Component({
    selector: 'jhi-insurance-policy-detail',
    templateUrl: './insurance-policy-detail.component.html'
})
export class InsurancePolicyDetailComponent implements OnInit, OnDestroy {

    insurancePolicy: InsurancePolicy;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private insurancePolicyService: InsurancePolicyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInsurancePolicies();
    }

    load(id) {
        this.insurancePolicyService.find(id)
            .subscribe((insurancePolicyResponse: HttpResponse<InsurancePolicy>) => {
                this.insurancePolicy = insurancePolicyResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInsurancePolicies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'insurancePolicyListModification',
            (response) => this.load(this.insurancePolicy.id)
        );
    }
}
