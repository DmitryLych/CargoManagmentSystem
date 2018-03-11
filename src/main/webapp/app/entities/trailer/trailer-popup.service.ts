import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Trailer } from './trailer.model';
import { TrailerService } from './trailer.service';

@Injectable()
export class TrailerPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private trailerService: TrailerService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.trailerService.find(id)
                    .subscribe((trailerResponse: HttpResponse<Trailer>) => {
                        const trailer: Trailer = trailerResponse.body;
                        if (trailer.yearOfIssue) {
                            trailer.yearOfIssue = {
                                year: trailer.yearOfIssue.getFullYear(),
                                month: trailer.yearOfIssue.getMonth() + 1,
                                day: trailer.yearOfIssue.getDate()
                            };
                        }
                        this.ngbModalRef = this.trailerModalRef(component, trailer);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.trailerModalRef(component, new Trailer());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    trailerModalRef(component: Component, trailer: Trailer): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.trailer = trailer;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
