import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CargoManagmentSystemSharedModule } from '../../shared';
import {
    DriverLicenseService,
    DriverLicensePopupService,
    DriverLicenseComponent,
    DriverLicenseDetailComponent,
    DriverLicenseDialogComponent,
    DriverLicensePopupComponent,
    DriverLicenseDeletePopupComponent,
    DriverLicenseDeleteDialogComponent,
    driverLicenseRoute,
    driverLicensePopupRoute,
    DriverLicenseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...driverLicenseRoute,
    ...driverLicensePopupRoute,
];

@NgModule({
    imports: [
        CargoManagmentSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DriverLicenseComponent,
        DriverLicenseDetailComponent,
        DriverLicenseDialogComponent,
        DriverLicenseDeleteDialogComponent,
        DriverLicensePopupComponent,
        DriverLicenseDeletePopupComponent,
    ],
    entryComponents: [
        DriverLicenseComponent,
        DriverLicenseDialogComponent,
        DriverLicensePopupComponent,
        DriverLicenseDeleteDialogComponent,
        DriverLicenseDeletePopupComponent,
    ],
    providers: [
        DriverLicenseService,
        DriverLicensePopupService,
        DriverLicenseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoManagmentSystemDriverLicenseModule {}
