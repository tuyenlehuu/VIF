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

    getByCode(assetCode: string) {
        return this.http.get(`${config.apiUrl}/asset/${assetCode}`);
    }

    addAsset(asset: Asset) {
        return this.http.post(`${config.apiUrl}/asset/add`, asset);

    }
}