import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CargoManagmentSystemSharedModule } from '../../shared';
import {
    GoodsService,
    GoodsPopupService,
    GoodsComponent,
    GoodsDetailComponent,
    GoodsDialogComponent,
    GoodsPopupComponent,
    GoodsDeletePopupComponent,
    GoodsDeleteDialogComponent,
    goodsRoute,
    goodsPopupRoute,
    GoodsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...goodsRoute,
    ...goodsPopupRoute,
];

@NgModule({
    imports: [
        CargoManagmentSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GoodsComponent,
        GoodsDetailComponent,
        GoodsDialogComponent,
        GoodsDeleteDialogComponent,
        GoodsPopupComponent,
        GoodsDeletePopupComponent,
    ],
    entryComponents: [
        GoodsComponent,
        GoodsDialogComponent,
        GoodsPopupComponent,
        GoodsDeleteDialogComponent,
        GoodsDeletePopupComponent,
    ],
    providers: [
        GoodsService,
        GoodsPopupService,
        GoodsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoManagmentSystemGoodsModule {}
