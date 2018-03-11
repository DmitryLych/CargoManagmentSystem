import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DriverLicenseComponent } from './driver-license.component';
import { DriverLicenseDetailComponent } from './driver-license-detail.component';
import { DriverLicensePopupComponent } from './driver-license-dialog.component';
import { DriverLicenseDeletePopupComponent } from './driver-license-delete-dialog.component';

@Injectable()
export class DriverLicenseResolvePagingParams implements Resolve<any> {

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

export const driverLicenseRoute: Routes = [
    {
        path: 'driver-license',
        component: DriverLicenseComponent,
        resolve: {
            'pagingParams': DriverLicenseResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.driverLicense.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'driver-license/:id',
        component: DriverLicenseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.driverLicense.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const driverLicensePopupRoute: Routes = [
    {
        path: 'driver-license-new',
        component: DriverLicensePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.driverLicense.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'driver-license/:id/edit',
        component: DriverLicensePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.driverLicense.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'driver-license/:id/delete',
        component: DriverLicenseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.driverLicense.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
