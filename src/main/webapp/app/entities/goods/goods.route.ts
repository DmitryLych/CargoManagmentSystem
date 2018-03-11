import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { GoodsComponent } from './goods.component';
import { GoodsDetailComponent } from './goods-detail.component';
import { GoodsPopupComponent } from './goods-dialog.component';
import { GoodsDeletePopupComponent } from './goods-delete-dialog.component';

@Injectable()
export class GoodsResolvePagingParams implements Resolve<any> {

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

export const goodsRoute: Routes = [
    {
        path: 'goods',
        component: GoodsComponent,
        resolve: {
            'pagingParams': GoodsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.goods.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'goods/:id',
        component: GoodsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.goods.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const goodsPopupRoute: Routes = [
    {
        path: 'goods-new',
        component: GoodsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.goods.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'goods/:id/edit',
        component: GoodsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.goods.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'goods/:id/delete',
        component: GoodsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cargoManagmentSystemApp.goods.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
