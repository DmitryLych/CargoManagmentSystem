import { BaseEntity } from './../../shared';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public companyName?: string,
        public address?: string,
        public email?: string,
        public telephoneNumber?: string,
        public drivers?: BaseEntity[],
        public userLogin?: string,
        public userId?: number,
    ) {
    }
}
