import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from '../config/application.config';
import { BillingInfo } from '../models/BillingInfo.model';
import { map } from 'rxjs/operators';
import { Pager } from '../models/Pager';


@Injectable()
export class BillingInfoService{
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<any>(`${config.apiUrl}/billing_info/getAlls`);
    }

    getUsersByCondition(bInFoCondition: BillingInfo, pager: Pager){
        if(!pager){
            pager = new Pager();
        }
        var url = `${config.apiUrl}/billing_info/getByCondition?`;
        url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;
        if(bInFoCondition.accountName){
            url = url + "&accountName=" + bInFoCondition.accountName;
        }

        if(bInFoCondition.bankAccount){
            url = url + "&bankAccount=" + bInFoCondition.bankAccount;
        }

        if(bInFoCondition.bankBranch){
            url = url + "&bankBranch=" + bInFoCondition.bankBranch;
        }

        if(bInFoCondition.bankName){
            url = url + "&bankName=" + bInFoCondition.bankName;
        }
       

        if(bInFoCondition.activeFlg !=null){
            url = url + "&activeFlg=" + bInFoCondition.activeFlg;
        }
        return this.http.get<any>(url);
    }

    getById(id: number) {
        return this.http.get(`${config.apiUrl}/billing_info/id/${id}`);
    }

    add(bInfo: BillingInfo) {
        return this.http.post(`${config.apiUrl}/billing_info/add`, bInfo);
    }

    update(bInfo: BillingInfo) {
        return this.http.put(`${config.apiUrl}/billing_info/update`, bInfo); 
    }

    deleteById(id: number) {
        return this.http.delete(`${config.apiUrl}/billing_info/deleteById/${id}`);
    }

   


}