import { BaseEntity } from './../../shared';

export class InsurancePolicy implements BaseEntity {
    constructor(
        public id?: number,
        public validate?: any,
        public type?: string,
        public cost?: number,
        public driverId?: number,
    ) {
    }
}
