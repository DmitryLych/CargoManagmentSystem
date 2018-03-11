import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Truck } from './truck.model';
import { TruckPopupService } from './truck-popup.service';
import { TruckService } from './truck.service';

@Component({
    selector: 'jhi-truck-delete-dialog',
    templateUrl: './truck-delete-dialog.component.html'
})
export class TruckDeleteDialogComponent {

    truck: Truck;

    constructor(
        private truckService: TruckService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.truckService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'truckListModification',
                content: 'Deleted an truck'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-truck-delete-popup',
    template: ''
})
export class TruckDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private truckPopupService: TruckPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.truckPopupService
                .open(TruckDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
