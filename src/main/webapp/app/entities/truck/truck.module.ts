import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CargoManagmentSystemSharedModule } from '../../shared';
import {
    TruckService,
    TruckPopupService,
    TruckComponent,
    TruckDetailComponent,
    TruckDialogComponent,
    TruckPopupComponent,
    TruckDeletePopupComponent,
    TruckDeleteDialogComponent,
    truckRoute,
    truckPopupRoute,
    TruckResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...truckRoute,
    ...truckPopupRoute,
];

@NgModule({
    imports: [
        CargoManagmentSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TruckComponent,
        TruckDetailComponent,
        TruckDialogComponent,
        TruckDeleteDialogComponent,
        TruckPopupComponent,
        TruckDeletePopupComponent,
    ],
    entryComponents: [
        TruckComponent,
        TruckDialogComponent,
        TruckPopupComponent,
        TruckDeleteDialogComponent,
        TruckDeletePopupComponent,
    ],
    providers: [
        TruckService,
        TruckPopupService,
        TruckResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoManagmentSystemTruckModule {}
