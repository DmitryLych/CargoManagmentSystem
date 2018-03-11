import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { MedicalExamination } from './medical-examination.model';
import { MedicalExaminationService } from './medical-examination.service';

@Injectable()
export class MedicalExaminationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private medicalExaminationService: MedicalExaminationService

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
                this.medicalExaminationService.find(id)
                    .subscribe((medicalExaminationResponse: HttpResponse<MedicalExamination>) => {
                        const medicalExamination: MedicalExamination = medicalExaminationResponse.body;
                        if (medicalExamination.validate) {
                            medicalExamination.validate = {
                                year: medicalExamination.validate.getFullYear(),
                                month: medicalExamination.validate.getMonth() + 1,
                                day: medicalExamination.validate.getDate()
                            };
                        }
                        this.ngbModalRef = this.medicalExaminationModalRef(component, medicalExamination);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.medicalExaminationModalRef(component, new MedicalExamination());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    medicalExaminationModalRef(component: Component, medicalExamination: MedicalExamination): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.medicalExamination = medicalExamination;
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
