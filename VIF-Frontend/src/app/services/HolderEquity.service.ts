import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/User.model';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';
import { Branch } from '../models/Branch.model';
import { HolderEquity } from '../models/HolderEquity';
import { HolderHistory } from '../models/holderHistory.model';

@Injectable()
export class HolderequityService{
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<any>(`${config.apiUrl}/holder/getAlls`);
    }
     
    getHolderequityByCondition(holderCondition: HolderEquity,fromDate:string,toDate:string,pager:Pager){
        if(!pager){
            pager = new Pager();
        }
        var url = `${config.apiUrl}/holder/getHoldersByCondition?`;
        url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;
        
        if(fromDate){
            url = url + "&fromDate=" + fromDate;
        }

        if(toDate){
            url = url + "&toDate=" + toDate;
        }

        if(holderCondition.fullName){
            url = url + "&fullName=" + holderCondition.fullName;
        }
        // console.log("url: ", url);
        return this.http.get<any>(url);
    }


    getById(id: number) {
            return this.http.get(`${config.apiUrl}/holder/${id}`);
        }

    register(holder:HolderEquity) {
        return this.http.post(`${config.apiUrl}/holder/add`,holder );
    }

    add(holderHistory:HolderHistory,id:number){
        return this.http.post(`${config.apiUrl}/holder_history/add/${id}`,holderHistory);
    }

    update(holder:HolderEquity) {
        return this.http.put(`${config.apiUrl}/holder/update`, holder); 
    }

    deleteById(id: number) {
        return this.http.delete(`${config.apiUrl}/holder/deleteById/${id}`);
    }

    // // deleteByUsername(username: string) {
    // //     return this.http.delete(`${config.apiUrl}/user/deleteByName/${username}`);
    // // }
    // getUsersByCondition(branchCondition: Branch, pager: Pager){
    //     if(!pager){
    //         pager = new Pager();
    //     }
    //     var url = `${config.apiUrl}/branch/getBranchsByCondition?`;
    //     url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;
    //     if(branchCondition.branchCode){
    //         url = url + "&branchCode=" + branchCondition.branchCode;
    //     }

    //     if(branchCondition.branchName){
    //         url = url + "&branchName=" + branchCondition.branchName;
    //     }

    //     if(branchCondition.activeFlg !=null && branchCondition.activeFlg !== -1){
    //         url = url + "&activeFlg=" + branchCondition.activeFlg;
    //     }
    //     // console.log("url: ", url);
    //     return this.http.get<any>(url);
    // }
}