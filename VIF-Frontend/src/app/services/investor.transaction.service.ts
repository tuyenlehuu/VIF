import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { config } from '../config/application.config';
import { map } from 'rxjs/operators';
import { BuySellCCQ } from '../models/BuySelCCQ.model';

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
}