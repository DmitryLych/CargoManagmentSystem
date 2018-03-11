import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Truck } from './truck.model';
import { TruckService } from './truck.service';

@Component({
    selector: 'jhi-truck-detail',
    templateUrl: './truck-detail.component.html'
})
export class TruckDetailComponent implements OnInit, OnDestroy {

    truck: Truck;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private truckService: TruckService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTrucks();
    }

    load(id) {
        this.truckService.find(id)
            .subscribe((truckResponse: HttpResponse<Truck>) => {
                this.truck = truckResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTrucks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'truckListModification',
            (response) => this.load(this.truck.id)
        );
    }
}
