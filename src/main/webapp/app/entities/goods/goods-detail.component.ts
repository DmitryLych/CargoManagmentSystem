import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Goods } from './goods.model';
import { GoodsService } from './goods.service';

@Component({
    selector: 'jhi-goods-detail',
    templateUrl: './goods-detail.component.html'
})
export class GoodsDetailComponent implements OnInit, OnDestroy {

    goods: Goods;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private goodsService: GoodsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGoods();
    }

    load(id) {
        this.goodsService.find(id)
            .subscribe((goodsResponse: HttpResponse<Goods>) => {
                this.goods = goodsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGoods() {
        this.eventSubscriber = this.eventManager.subscribe(
            'goodsListModification',
            (response) => this.load(this.goods.id)
        );
    }
}
