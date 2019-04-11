import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AppParam } from '../models/AppParam.model';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';

@Injectable()
export class AppParamService {
  constructor(private http: HttpClient) { }
  getAll() {
    return this.http.get<any>(`${config.apiUrl}/app_param/getAlls`);
  }
  getAppParamsByCondition(appParamCondition: AppParam, pager: Pager) {
    if (!pager) {
      pager = new Pager();
    }
    var url = `${config.apiUrl}/app_param/getAppParamsByCondition?`;
    url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;
    if (appParamCondition.propKey) {
      url = url + "&propKey=" + appParamCondition.propKey;
    }

    if (appParamCondition.propType != null && appParamCondition.propType !== '0') {
      url = url + "&propType=" + appParamCondition.propType;
    }

    if (appParamCondition.propValue) {
      url = url + "&propValue=" + appParamCondition.propValue;
    }

    if (appParamCondition.activeFlg != null && appParamCondition.activeFlg !== -1) {
      url = url + "&activeFlg=" + appParamCondition.activeFlg;
    }

    if (appParamCondition.description) {
      url = url + "&description=" + appParamCondition.description;
    }
    // console.log("url: ", url);
    return this.http.get<any>(url);
  }

  getById(id: number) {
    return this.http.get(`${config.apiUrl}/app_param/${id}`);
  }
  update(appParam: AppParam) {
    return this.http.put(`${config.apiUrl}/app_param/update`, appParam);
  }
  deleteById(id: number) {
    return this.http.delete(`${config.apiUrl}/app_param/deleteById/${id}`);
  }
  register(appParam: AppParam) {
    return this.http.post(`${config.apiUrl}/app_param/add`, appParam);
  }
}