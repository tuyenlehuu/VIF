import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';
import { map } from 'rxjs/operators';
import { InvestAppro } from '../models/InvestAppro.model';

@Injectable()
export class InvestApproService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<any>(`${config.apiUrl}/invest_request/getAlls`);
    }

    getInvestApproByCondition(RequestCondition: InvestAppro, fromDate: string, toDate: string, pager: Pager) {
        if (!pager) {
            pager = new Pager();
        }
        var url = `${config.apiUrl}/invest_request/getInvestRequestByCondition?`;
        url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;

        if (RequestCondition.typeOfRequest) {
            url = url + "&typeOfRequest=" + RequestCondition.typeOfRequest;
        }

        if (fromDate) {
            url = url + "&fromDate=" + fromDate;
        }

        if (toDate) {
            url = url + "&toDate=" + toDate;
        }

        if (RequestCondition.status) {
            url = url + "&status=" + RequestCondition.status;
        }

        // console.log("url: ", url);
        return this.http.get<any>(url);
    }

}