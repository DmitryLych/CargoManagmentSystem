import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Truck } from './truck.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Truck>;

@Injectable()
export class TruckService {

    private resourceUrl =  SERVER_API_URL + 'api/trucks';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(truck: Truck): Observable<EntityResponseType> {
        const copy = this.convert(truck);
        return this.http.post<Truck>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(truck: Truck): Observable<EntityResponseType> {
        const copy = this.convert(truck);
        return this.http.put<Truck>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Truck>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Truck[]>> {
        const options = createRequestOption(req);
        return this.http.get<Truck[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Truck[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Truck = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Truck[]>): HttpResponse<Truck[]> {
        const jsonResponse: Truck[] = res.body;
        const body: Truck[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Truck.
     */
    private convertItemFromServer(truck: Truck): Truck {
        const copy: Truck = Object.assign({}, truck);
        copy.yearOfIssue = this.dateUtils
            .convertLocalDateFromServer(truck.yearOfIssue);
        return copy;
    }

    /**
     * Convert a Truck to a JSON which can be sent to the server.
     */
    private convert(truck: Truck): Truck {
        const copy: Truck = Object.assign({}, truck);
        copy.yearOfIssue = this.dateUtils
            .convertLocalDateToServer(truck.yearOfIssue);
        return copy;
    }
}
