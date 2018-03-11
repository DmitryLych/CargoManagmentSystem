import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { InsurancePolicy } from './insurance-policy.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<InsurancePolicy>;

@Injectable()
export class InsurancePolicyService {

    private resourceUrl =  SERVER_API_URL + 'api/insurance-policies';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(insurancePolicy: InsurancePolicy): Observable<EntityResponseType> {
        const copy = this.convert(insurancePolicy);
        return this.http.post<InsurancePolicy>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(insurancePolicy: InsurancePolicy): Observable<EntityResponseType> {
        const copy = this.convert(insurancePolicy);
        return this.http.put<InsurancePolicy>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<InsurancePolicy>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<InsurancePolicy[]>> {
        const options = createRequestOption(req);
        return this.http.get<InsurancePolicy[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<InsurancePolicy[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: InsurancePolicy = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<InsurancePolicy[]>): HttpResponse<InsurancePolicy[]> {
        const jsonResponse: InsurancePolicy[] = res.body;
        const body: InsurancePolicy[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to InsurancePolicy.
     */
    private convertItemFromServer(insurancePolicy: InsurancePolicy): InsurancePolicy {
        const copy: InsurancePolicy = Object.assign({}, insurancePolicy);
        copy.validate = this.dateUtils
            .convertLocalDateFromServer(insurancePolicy.validate);
        return copy;
    }

    /**
     * Convert a InsurancePolicy to a JSON which can be sent to the server.
     */
    private convert(insurancePolicy: InsurancePolicy): InsurancePolicy {
        const copy: InsurancePolicy = Object.assign({}, insurancePolicy);
        copy.validate = this.dateUtils
            .convertLocalDateToServer(insurancePolicy.validate);
        return copy;
    }
}
