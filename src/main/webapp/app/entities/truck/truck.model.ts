import { BaseEntity } from './../../shared';

export class Truck implements BaseEntity {
    constructor(
        public id?: number,
        public registerSign?: string,
        public bodyNumber?: string,
        public weight?: number,
        public color?: string,
        public yearOfIssue?: any,
        public trailerId?: number,
    ) {
    }
}
