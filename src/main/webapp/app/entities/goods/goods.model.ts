import { BaseEntity } from './../../shared';

export class Goods implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public weight?: number,
        public volume?: number,
        public goodsType?: string,
        public orderId?: number,
    ) {
    }
}
