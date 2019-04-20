import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';
import { map } from 'rxjs/operators';
import { InvestRequest } from '../models/InvestRequest.model';


@Injectable()
export class InvestRequestService{
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<any>(`${config.apiUrl}/invest_request/getAlls`);
    }

    getInvestRequestByCondition(RequestCondition: InvestRequest, pager: Pager){
        if(!pager){
            pager = new Pager();
        }
        var url = `${config.apiUrl}/invest_request/getInvestRequestByCondition?`;
        url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;
       
        if(RequestCondition.typeOfRequest){
            url = url + "&typeOfRequest=" + RequestCondition.typeOfRequest;
        }

        if(RequestCondition.status!=null){
            url = url + "&status=" + RequestCondition.status;
        }

        // console.log("url: ", url);
        return this.http.get<any>(url);
    }

    getById(id: number) {
        return this.http.get(`${config.apiUrl}/invest_request/${id}`);
    }

    add(request: InvestRequest) {
        var url = `${config.apiUrl}/invest_request/add`;
        // var currentUser = localStorage.getItem("currentUser");
        // var token = JSON.parse(currentUser).token;
        // url = url + "access_token=" + token;
         console.log("APIIIIIIIII",request);
        return this.http.post(`${config.apiUrl}/invest_request/add`, request);
        
    }

    update(request: InvestRequest) {
        return this.http.put(`${config.apiUrl}/invest_request/update`, request); 
    }

    
}