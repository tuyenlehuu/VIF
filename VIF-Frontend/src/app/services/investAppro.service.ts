import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';
import { map } from 'rxjs/operators';
import { InvestAppro } from '../models/InvestAppro.model';
import { InvestRequest } from '../models/InvestRequest.model';

@Injectable()
export class InvestApproService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<any>(`${config.apiUrl}/invest-appro/getAlls`);
    }

    reject(id:number){
        return this.http.delete(`${config.apiUrl}/invest-appro/reject/${id}`);
    }

    accept(investRequest: InvestRequest) {
        return this.http.put(`${config.apiUrl}/invest-appro/accept`, investRequest);
      }

    getInvestApproByCondition(RequestCondition: InvestAppro, fromDate: string, toDate: string, pager: Pager) {
        if (!pager) {
            pager = new Pager();
        }
        var url = `${config.apiUrl}/invest-appro/getInvestRequestsByCondition?`;
        url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;

        if (RequestCondition.typeOfRequest) {
            url = url + "&typeOfRequest=" + RequestCondition.typeOfRequest;
        }

        if (RequestCondition.typeOfInvest) {
            url = url + "&typeOfInvest=" + RequestCondition.typeOfInvest;
        }

        if (RequestCondition.status) {
            url = url + "&status=" + RequestCondition.status;
        }

        if (fromDate) {
            url = url + "&fromDate=" + fromDate;
        }

        if (toDate) {
            url = url + "&toDate=" + toDate;
        }
        // console.log("url: ", url);
        return this.http.get<any>(url);
    }
    update(request: InvestAppro) {
        return this.http.put(`${config.apiUrl}/invest_request/update`, request); 
    }
    add(request: InvestRequest) {
        var url = `${config.apiUrl}/invest_request/add`;
        // var currentUser = localStorage.getItem("currentUser");
        // var token = JSON.parse(currentUser).token;
        // url = url + "access_token=" + token;
         console.log("APIIIIIIIII",request);
        return this.http.post(`${config.apiUrl}/invest_request/add`, request);
        
    }

}