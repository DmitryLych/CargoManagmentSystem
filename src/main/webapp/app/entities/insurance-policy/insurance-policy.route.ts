import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { InsurancePolicyComponent } from './insurance-policy.component';
import { InsurancePolicyDetailComponent } from './insurance-policy-detail.component';
import { InsurancePolicyPopupComponent } from './insurance-policy-dialog.component';
import { InsurancePolicyDeletePopupComponent } from './insurance-policy-delete-dialog.component';

@Injectable()
export class InsurancePolicyResolvePagingParams implements Resolve<any> {

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

export const insurancePolicyRoute: Routes = [
    {
        path: 'insurance-policy',
        component: InsurancePolicyComponent,
        resolve: {
            'pagingParams': InsurancePolicyResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.insurancePolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'insurance-policy/:id',
        component: InsurancePolicyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.insurancePolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const insurancePolicyPopupRoute: Routes = [
    {
        path: 'insurance-policy-new',
        component: InsurancePolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.insurancePolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'insurance-policy/:id/edit',
        component: InsurancePolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.insurancePolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'insurance-policy/:id/delete',
        component: InsurancePolicyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.insurancePolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
