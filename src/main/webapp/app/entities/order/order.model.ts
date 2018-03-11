import { BaseEntity } from './../../shared';

export class Order implements BaseEntity {
    constructor(
        public id?: number,
        public cost?: number,
        public downloadAddress?: string,
        public unloadingAddress?: string,
        public issued?: boolean,
        public completed?: boolean,
        public paid?: boolean,
        public customerId?: number,
        public goods?: BaseEntity[],
        public driverId?: number,
    ) {
        this.issued = false;
        this.completed = false;
        this.paid = false;
    }
}
