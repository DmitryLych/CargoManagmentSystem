import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TrailerComponent } from './trailer.component';
import { TrailerDetailComponent } from './trailer-detail.component';
import { TrailerPopupComponent } from './trailer-dialog.component';
import { TrailerDeletePopupComponent } from './trailer-delete-dialog.component';

@Injectable()
export class TrailerResolvePagingParams implements Resolve<any> {

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

export const trailerRoute: Routes = [
    {
        path: 'trailer',
        component: TrailerComponent,
        resolve: {
            'pagingParams': TrailerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.trailer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'trailer/:id',
        component: TrailerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.trailer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const trailerPopupRoute: Routes = [
    {
        path: 'trailer-new',
        component: TrailerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.trailer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'trailer/:id/edit',
        component: TrailerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.trailer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'trailer/:id/delete',
        component: TrailerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.trailer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
