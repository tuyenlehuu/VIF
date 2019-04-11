import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/User.model';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';
import { Branch } from '../models/Branch.model';

@Injectable()
export class BranchService{
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<any>(`${config.apiUrl}/branch/getAlls`);
    }

    getBranchById(id: number) {
        return this.http.get<any>(`${config.apiUrl}/branch/` + id);
    }

    
    

    getById(id: number) {
            return this.http.get(`${config.apiUrl}/branch/${id}`);
        }

    register(branch: Branch) {
        return this.http.post(`${config.apiUrl}/branch/add`,branch );
    }

    update(branch: Branch) {
        return this.http.put(`${config.apiUrl}/branch/update`, branch); 
    }

    deleteById(id: number) {
        return this.http.delete(`${config.apiUrl}/branch/deleteById/${id}`);
    }

    // deleteByUsername(username: string) {
    //     return this.http.delete(`${config.apiUrl}/user/deleteByName/${username}`);
    // }
    getUsersByCondition(branchCondition: Branch, pager: Pager){
        if(!pager){
            pager = new Pager();
        }
        var url = `${config.apiUrl}/branch/getBranchsByCondition?`;
        url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;
        if(branchCondition.branchCode){
            url = url + "&branchCode=" + branchCondition.branchCode;
        }

        if(branchCondition.branchName){
            url = url + "&branchName=" + branchCondition.branchName;
        }

        if(branchCondition.activeFlg !=null && branchCondition.activeFlg !== -1){
            url = url + "&activeFlg=" + branchCondition.activeFlg;
        }
        // console.log("url: ", url);
        return this.http.get<any>(url);
    }
}