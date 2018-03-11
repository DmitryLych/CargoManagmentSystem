import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DriverLicense } from './driver-license.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DriverLicense>;

@Injectable()
export class DriverLicenseService {

    private resourceUrl =  SERVER_API_URL + 'api/driver-licenses';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(driverLicense: DriverLicense): Observable<EntityResponseType> {
        const copy = this.convert(driverLicense);
        return this.http.post<DriverLicense>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(driverLicense: DriverLicense): Observable<EntityResponseType> {
        const copy = this.convert(driverLicense);
        return this.http.put<DriverLicense>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DriverLicense>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DriverLicense[]>> {
        const options = createRequestOption(req);
        return this.http.get<DriverLicense[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DriverLicense[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DriverLicense = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DriverLicense[]>): HttpResponse<DriverLicense[]> {
        const jsonResponse: DriverLicense[] = res.body;
        const body: DriverLicense[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DriverLicense.
     */
    private convertItemFromServer(driverLicense: DriverLicense): DriverLicense {
        const copy: DriverLicense = Object.assign({}, driverLicense);
        copy.validate = this.dateUtils
            .convertLocalDateFromServer(driverLicense.validate);
        return copy;
    }

    /**
     * Convert a DriverLicense to a JSON which can be sent to the server.
     */
    private convert(driverLicense: DriverLicense): DriverLicense {
        const copy: DriverLicense = Object.assign({}, driverLicense);
        copy.validate = this.dateUtils
            .convertLocalDateToServer(driverLicense.validate);
        return copy;
    }
}
