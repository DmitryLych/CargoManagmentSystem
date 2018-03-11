import { BaseEntity } from './../../shared';

export class MedicalExamination implements BaseEntity {
    constructor(
        public id?: number,
        public validate?: any,
        public driverId?: number,
    ) {
    }
}
