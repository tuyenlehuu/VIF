import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ShareMaster } from '../models/ShareMaster.model';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';
import { Asset } from '../models/Asset.model';

@Injectable()
export class ShareMasterService{
    constructor(private http: HttpClient) { }

    getAllShares(){
        return this.http.get<any>(`${config.apiUrl}/cophieu/getAlls`);
    }

    getShareById(shareId: string) {
        
    }

    getByShareByCode(shareCode: string) {
        return this.http.get(`${config.apiUrl}/cophieu/code/${shareCode}`);
    }

    addShare(asset: Asset) {
       
    }

    updateShare(asset : Asset){

    }
}