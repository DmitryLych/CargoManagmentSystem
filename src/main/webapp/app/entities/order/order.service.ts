import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Order } from './order.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Order>;

@Injectable()
export class OrderService {

    private resourceUrl =  SERVER_API_URL + 'api/orders';

    constructor(private http: HttpClient) { }

    create(order: Order): Observable<EntityResponseType> {
        const copy = this.convert(order);
        return this.http.post<Order>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(order: Order): Observable<EntityResponseType> {
        const copy = this.convert(order);
        return this.http.put<Order>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Order>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Order[]>> {
        const options = createRequestOption(req);
        return this.http.get<Order[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Order[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Order = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Order[]>): HttpResponse<Order[]> {
        const jsonResponse: Order[] = res.body;
        const body: Order[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Order.
     */
    private convertItemFromServer(order: Order): Order {
        const copy: Order = Object.assign({}, order);
        return copy;
    }

    /**
     * Convert a Order to a JSON which can be sent to the server.
     */
    private convert(order: Order): Order {
        const copy: Order = Object.assign({}, order);
        return copy;
    }
}
