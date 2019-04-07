import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Customer } from '../models/Customer.model';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';

@Injectable()
export class CustomerService{
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<any>(`${config.apiUrl}/customer/getAlls`);
    }
}