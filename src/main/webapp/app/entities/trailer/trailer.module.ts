import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CargoManagmentSystemSharedModule } from '../../shared';
import {
    TrailerService,
    TrailerPopupService,
    TrailerComponent,
    TrailerDetailComponent,
    TrailerDialogComponent,
    TrailerPopupComponent,
    TrailerDeletePopupComponent,
    TrailerDeleteDialogComponent,
    trailerRoute,
    trailerPopupRoute,
    TrailerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...trailerRoute,
    ...trailerPopupRoute,
];

@NgModule({
    imports: [
        CargoManagmentSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TrailerComponent,
        TrailerDetailComponent,
        TrailerDialogComponent,
        TrailerDeleteDialogComponent,
        TrailerPopupComponent,
        TrailerDeletePopupComponent,
    ],
    entryComponents: [
        TrailerComponent,
        TrailerDialogComponent,
        TrailerPopupComponent,
        TrailerDeleteDialogComponent,
        TrailerDeletePopupComponent,
    ],
    providers: [
        TrailerService,
        TrailerPopupService,
        TrailerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoManagmentSystemTrailerModule {}
