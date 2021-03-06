import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { config } from '../config/application.config';
import { map } from 'rxjs/operators';
import { BuySellCCQ } from '../models/BuySelCCQ.model';
import { Pager } from '../models/Pager';
import { CommissionObj } from '../models/CommissionObj.model';

@Injectable()
export class InvestorTransService{
    constructor(private http: HttpClient) { }

    buyCCQ(buyCCQObject: BuySellCCQ) {
        return this.http.post<any>(`${config.apiUrl}/investor-transaction/buyCCQ`, buyCCQObject).pipe(map(res => {return res;}));
    }

    buyEnsureCCQ(buyEnsureCCQObject: BuySellCCQ) {
        return this.http.post<any>(`${config.apiUrl}/investor-transaction/buyEnsureCCQ`, buyEnsureCCQObject).pipe(map(res => {return res;}));
    }

    sellCCQ(sellCCQObject: BuySellCCQ) {
        return this.http.post<any>(`${config.apiUrl}/investor-transaction/sellCCQ`, sellCCQObject).pipe(map(res => {return res;}));
    }


    getEnsureCCQByCusAsset(customerId: number, assetCode: string) {
        return this.http.get(`${config.apiUrl}/investor-transaction/getEnsureCCQByCusAsset/${customerId}/${assetCode}`);
    }

    cCommissionDivide(commissionObj: CommissionObj) {
        return this.http.post<any>(`${config.apiUrl}/investor-transaction/cCommissionDivide`, commissionObj).pipe(map(res => {return res;}));
    }

    searchInvestorHistoryByCondition(customerId: number, fromDate: string, toDate: string, pager: Pager){
        if(!pager){
            pager = new Pager();
        }
        var url = `${config.apiUrl}/investor-transaction/getInvestorHistoryByCondition?`;
        url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;
        if(customerId){
            url = url + "&customerId=" + customerId;
        }

        if(fromDate){
            url = url + "&fromDate=" + fromDate;
        }

        if(toDate){
            url = url + "&toDate=" + toDate;
        }

        return this.http.get<any>(url);
    }

    exportCsv(customerId: number, fromDate: string, toDate: string){
        var url = `${config.apiUrl}/investor-transaction/exportCSV/invest-history.csv?`;
        var currentUser = localStorage.getItem("currentUser");
        var token = JSON.parse(currentUser).token;
        url = url + "access_token=" + token;
        if(customerId){
            url = url + "&customerId=" + customerId;
        }

        if(fromDate){
            url = url + "&fromDate=" + fromDate;
        }

        if(toDate){
            url = url + "&toDate=" + toDate;
        }

        window.open(url);

        // return this.http.get<any>(url);
    }

}