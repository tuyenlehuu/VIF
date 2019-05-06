import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { config } from '../config/application.config';
import { map } from 'rxjs/operators';
import { BuySellAsset } from '../models/BuySellAsset.model';
import { Pager } from '../models/Pager';
import { DividendObject } from '../models/Dividend.model';

@Injectable()
export class InvestManagementService {
    constructor(private http: HttpClient) { }

    buyAsset(buyAssetObject: BuySellAsset) {
        return this.http.post<any>(`${config.apiUrl}/asset/buySercurities`, buyAssetObject).pipe(map(res => { return res; }));
    }

    sellAsset(sellAssetObject: BuySellAsset) {
        return this.http.post<any>(`${config.apiUrl}/asset/sellSercurities`, sellAssetObject).pipe(map(res => { return res; }));
    }

    dividendTrans(dividendobj: DividendObject) {
        return this.http.post<any>(`${config.apiUrl}/asset/dividendTrans`, dividendobj).pipe(map(res => { return res; }));
    }


}