import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';
import { map } from 'rxjs/operators';
import { InvestRequest } from '../models/InvestRequest.model';


@Injectable()
export class InvestRequestService {
    constructor(private http: HttpClient) { }

    getPriceCCQ() {
        return this.http.get<any>(`${config.apiUrl}/invest_request/getPriceCCQ`);
    }

    getAll() {
        return this.http.get<any>(`${config.apiUrl}/invest_request/getAlls`);
    }

    getInvestRequestByCondition(RequestCondition: InvestRequest, pager: Pager) {
        if (!pager) {
            pager = new Pager();
        }
        var url = `${config.apiUrl}/invest_request/getInvestRequestByCondition?`;
        url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;

        if (RequestCondition.typeOfRequest) {
            url = url + "&typeOfRequest=" + RequestCondition.typeOfRequest;
        }

        if (RequestCondition.status != null) {
            url = url + "&status=" + RequestCondition.status;
        }

        // console.log("url: ", url);
        return this.http.get<any>(url);
    }

    getById(id: number) {
        return this.http.get(`${config.apiUrl}/invest_request/id/${id}`);
    }

    add(request: InvestRequest) {
        var url = `${config.apiUrl}/invest_request/add`;
        console.log("APIIIIIIIII", request);
        return this.http.post(`${config.apiUrl}/invest_request/add`, request);

    }

    update(request: InvestRequest) {
        return this.http.put(`${config.apiUrl}/invest_request/update`, request);
    }

    getEnsureCCQByCusAsset(customerId: number, assetCode: string) {
        return this.http.get(`${config.apiUrl}/invest_request/getEnsureCCQByCusAsset/${customerId}/${assetCode}`);
    }

    getCustomerByUsername(userName: String){
        return this.http.get(`${config.apiUrl}/invest_request/getCustomerByUsername?userName=${userName}`);
    }
}