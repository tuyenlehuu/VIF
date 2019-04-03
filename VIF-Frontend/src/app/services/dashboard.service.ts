import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from '../config/application.config';

@Injectable()
export class DashboardService{
    constructor(private http: HttpClient) { }

    getData() {
        return this.http.get<any>(`${config.apiUrl}/dashboard/getData`);
    }

    getReportAsset() {
        return this.http.get<any>(`${config.apiUrl}/dashboard/get-asset-report`);
    }
}