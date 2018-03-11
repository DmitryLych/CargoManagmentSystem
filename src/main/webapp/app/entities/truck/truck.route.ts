import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TruckComponent } from './truck.component';
import { TruckDetailComponent } from './truck-detail.component';
import { TruckPopupComponent } from './truck-dialog.component';
import { TruckDeletePopupComponent } from './truck-delete-dialog.component';

@Injectable()
export class TruckResolvePagingParams implements Resolve<any> {

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

export const truckRoute: Routes = [
    {
        path: 'truck',
        component: TruckComponent,
        resolve: {
            'pagingParams': TruckResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.truck.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'truck/:id',
        component: TruckDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.truck.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const truckPopupRoute: Routes = [
    {
        path: 'truck-new',
        component: TruckPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.truck.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'truck/:id/edit',
        component: TruckPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.truck.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'truck/:id/delete',
        component: TruckDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.truck.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
