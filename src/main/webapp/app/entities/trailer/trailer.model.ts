import { BaseEntity } from './../../shared';

export class Trailer implements BaseEntity {
    constructor(
        public id?: number,
        public registerSign?: string,
        public color?: string,
        public trailerType?: string,
        public weight?: number,
        public height?: number,
        public longest?: number,
        public volume?: number,
        public yearOfIssue?: any,
        public truckId?: number,
    ) {
    }
}
