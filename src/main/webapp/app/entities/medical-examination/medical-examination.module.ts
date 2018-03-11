import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CargoManagmentSystemSharedModule } from '../../shared';
import {
    MedicalExaminationService,
    MedicalExaminationPopupService,
    MedicalExaminationComponent,
    MedicalExaminationDetailComponent,
    MedicalExaminationDialogComponent,
    MedicalExaminationPopupComponent,
    MedicalExaminationDeletePopupComponent,
    MedicalExaminationDeleteDialogComponent,
    medicalExaminationRoute,
    medicalExaminationPopupRoute,
    MedicalExaminationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...medicalExaminationRoute,
    ...medicalExaminationPopupRoute,
];

@NgModule({
    imports: [
        CargoManagmentSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MedicalExaminationComponent,
        MedicalExaminationDetailComponent,
        MedicalExaminationDialogComponent,
        MedicalExaminationDeleteDialogComponent,
        MedicalExaminationPopupComponent,
        MedicalExaminationDeletePopupComponent,
    ],
    entryComponents: [
        MedicalExaminationComponent,
        MedicalExaminationDialogComponent,
        MedicalExaminationPopupComponent,
        MedicalExaminationDeleteDialogComponent,
        MedicalExaminationDeletePopupComponent,
    ],
    providers: [
        MedicalExaminationService,
        MedicalExaminationPopupService,
        MedicalExaminationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoManagmentSystemMedicalExaminationModule {}
