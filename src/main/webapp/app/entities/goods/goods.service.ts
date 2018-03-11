import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Goods } from './goods.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Goods>;

@Injectable()
export class GoodsService {

    private resourceUrl =  SERVER_API_URL + 'api/goods';

    constructor(private http: HttpClient) { }

    create(goods: Goods): Observable<EntityResponseType> {
        const copy = this.convert(goods);
        return this.http.post<Goods>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(goods: Goods): Observable<EntityResponseType> {
        const copy = this.convert(goods);
        return this.http.put<Goods>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Goods>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Goods[]>> {
        const options = createRequestOption(req);
        return this.http.get<Goods[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Goods[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Goods = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Goods[]>): HttpResponse<Goods[]> {
        const jsonResponse: Goods[] = res.body;
        const body: Goods[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Goods.
     */
    private convertItemFromServer(goods: Goods): Goods {
        const copy: Goods = Object.assign({}, goods);
        return copy;
    }

    /**
     * Convert a Goods to a JSON which can be sent to the server.
     */
    private convert(goods: Goods): Goods {
        const copy: Goods = Object.assign({}, goods);
        return copy;
    }
}
