import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Truck } from './truck.model';
import { TruckService } from './truck.service';

@Injectable()
export class TruckPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private truckService: TruckService

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
                this.truckService.find(id)
                    .subscribe((truckResponse: HttpResponse<Truck>) => {
                        const truck: Truck = truckResponse.body;
                        if (truck.yearOfIssue) {
                            truck.yearOfIssue = {
                                year: truck.yearOfIssue.getFullYear(),
                                month: truck.yearOfIssue.getMonth() + 1,
                                day: truck.yearOfIssue.getDate()
                            };
                        }
                        this.ngbModalRef = this.truckModalRef(component, truck);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.truckModalRef(component, new Truck());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    truckModalRef(component: Component, truck: Truck): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.truck = truck;
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
