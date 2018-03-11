import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Trailer } from './trailer.model';
import { TrailerService } from './trailer.service';

@Component({
    selector: 'jhi-trailer-detail',
    templateUrl: './trailer-detail.component.html'
})
export class TrailerDetailComponent implements OnInit, OnDestroy {

    trailer: Trailer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private trailerService: TrailerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTrailers();
    }

    load(id) {
        this.trailerService.find(id)
            .subscribe((trailerResponse: HttpResponse<Trailer>) => {
                this.trailer = trailerResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTrailers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'trailerListModification',
            (response) => this.load(this.trailer.id)
        );
    }
}
