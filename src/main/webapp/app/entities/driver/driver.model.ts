import { BaseEntity } from './../../shared';

export class Driver implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public yearOfIssue?: any,
        public address?: string,
        public tepephoneNumber?: string,
        public email?: string,
        public status?: boolean,
        public driverLicenseId?: number,
        public medicalExaminationId?: number,
        public truckId?: number,
        public insurancePolicies?: BaseEntity[],
        public companyId?: number,
        public orders?: BaseEntity[],
        public userLogin?: string,
        public userId?: number,
    ) {
        this.status = false;
    }
}
