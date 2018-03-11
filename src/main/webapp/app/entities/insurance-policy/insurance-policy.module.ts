import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CargoManagmentSystemSharedModule } from '../../shared';
import {
    InsurancePolicyService,
    InsurancePolicyPopupService,
    InsurancePolicyComponent,
    InsurancePolicyDetailComponent,
    InsurancePolicyDialogComponent,
    InsurancePolicyPopupComponent,
    InsurancePolicyDeletePopupComponent,
    InsurancePolicyDeleteDialogComponent,
    insurancePolicyRoute,
    insurancePolicyPopupRoute,
    InsurancePolicyResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...insurancePolicyRoute,
    ...insurancePolicyPopupRoute,
];

@NgModule({
    imports: [
        CargoManagmentSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InsurancePolicyComponent,
        InsurancePolicyDetailComponent,
        InsurancePolicyDialogComponent,
        InsurancePolicyDeleteDialogComponent,
        InsurancePolicyPopupComponent,
        InsurancePolicyDeletePopupComponent,
    ],
    entryComponents: [
        InsurancePolicyComponent,
        InsurancePolicyDialogComponent,
        InsurancePolicyPopupComponent,
        InsurancePolicyDeleteDialogComponent,
        InsurancePolicyDeletePopupComponent,
    ],
    providers: [
        InsurancePolicyService,
        InsurancePolicyPopupService,
        InsurancePolicyResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoManagmentSystemInsurancePolicyModule {}
