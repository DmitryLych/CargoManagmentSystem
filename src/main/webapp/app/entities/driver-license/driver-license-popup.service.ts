import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DriverLicense } from './driver-license.model';
import { DriverLicenseService } from './driver-license.service';

@Injectable()
export class DriverLicensePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private driverLicenseService: DriverLicenseService

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
                this.driverLicenseService.find(id)
                    .subscribe((driverLicenseResponse: HttpResponse<DriverLicense>) => {
                        const driverLicense: DriverLicense = driverLicenseResponse.body;
                        if (driverLicense.validate) {
                            driverLicense.validate = {
                                year: driverLicense.validate.getFullYear(),
                                month: driverLicense.validate.getMonth() + 1,
                                day: driverLicense.validate.getDate()
                            };
                        }
                        this.ngbModalRef = this.driverLicenseModalRef(component, driverLicense);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.driverLicenseModalRef(component, new DriverLicense());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    driverLicenseModalRef(component: Component, driverLicense: DriverLicense): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.driverLicense = driverLicense;
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
