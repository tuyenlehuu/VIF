import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { config } from '../config/application.config';
import { map } from 'rxjs/operators';
import { BuySellCCQ } from '../models/BuySelCCQ.model';
import { Pager } from '../models/Pager';

@Injectable()
export class InvestorTransService{
    constructor(private http: HttpClient) { }

    buyCCQ(buyCCQObject: BuySellCCQ) {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };

        return this.http.post<any>(`${config.apiUrl}/investor-transaction/buyCCQ`, buyCCQObject, httpOptions).pipe(map(res => {return res;}));
    }

    sellCCQ(sellCCQObject: BuySellCCQ) {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };
        return this.http.post<any>(`${config.apiUrl}/investor-transaction/sellCCQ`, sellCCQObject, httpOptions).pipe(map(res => {return res;}));
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