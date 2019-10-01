import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { StockTracking } from '../models/StockTracking.model';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';
import { map } from 'rxjs/operators';

@Injectable()
export class StockTrackingService{
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<any>(`${config.apiUrl}/stock-tracking/getAlls`);
    }

    getStockTrackingsByCondition(stockCondition: StockTracking, pager: Pager){
        if(!pager){
            pager = new Pager();
        }
        var url = `${config.apiUrl}/stock-tracking/getStockTrackingByCondition?`;
        url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;
        if(stockCondition.code){
            url = url + "&stockCode=" + stockCondition.code;
        }
        
        // console.log("url: ", url);
        return this.http.get<any>(url);
    }

    getByCode(stockCode: string) {
        return this.http.get(`${config.apiUrl}/stock-tracking/stock-code/${stockCode}`);
    }

    addStockTracking(stockTracking: StockTracking) {
        return this.http.post<any>(`${config.apiUrl}/stock-tracking/add`, stockTracking).pipe(map(res => {return res;}));
    }

    update(stockTracking: StockTracking) {
        return this.http.put(`${config.apiUrl}/stock-tracking/update`, stockTracking); 
    }

    deleteStockTrackingById(id: number) {
        return this.http.delete(`${config.apiUrl}/stock-tracking/deleteById/${id}`);
    }

    deleteStockTrackingByCode(stockCode: string) {
        return this.http.delete(`${config.apiUrl}/stock-tracking/deleteByCode/${stockCode}`);
    }

    getStockTrackingById(id: number) {
        return this.http.get(`${config.apiUrl}/stock-tracking/${id}`);
    }
}