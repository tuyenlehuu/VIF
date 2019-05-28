import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';
import { from } from 'rxjs';


@Injectable()
export class TransactionService{
    constructor(private http: HttpClient) { }

    getAll() {
      return this.http.get<any>(`${config.apiUrl}/transaction/getAlls`);
  }
    getTransactionsByCondition(fromDate:string,toDate:string,typeOftransaction1:string,assetID:number, pager: Pager){
        if(!pager){
            pager = new Pager();
        }
        var url = `${config.apiUrl}/transaction/getTransactionsByCondition?`;
        url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;
       
        if(fromDate){
            url = url + "&fromDate=" + fromDate;
        }
        if(toDate){
            url = url + "&toDate=" + toDate;
        }
        if(typeOftransaction1){
          url = url + "&typeOfTransaction=" + typeOftransaction1;
      }
        
        if(assetID){
            url = url + "&assetId=" + assetID;
        }

       
        // console.log("url: ", url);
        return this.http.get<any>(url);
    }
    exportCsv(fromDate: string,toDate:string,typeOftransaction1:string,assetID:number ){
        var url = `${config.apiUrl}/transaction/exportCSV/transaction-history.csv?`;
        var currentUser = localStorage.getItem("currentUser");
        var token = JSON.parse(currentUser).token;
        url = url + "access_token=" + token;
       

        if(fromDate){
            url = url + "&fromDate=" + fromDate;
        }
        
        if(toDate){
            url = url + "&toDate=" + toDate;
        }

        if(typeOftransaction1){
            url = url + "&typeOfTransaction=" + typeOftransaction1;
        }
        if(assetID){
            url = url + "&assetId=" + assetID;
        }

        window.open(url);

        // return this.http.get<any>(url);
    }

}