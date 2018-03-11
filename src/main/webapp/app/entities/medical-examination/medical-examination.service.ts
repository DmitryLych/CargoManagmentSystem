import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MedicalExamination } from './medical-examination.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MedicalExamination>;

@Injectable()
export class MedicalExaminationService {

    private resourceUrl =  SERVER_API_URL + 'api/medical-examinations';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(medicalExamination: MedicalExamination): Observable<EntityResponseType> {
        const copy = this.convert(medicalExamination);
        return this.http.post<MedicalExamination>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(medicalExamination: MedicalExamination): Observable<EntityResponseType> {
        const copy = this.convert(medicalExamination);
        return this.http.put<MedicalExamination>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MedicalExamination>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MedicalExamination[]>> {
        const options = createRequestOption(req);
        return this.http.get<MedicalExamination[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MedicalExamination[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MedicalExamination = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MedicalExamination[]>): HttpResponse<MedicalExamination[]> {
        const jsonResponse: MedicalExamination[] = res.body;
        const body: MedicalExamination[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MedicalExamination.
     */
    private convertItemFromServer(medicalExamination: MedicalExamination): MedicalExamination {
        const copy: MedicalExamination = Object.assign({}, medicalExamination);
        copy.validate = this.dateUtils
            .convertLocalDateFromServer(medicalExamination.validate);
        return copy;
    }

    /**
     * Convert a MedicalExamination to a JSON which can be sent to the server.
     */
    private convert(medicalExamination: MedicalExamination): MedicalExamination {
        const copy: MedicalExamination = Object.assign({}, medicalExamination);
        copy.validate = this.dateUtils
            .convertLocalDateToServer(medicalExamination.validate);
        return copy;
    }
}
