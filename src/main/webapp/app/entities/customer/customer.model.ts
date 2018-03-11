import { BaseEntity } from './../../shared';

export class Customer implements BaseEntity {
    constructor(
        public id?: number,
        public address?: string,
        public customerName?: string,
        public email?: string,
        public companyTelephoneNumber?: string,
        public mobileTelephoneNumber?: string,
        public orders?: BaseEntity[],
        public userLogin?: string,
        public userId?: number,
    ) {
    }
}
