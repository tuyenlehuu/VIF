import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Asset } from '../models/Asset.model';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';
import { map } from 'rxjs/operators';

@Injectable()
export class AssetService{
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<any>(`${config.apiUrl}/asset/getAlls`);
    }

    getAssetsByCondition(assetCondition: Asset, pager: Pager){
        if(!pager){
            pager = new Pager();
        }
        var url = `${config.apiUrl}/asset/getAssetsByCondition?`;
        url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;
        if(assetCondition.assetCode){
            url = url + "&assetCode=" + assetCondition.assetCode;
        }

        if(assetCondition.assetName){
            url = url + "&assetName=" + assetCondition.assetName;
        }

        if(assetCondition.groupAsset !=null && assetCondition.groupAsset.id !=null){
            url = url + "&groupAssetId=" + assetCondition.groupAsset.id;
        }
        // console.log("url: ", url);
        return this.http.get<any>(url);
    }

    getAllShares(){
        return this.http.get<any>(`${config.apiUrl}/asset/getAllShares`);
    }

    getAllSharesForBuy(){
        return this.http.get<any>(`${config.apiUrl}/asset/getAllSharesForBuy`);
    }

    getByCode(assetCode: string) {
        return this.http.get(`${config.apiUrl}/asset/getAssetByCode/${assetCode}`);
    }

    addAsset(asset: Asset) {
        return this.http.post<any>(`${config.apiUrl}/asset/add`, asset).pipe(map(res => {return res;}));;;
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

    getAssetById(id: number) {
        return this.http.get(`${config.apiUrl}/asset/getAssetById/${id}`);
    }

    getGroupAsset() {
        return this.http.get(`${config.apiUrl}/asset/group/getAlls`);
    }

    
}