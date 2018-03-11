import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MedicalExaminationComponent } from './medical-examination.component';
import { MedicalExaminationDetailComponent } from './medical-examination-detail.component';
import { MedicalExaminationPopupComponent } from './medical-examination-dialog.component';
import { MedicalExaminationDeletePopupComponent } from './medical-examination-delete-dialog.component';

@Injectable()
export class MedicalExaminationResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const medicalExaminationRoute: Routes = [
    {
        path: 'medical-examination',
        component: MedicalExaminationComponent,
        resolve: {
            'pagingParams': MedicalExaminationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.medicalExamination.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'medical-examination/:id',
        component: MedicalExaminationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.medicalExamination.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const medicalExaminationPopupRoute: Routes = [
    {
        path: 'medical-examination-new',
        component: MedicalExaminationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.medicalExamination.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'medical-examination/:id/edit',
        component: MedicalExaminationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.medicalExamination.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'medical-examination/:id/delete',
        component: MedicalExaminationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.medicalExamination.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
