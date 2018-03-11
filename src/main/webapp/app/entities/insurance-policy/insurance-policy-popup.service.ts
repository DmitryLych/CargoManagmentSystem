import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { InsurancePolicy } from './insurance-policy.model';
import { InsurancePolicyService } from './insurance-policy.service';

@Injectable()
export class InsurancePolicyPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private insurancePolicyService: InsurancePolicyService

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
                this.insurancePolicyService.find(id)
                    .subscribe((insurancePolicyResponse: HttpResponse<InsurancePolicy>) => {
                        const insurancePolicy: InsurancePolicy = insurancePolicyResponse.body;
                        if (insurancePolicy.validate) {
                            insurancePolicy.validate = {
                                year: insurancePolicy.validate.getFullYear(),
                                month: insurancePolicy.validate.getMonth() + 1,
                                day: insurancePolicy.validate.getDate()
                            };
                        }
                        this.ngbModalRef = this.insurancePolicyModalRef(component, insurancePolicy);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.insurancePolicyModalRef(component, new InsurancePolicy());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    insurancePolicyModalRef(component: Component, insurancePolicy: InsurancePolicy): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.insurancePolicy = insurancePolicy;
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
