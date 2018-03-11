import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CargoManagmentSystemCompanyModule } from './company/company.module';
import { CargoManagmentSystemCustomerModule } from './customer/customer.module';
import { CargoManagmentSystemDriverModule } from './driver/driver.module';
import { CargoManagmentSystemDriverLicenseModule } from './driver-license/driver-license.module';
import { CargoManagmentSystemGoodsModule } from './goods/goods.module';
import { CargoManagmentSystemInsurancePolicyModule } from './insurance-policy/insurance-policy.module';
import { CargoManagmentSystemTruckModule } from './truck/truck.module';
import { CargoManagmentSystemTrailerModule } from './trailer/trailer.module';
import { CargoManagmentSystemOrderModule } from './order/order.module';
import { CargoManagmentSystemMedicalExaminationModule } from './medical-examination/medical-examination.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CargoManagmentSystemCompanyModule,
        CargoManagmentSystemCustomerModule,
        CargoManagmentSystemDriverModule,
        CargoManagmentSystemDriverLicenseModule,
        CargoManagmentSystemGoodsModule,
        CargoManagmentSystemInsurancePolicyModule,
        CargoManagmentSystemTruckModule,
        CargoManagmentSystemTrailerModule,
        CargoManagmentSystemOrderModule,
        CargoManagmentSystemMedicalExaminationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoManagmentSystemEntityModule {}
