import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/User.model';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';

@Injectable()
export class InvestorTransService{
    constructor(private http: HttpClient) { }

    buyCCQ(customerId: number, money: number, priceCCQ: number) {
        var url = `${config.apiUrl}/investor-transaction/buyCCQ?customerId=`;
        url = url + customerId + "&money=" + money + "&priceCCQ=" + priceCCQ;
        return this.http.get<any>(url);
    }

    sellCCQ(customerId: number, amountCCQ: number, priceCCQ: number) {
        var url = `${config.apiUrl}/investor-transaction/sellCCQ?customerId=`;
        url = url + customerId + "&amountCCQ=" + amountCCQ + "&priceCCQ=" + priceCCQ;
        return this.http.get<any>(url);
    }
}