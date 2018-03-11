import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Trailer } from './trailer.model';
import { TrailerPopupService } from './trailer-popup.service';
import { TrailerService } from './trailer.service';
import { Truck, TruckService } from '../truck';

@Component({
    selector: 'jhi-trailer-dialog',
    templateUrl: './trailer-dialog.component.html'
})
export class TrailerDialogComponent implements OnInit {

    trailer: Trailer;
    isSaving: boolean;

    trucks: Truck[];
    yearOfIssueDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private trailerService: TrailerService,
        private truckService: TruckService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.truckService.query()
            .subscribe((res: HttpResponse<Truck[]>) => { this.trucks = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.trailer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.trailerService.update(this.trailer));
        } else {
            this.subscribeToSaveResponse(
                this.trailerService.create(this.trailer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Trailer>>) {
        result.subscribe((res: HttpResponse<Trailer>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Trailer) {
        this.eventManager.broadcast({ name: 'trailerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTruckById(index: number, item: Truck) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-trailer-popup',
    template: ''
})
export class TrailerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private trailerPopupService: TrailerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.trailerPopupService
                    .open(TrailerDialogComponent as Component, params['id']);
            } else {
                this.trailerPopupService
                    .open(TrailerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
