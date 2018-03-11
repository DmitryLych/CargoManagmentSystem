import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Trailer } from './trailer.model';
import { TrailerPopupService } from './trailer-popup.service';
import { TrailerService } from './trailer.service';

@Component({
    selector: 'jhi-trailer-delete-dialog',
    templateUrl: './trailer-delete-dialog.component.html'
})
export class TrailerDeleteDialogComponent {

    trailer: Trailer;

    constructor(
        private trailerService: TrailerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.trailerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'trailerListModification',
                content: 'Deleted an trailer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-trailer-delete-popup',
    template: ''
})
export class TrailerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private trailerPopupService: TrailerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.trailerPopupService
                .open(TrailerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
