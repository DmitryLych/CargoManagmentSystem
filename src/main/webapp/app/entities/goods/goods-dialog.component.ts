import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Goods } from './goods.model';
import { GoodsPopupService } from './goods-popup.service';
import { GoodsService } from './goods.service';
import { Order, OrderService } from '../order';

@Component({
    selector: 'jhi-goods-dialog',
    templateUrl: './goods-dialog.component.html'
})
export class GoodsDialogComponent implements OnInit {

    goods: Goods;
    isSaving: boolean;

    orders: Order[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private goodsService: GoodsService,
        private orderService: OrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.orderService.query()
            .subscribe((res: HttpResponse<Order[]>) => { this.orders = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.goods.id !== undefined) {
            this.subscribeToSaveResponse(
                this.goodsService.update(this.goods));
        } else {
            this.subscribeToSaveResponse(
                this.goodsService.create(this.goods));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Goods>>) {
        result.subscribe((res: HttpResponse<Goods>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Goods) {
        this.eventManager.broadcast({ name: 'goodsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOrderById(index: number, item: Order) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-goods-popup',
    template: ''
})
export class GoodsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private goodsPopupService: GoodsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.goodsPopupService
                    .open(GoodsDialogComponent as Component, params['id']);
            } else {
                this.goodsPopupService
                    .open(GoodsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
