import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Trailer } from './trailer.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Trailer>;

@Injectable()
export class TrailerService {

    private resourceUrl =  SERVER_API_URL + 'api/trailers';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(trailer: Trailer): Observable<EntityResponseType> {
        const copy = this.convert(trailer);
        return this.http.post<Trailer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(trailer: Trailer): Observable<EntityResponseType> {
        const copy = this.convert(trailer);
        return this.http.put<Trailer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Trailer>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Trailer[]>> {
        const options = createRequestOption(req);
        return this.http.get<Trailer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Trailer[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Trailer = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Trailer[]>): HttpResponse<Trailer[]> {
        const jsonResponse: Trailer[] = res.body;
        const body: Trailer[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Trailer.
     */
    private convertItemFromServer(trailer: Trailer): Trailer {
        const copy: Trailer = Object.assign({}, trailer);
        copy.yearOfIssue = this.dateUtils
            .convertLocalDateFromServer(trailer.yearOfIssue);
        return copy;
    }

    /**
     * Convert a Trailer to a JSON which can be sent to the server.
     */
    private convert(trailer: Trailer): Trailer {
        const copy: Trailer = Object.assign({}, trailer);
        copy.yearOfIssue = this.dateUtils
            .convertLocalDateToServer(trailer.yearOfIssue);
        return copy;
    }
}
