import { BaseEntity } from './../../shared';

export class DriverLicense implements BaseEntity {
    constructor(
        public id?: number,
        public category?: string,
        public validate?: any,
        public specialNotes?: string,
        public driverId?: number,
    ) {
    }
}
