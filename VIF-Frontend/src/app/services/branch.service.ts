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

    // getBranchByCondition(branchCondition: Branch,pager:Pager){
    //     if(!pager){
    //         pager = new Pager();
    //     }
    //     var url = `${config.apiUrl}/branch/getBranchByCondition?`;
    //     url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;
        
    //     if(branchCondition.code){
    //         url = url + "&code=" + branchCondition.code;
    //     }

    //     if(branchCondition.name){
    //         url = url + "&name=" + branchCondition.name;
    //     }

    //     if(branchCondition.activeFlg !=null && branchCondition.activeFlg !== -1){
    //         url = url + "&activeFlg=" + branchCondition.activeFlg;
    //     }
    //     // console.log("url: ", url);
    //     return this.http.get<any>(url);
    // }

    getById(id: number) {
            return this.http.get(`${config.apiUrl}/branch/${id}`);
        }

    // register(user: User) {
    //     return this.http.post(`${config.apiUrl}/user/add`, user);
    // }

    // update(user: User) {
    //     return this.http.put(`${config.apiUrl}/user/update`, user); 
    // }

    // deleteById(id: number) {
    //     return this.http.delete(`${config.apiUrl}/user/deleteById/${id}`);
    // }

    // deleteByUsername(username: string) {
    //     return this.http.delete(`${config.apiUrl}/user/deleteByName/${username}`);
    // }
}