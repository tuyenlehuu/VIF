import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Asset } from '../models/Asset.model';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';

@Injectable()
export class AssetService{
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<any>(`${config.apiUrl}/asset/getAlls`);
    }

    getAllShares(){
        return this.http.get<any>(`${config.apiUrl}/asset/getAllShares`);
    }

    getByCode(assetCode: string) {
        return this.http.get(`${config.apiUrl}/asset/getAssetByCode/${assetCode}`);
    }

    addAsset(asset: Asset) {
        return this.http.post(`${config.apiUrl}/asset/add`, asset);
    }

    getOtherAssetNotShares(){
        return this.http.get<any>(`${config.apiUrl}/asset/getOtherAssetNotShares`);
    }

    update(asset: Asset) {
        return this.http.put(`${config.apiUrl}/asset/update`, asset); 
    }

    deleteAssetById(id: number) {
        return this.http.delete(`${config.apiUrl}/asset/deleteAssetById/${id}`);
    }

    deleteAssetByCode(assetCode: string) {
        return this.http.delete(`${config.apiUrl}/asset/deleteAssetByCode/${assetCode}`);
    }
}