import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Goods } from './goods.model';
import { GoodsPopupService } from './goods-popup.service';
import { GoodsService } from './goods.service';

@Component({
    selector: 'jhi-goods-delete-dialog',
    templateUrl: './goods-delete-dialog.component.html'
})
export class GoodsDeleteDialogComponent {

    goods: Goods;

    constructor(
        private goodsService: GoodsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.goodsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'goodsListModification',
                content: 'Deleted an goods'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-goods-delete-popup',
    template: ''
})
export class GoodsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private goodsPopupService: GoodsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.goodsPopupService
                .open(GoodsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
