import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Truck } from './truck.model';
import { TruckPopupService } from './truck-popup.service';
import { TruckService } from './truck.service';
import { Trailer, TrailerService } from '../trailer';

@Component({
    selector: 'jhi-truck-dialog',
    templateUrl: './truck-dialog.component.html'
})
export class TruckDialogComponent implements OnInit {

    truck: Truck;
    isSaving: boolean;

    trailers: Trailer[];
    yearOfIssueDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private truckService: TruckService,
        private trailerService: TrailerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.trailerService
            .query({filter: 'truck-is-null'})
            .subscribe((res: HttpResponse<Trailer[]>) => {
                if (!this.truck.trailerId) {
                    this.trailers = res.body;
                } else {
                    this.trailerService
                        .find(this.truck.trailerId)
                        .subscribe((subRes: HttpResponse<Trailer>) => {
                            this.trailers = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.truck.id !== undefined) {
            this.subscribeToSaveResponse(
                this.truckService.update(this.truck));
        } else {
            this.subscribeToSaveResponse(
                this.truckService.create(this.truck));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Truck>>) {
        result.subscribe((res: HttpResponse<Truck>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Truck) {
        this.eventManager.broadcast({ name: 'truckListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTrailerById(index: number, item: Trailer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-truck-popup',
    template: ''
})
export class TruckPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private truckPopupService: TruckPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.truckPopupService
                    .open(TruckDialogComponent as Component, params['id']);
            } else {
                this.truckPopupService
                    .open(TruckDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
