import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Customer } from '../models/Customer.model';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';
import { map } from 'rxjs/operators';

@Injectable()
export class CustomerService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<any>(`${config.apiUrl}/customer/getAlls`);
    }


    getCustomersByCondition(customerCondition: Customer, pager: Pager) {
        if (!pager) {
            pager = new Pager();
        }
        var url = `${config.apiUrl}/customer/getCustomersByCondition?`;
        url = url + "page=" + pager.page + "&pageSize=" + pager.pageSize;
        if (customerCondition.fullName) {
            url = url + "&fullName=" + customerCondition.fullName;
        }

        if (customerCondition.code) {
            url = url + "&code=" + customerCondition.code;
        }

        if (customerCondition.activeFlg != null && customerCondition.activeFlg !== -1) {
            url = url + "&activeFlg=" + customerCondition.activeFlg;
        }

        if (customerCondition.email) {
            url = url + "&email=" + customerCondition.email;
        }

        return this.http.get<any>(url);
    }

    getById(id: number) {
        return this.http.get(`${config.apiUrl}/customer/id/${id}`);
    }

    addCustomer(customer: Customer) {
        return this.http.post(`${config.apiUrl}/customer/add`, customer);
    }

    update(customer: Customer) {

        return this.http.put(`${config.apiUrl}/customer/update`, customer);

    }

    deleteCustomerById(id: number) {
        return this.http.delete(`${config.apiUrl}/customer/deleteById/${id}`);
    }

    deleteByCustomerByCode(code: string) {
        return this.http.delete(`${config.apiUrl}/customer/deleteByCode/${code}`);
    }


    upFile(file: FormData): boolean {
        // debugger;
        // if (this.http.post(`${config.apiUrl}/customer/upFileAvatar`, file)) { return true; }
        this.http.post(`${config.apiUrl}/customer/upFileAvatar`, file).pipe(map(res => {console.log("ABCAAA", res); return res;}));
        return false;

    }

    getUsers(id: Number) {
        return this.http.get(`${config.apiUrl}/customer/UsersById/${id}`);


    }

}