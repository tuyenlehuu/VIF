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

    getNavReport(customerId: number, fromDate: string, toDate: string) {
        let url: string = `${config.apiUrl}/dashboard/get-nav-report?page=1`;
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

    getNavChartData(isByMonth: boolean) {
        let url: string = `${config.apiUrl}/dashboard/get-nav-chart-report?isByMonth=` + isByMonth;
        return this.http.get<any>(url);
    }

    getDebtReportData(username: string) {
        let url: string = `${config.apiUrl}/dashboard/get-debt-report?username=` + username;
        return this.http.get<any>(url);
    }
}